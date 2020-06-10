package it.polimi.ingsw.server;

import it.polimi.ingsw.connection.Handshake;
import it.polimi.ingsw.connection.Message;
import it.polimi.ingsw.util.listeners.ConnectionListener;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class SocketClientConnection implements ClientConnection, Runnable {

    private Socket socket;
    private ObjectOutputStream out;
    private Server server;
    private ConnectionListener listener;
    private boolean active = true;

    /**
     * Create a new SocketClientConnection
     *
     * @param socket the socket the connection is using
     * @param server the server that manages the game
     */
    public SocketClientConnection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    /**
     * getter of param active
     */
    private synchronized boolean isActive(){
        return active;
    }


    /**
     * setter of param litener
     */
    @Override
    public void addListener(ConnectionListener listener) {
        this.listener=listener;
    }


    /**
     * Method that sends the object message.
     * This method is blocking.
     *
     * @param message the object to send
     */
    public synchronized void send(Object message) {
        try {
            out.reset();
            out.writeObject(message);
            out.flush();
        } catch(IOException e){
            System.err.println(e.getMessage());
        }
    }

    /**
     * methods that calls closeConnection and eliminates the connection frm the server
     */
    private void close() {
        closeConnection();
        System.out.println("[CONNECTION] Deregistering client...");
        server.deregisterConnection(this);
    }

    /**
     * Closes the connection and removes it from the server
     */
    public synchronized void closeConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error when closing socket!");
        }
        active = false;
    }


    /**
     * Sends the message to the client asynchronously
     *
     * @param message the object to send
     */
    @Override
    public void asyncSend(final Object message){
        new Thread(() -> send(message)).start();
    }

    /**
     *method that reads the handshake
     */
    @Override
    public void run() {
        ObjectInputStream in;
        String name;
        try{
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            Handshake message = (Handshake) in.readObject();
            name = message.getPlayerName();
            server.lobby(this, name);
            while(isActive()){
                Message read = (Message) in.readObject();
                if(read.getMessageType() == Message.MessageType.SETUP) {
                    String payload = read.getPayload();
                    int num = Integer.parseInt(read.getPayload().substring(0, 1));
                    server.setGameSize(num);
                    List<String> gods = new ArrayList<>();
                    for(String s : payload.split(" "))
                        if(s.length() > 1) gods.add(s);
                    System.out.println("Received "+gods);
                    server.setGods(gods);
                }
                else if(read.getMessageType() == Message.MessageType.GOD_CHOICE)
                    server.selectGod(name, read.getPayload());
                else
                    listener.onMessageReceived(read);
            }
        } catch (IOException | NoSuchElementException | ClassNotFoundException e) {
            System.err.println("Error! " + e.getMessage());
        }finally{
            close();
        }
    }
}