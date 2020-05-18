package it.polimi.ingsw.server;

import it.polimi.ingsw.connection.Handshake;
import it.polimi.ingsw.connection.Message;
import it.polimi.ingsw.util.listeners.ConnectionListener;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;

public class SocketClientConnection implements ClientConnection, Runnable {

    private Socket socket;
    private ObjectOutputStream out;
    private Server server;
    private ConnectionListener listener;

    private boolean active = true;

    /**
     * setter fo SocketClientConnection
     * @param socket
     * @param server
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
     * method that sends the object message
     * @param message
     */
    private synchronized void send(Object message) {
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
        System.out.println("Deregistering client...");
        server.deregisterConnection(this);
        System.out.println("Done!");
    }

    /**
     * method that tells closes the connection and communicates it to the client
     */
    public synchronized void closeConnection() {
        send("Connection closed!");
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error when closing socket!");
        }
        active = false;
    }


    /**
     * creates a new thread to send a messagge in order to not block the rest of the server while sent
     * @param message
     */
    @Override
    public void asyncSend(final Object message){
        new Thread(new Runnable() {
            @Override
            public void run() {
                send(message);
            }
        }).start();
    }

    /**
     *method that reads the handshake
     */
    @Override
    public void run() {
        ObjectInputStream in;
        String name;
        try{
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            Handshake message = (Handshake) in.readObject();
            name = message.getPlayerName();
            server.lobby(this, name);
            while(isActive()){
                Message read = (Message) in.readObject();
                listener.onMessageReceived(read);
            }
        } catch (IOException | NoSuchElementException | ClassNotFoundException e) {
            System.err.println("Error!" + e.getMessage());
        }finally{
            close();
        }
    }
}