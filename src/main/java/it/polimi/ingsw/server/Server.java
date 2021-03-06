package it.polimi.ingsw.server;

import it.polimi.ingsw.connection.AvailableGodsMessage;
import it.polimi.ingsw.connection.Message;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.view.RemoteView;
import it.polimi.ingsw.view.View;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *Handles all incoming connections and game setup.
 * Keeps track of active games and handles deregistration of the clients.
 */
public class Server {

    private static final int PORT = 27000;
    private ServerSocket serverSocket;
    private ExecutorService executor = Executors.newFixedThreadPool(128);
    private Map<String, ClientConnection> waitingConnection = new HashMap<>();
    private Map<String, String> waitingPlayersGods = new HashMap<>();
    private Map<Integer, List<ClientConnection>> activeGames = new HashMap<>();
    private List<String> godSelectionQueue = new ArrayList<>();
    private String host;
    private int waitingGameSize = -1;
    private List<String> availableGods = new ArrayList<>();

    /**
     * Create a new Server
     *
     * @throws IOException if there is a problem opening the socket
     */
    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
    }

    /**
     * Set the waiting game size
     * @param num number of players we want to have in the game
     */
    public synchronized void setGameSize(int num){
        System.out.println("[SERVER] Setting game size to "+num);
        waitingGameSize = num;
    }

    /**
     * Select the gods the players can choose from
     *
     * @param selected the list of available gods
     */
    public synchronized void setGods(List<String> selected){
        availableGods.clear();
        availableGods.addAll(selected);
    }


    /**
     * Eliminates a connection with a client when it is no longer needed
     * @param c stands for the connection that we want to eliminate
     */
    public synchronized void deregisterConnection(ClientConnection c) {
        String name = null;
        for(String n : waitingConnection.keySet())
            if(waitingConnection.get(n).equals(c)){
                name = n;
                break;
            }
        if(name != null)
            waitingConnection.remove(name);
        if(waitingConnection.size() == 0)
            waitingGameSize = -1;
        godSelectionQueue.remove(name);
        if(waitingPlayersGods.containsKey(name)){
            availableGods.add(waitingPlayersGods.get(name));
            waitingPlayersGods.remove(name);
        }
        int id = -1;
        for(int k : activeGames.keySet())
            if(activeGames.get(k).contains(c)){
                id = k;
                break;
            }
        if(id != -1){
            for(ClientConnection conn : activeGames.get(id))
                conn.closeConnection();
            activeGames.remove(id);
        }
    }

    /**
     * Sends the gods that are left to be chosen.
     */
    private void sendAvailableGods(){
        AvailableGodsMessage availableGodsMessage = new AvailableGodsMessage();
        for(String god : availableGods)
            availableGodsMessage.addGod(god);
        if(godSelectionQueue.size() == 0)
            availableGodsMessage.setCurPlayer(host);
        else
            availableGodsMessage.setCurPlayer(godSelectionQueue.get(0));
        for(String name : waitingConnection.keySet())
            waitingConnection.get(name).asyncSend(availableGodsMessage);
    }

    /**
     *Handles the connection between server and client during the stage of selection fo the gods.
     * @param name name of the player
     * @param godName
     */
    public void selectGod(String name, String godName){
        waitingPlayersGods.put(name, godName);
        godSelectionQueue.remove(name);
        availableGods.remove(godName);
        if(name.equals(host))
            setupGame();
        else if(godSelectionQueue.size() > 0 || availableGods.size() == 1)
            sendAvailableGods();
    }

    /**
     *Handles the connection between server and client during the stage of setup
     */
    public void setupGame(){
        System.out.println("[SERVER] Creating game...");
        List<String> keys = new ArrayList<>(waitingConnection.keySet());
        Model model = new Model();
        Controller controller = new Controller(model,waitingGameSize);
        List<View> views = new ArrayList<>();
        List<ClientConnection> connections = new ArrayList<>();
        for(int i=0;i<waitingGameSize;i++) {
            connections.add(waitingConnection.get(keys.get(i)));
            View view = new RemoteView(this, model, connections.get(i), i);
            connections.get(i).addListener(view);
            views.add(view);
            model.addListener(view);
            view.addListener(controller);
        }

        for(int i=0;i<waitingGameSize;i++) {
            String name = keys.get(i);
            views.get(i).processPlayerCreation(waitingPlayersGods.get(name), name);
        }

        activeGames.put(activeGames.size(), connections);
        waitingConnection.clear();
        waitingPlayersGods.clear();
        waitingGameSize = -1;

        System.out.println("[SERVER] Let the games begin!");
    }

    private void addToLobby(String name, ClientConnection c){
        waitingConnection.put(name, c);
        godSelectionQueue.add(name);
    }

    /**
     * method which makes the server wait for a new player and puts him in wait
     * @param c stands for the connection that we have to add to the game
     * @param name stands for the name of the player that wants to play
     */
    public synchronized void lobby(ClientConnection c, String name){
        if(waitingConnection.containsKey(name)){
            System.out.println("[SERVER] We already have a "+name+" in the lobby!");
            Message err = new Message();
            err.setStatus(Message.Status.ERROR);
            err.setErrorType(Message.ErrorType.NAME_TAKEN_ERROR);
            err.setPayload("Name "+name+" is already in the waiting lobby! Choose another name and reconnect");
            c.send(err);
            c.closeConnection();
            return;
        }
        System.out.println("[SERVER] Adding "+name+" to the lobby.");
        if(waitingConnection.size() == 0){
            System.out.println("[SERVER] Asking " + name + " for player number...");
            Message numReq = new Message();
            numReq.setMessageType(Message.MessageType.SETUP_REQ);
            c.asyncSend(numReq);
        }
        if(waitingGameSize == -1){
            if(waitingConnection.size() == 0) {
                waitingConnection.put(name, c);
                host = name;
            }
            else{
                Message err = new Message();
                err.setStatus(Message.Status.ERROR);
                err.setErrorType(Message.ErrorType.LOBBY_ERROR);
                c.asyncSend(err);
            }
        }
        else{
            addToLobby(name, c);
            if(godSelectionQueue.size() > 0)
                sendAvailableGods();
        }
    }


    /**
     * method that makes the server wait for a connection request
     */
    public void run(){
        System.out.println("[SERVER] Listening on port "+PORT);
        while(true){
            try {
                Socket newSocket = serverSocket.accept();
                System.out.println("[SERVER] Accepted new connection");
                SocketClientConnection socketConnection = new SocketClientConnection(newSocket, this);
                executor.submit(socketConnection);
            } catch (IOException e) {
                System.out.println("Connection Error!");
            }
        }
    }
}

