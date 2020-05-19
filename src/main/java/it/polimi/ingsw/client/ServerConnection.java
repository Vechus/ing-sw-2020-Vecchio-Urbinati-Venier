package it.polimi.ingsw.client;

import it.polimi.ingsw.client.interfaces.ClientServerInterface;
import it.polimi.ingsw.connection.Handshake;
import it.polimi.ingsw.connection.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Queue;

public class ServerConnection implements ClientServerInterface {
    String ip;
    int port;
    private ObjectOutputStream socketOut;
    private ObjectInputStream socketIn;

    public ServerConnection(String ip, int port) {
        this.ip = ip;
        this.port = port;

        try {
            // Connect to server and setup streams
            Socket socket = new Socket(ip, port);
            socketOut = new ObjectOutputStream(socket.getOutputStream());
            socketIn = new ObjectInputStream(socket.getInputStream());
        }
        catch(IOException e){
            System.out.println("Error while communicating with server");
        }
    }

    private void sendObject(Object o) throws IOException {
        socketOut.writeObject(o);
        socketOut.flush();
    }

    @Override
    public Message handshake(String playerName) throws IOException, ClassNotFoundException {
        Handshake handshake = new Handshake();
        handshake.setPlayerName(playerName);
        sendObject(handshake);
        return receiveMessage();
    }

    @Override
    public Message receiveMessage() throws IOException, ClassNotFoundException {
        Object o = socketIn.readObject();
        return (Message) o;
    }

    /**
     * method that sends the object message
     * @param message
     */
    private synchronized void send(Object message) {
        try {
            socketOut.reset();
            socketOut.writeObject(message);
            socketOut.flush();
        } catch(IOException e){
            System.err.println(e.getMessage());
        }
    }

    /**
     * creates a new thread to send a messagge in order to not block the rest of the server while sent
     * @param message
     */
    @Override
    public void sendMessage(final Message message){
        new Thread(new Runnable() {
            @Override
            public void run() {
                send(message);
            }
        }).start();
    }
}
