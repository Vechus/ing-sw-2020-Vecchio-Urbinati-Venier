package it.polimi.ingsw.client;

import it.polimi.ingsw.client.interfaces.ClientServerInterface;
import it.polimi.ingsw.connection.Handshake;
import it.polimi.ingsw.connection.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Queue;

public class ClientConnection implements ClientServerInterface, Runnable {
    String ip;
    int port;
    private ObjectOutputStream socketOut;
    private ObjectInputStream socketIn;
    Queue<Message> outQueue;
    boolean run = true;

    public ClientConnection(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    /**
     * We need to send the messages to the server asynchronously.
     * Thus, we make a queue, which is iterated through and sent over in this thread.
     * Only this object (thus only this thread) can access sendObject
     */
    @Override
    public void run() {
        try {
            // Connect to server and setup streams
            Socket socket = new Socket(ip, port);
            socketIn = new ObjectInputStream(socket.getInputStream());
            socketOut = new ObjectOutputStream(socket.getOutputStream());

            while(run){
                if(outQueue.isEmpty()) continue;
                Message message = outQueue.remove();
                sendObject(message);
            }
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
    public Message handshake(String playerName, boolean host, int gameID) throws IOException, ClassNotFoundException {
        Handshake handshake = new Handshake();
        handshake.setGameId(gameID);
        handshake.setPlayerName(playerName);
        handshake.setType(host ? Handshake.HandshakeType.HOST : Handshake.HandshakeType.JOIN);
        sendObject(handshake);
        return receiveMessage();
    }

    @Override
    public Message receiveMessage() throws IOException, ClassNotFoundException {
        Object o = socketIn.readObject();
        socketOut.flush();
        return (Message) o;
    }

    @Override
    public void sendMessage(Message message) {
        outQueue.add(message);
    }

    public void stop(){
        run = false;
    }
}
