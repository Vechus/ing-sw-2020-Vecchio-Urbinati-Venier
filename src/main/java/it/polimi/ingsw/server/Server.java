package it.polimi.ingsw.server;

import it.polimi.ingsw.connection.Message;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.god.God;
import it.polimi.ingsw.view.RemoteView;
import it.polimi.ingsw.view.View;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final int PORT = 27000;
    private ServerSocket serverSocket;
    private ExecutorService executor = Executors.newFixedThreadPool(128);
    private Map<String, ClientConnection> waitingConnection = new HashMap<>();
    private Map<Integer, List<ClientConnection>> activeGames = new HashMap<>();
    private int waitingGameSize = -1;


    /**
     * setter to create Srver class
     * @throws IOException
     */
    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
    }


    /**
     * method to  eliminate a connection with a client when it is no loneger needed
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
     * Set the waiting game size
     * @param num number of players we want to have in the game
     */
    public synchronized void setGameSize(int num){
        System.out.println("[SERVER] Setting game size to "+num);
        waitingGameSize = num;
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
            numReq.setMessageType(Message.MessageType.NUMBER_PLAYERS_REQ);
            c.asyncSend(numReq);
        }
        if(waitingGameSize == -1){
            if(waitingConnection.size() == 0)
                waitingConnection.put(name, c);
            else{
                Message err = new Message();
                err.setStatus(Message.Status.ERROR);
                err.setErrorType(Message.ErrorType.LOBBY_FULL_ERROR);
                c.asyncSend(err);
            }
            return;
        }
        else
            waitingConnection.put(name, c);

        Message ok = new Message();
        ok.setStatus(Message.Status.OK);
        c.asyncSend(ok);

        if (waitingConnection.size() >= waitingGameSize) {
            System.out.println("[SERVER] Starting game...");
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

            activeGames.put(activeGames.size(), connections);
            waitingConnection.clear();

            for(int i=0;i<waitingGameSize;i++) {
                views.get(i).processPlayerCreation("", keys.get(i));
            }
            model.updateClientModel();
            waitingGameSize = -1;
            model.sendPlaceRequest(0);
            System.out.println("[SERVER] Let the games begin!");
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

