package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.god.God;
import it.polimi.ingsw.view.RemoteView;
import it.polimi.ingsw.view.View;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final int PORT = 12345;
    private ServerSocket serverSocket;
    private ExecutorService executor = Executors.newFixedThreadPool(128);
    private Map<String, ClientConnection> waitingConnection = new HashMap<>();
    private Model waitingModel;
    private Controller waitingController;
    private Map<Integer, List<ClientConnection>> activeGames = new HashMap<>();
    private int waitingGameSize = 2;

    //Deregister connection
    public synchronized void deregisterConnection(ClientConnection c) {
        ClientConnection opponent = waitingConnection.get(c);
        if(opponent != null) {
            opponent.closeConnection();
        }
        waitingConnection.remove(c);
        waitingConnection.remove(opponent);
        Iterator<String> iterator = waitingConnection.keySet().iterator();
        while(iterator.hasNext()){
            if(waitingConnection.get(iterator.next())==c){
                iterator.remove();
            }
        }
    }



    //Wait for another player
    public synchronized void lobby(ClientConnection c, String name){
        if(waitingConnection.size() == 0){
            // ask player number
        }
        waitingConnection.put(name, c);

        if (waitingConnection.size() == waitingGameSize) {
            List<String> keys = new ArrayList<>(waitingConnection.keySet());
            Model model = new Model();
            Controller controller = new Controller(model,waitingGameSize);
            List<View> views = new ArrayList<>();
            List<ClientConnection> connections = new ArrayList<>();
            for(int i=0;i<waitingGameSize;i++) {
                connections.add(waitingConnection.get(keys.get(i)));
                View view = new RemoteView(connections.get(i));
                views.add(view);
                model.addListener(view);
                view.addListener(controller);

            }

            activeGames.put(activeGames.size(), connections);
            waitingConnection.clear();

            for(int i=0;i<waitingGameSize;i++)
                views.get(i).processPlayerCreation(new God(model.getBoard()), keys.get(i));
        }
    }


    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
    }

    public void run(){
        while(true){
            try {
                Socket newSocket = serverSocket.accept();
                SocketClientConnection socketConnection = new SocketClientConnection(newSocket, this);
                executor.submit(socketConnection);
            } catch (IOException e) {
                System.out.println("Connection Error!");
            }
        }
    }
}

