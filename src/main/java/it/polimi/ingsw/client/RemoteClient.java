package it.polimi.ingsw.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * The type RemoteClient.
 */
public class RemoteClient {
    /**
     * The enum clientStateEnum describes the client as a Finite State Machine.
     */
    private final String ip;
    private final int port;
    private ObjectOutputStream socketOut;
    private ObjectInputStream socketIn;
    private boolean active = true;
    private final ClientController clientController;
    private int gameID;
    private String playerName;

    /**
     * Instantiates a new RemoteClient.
     *
     * @param ip   the ip
     * @param port the port
     */
    public RemoteClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.clientController = new ClientController(this);
    }

    /**
     * Gets game id.
     *
     * @return the game id
     */
    public synchronized int getGameID() {
        return gameID;
    }

    /**
     * Sets game id.
     *
     * @param gameID the game id
     */
    public synchronized void setGameID(int gameID) {
        this.gameID = gameID;
    }

    /**
     * Gets player name.
     *
     * @return the player name
     */
    public synchronized String getPlayerName() {
        return playerName;
    }

    /**
     * Sets player name.
     *
     * @param playerName the player name
     */
    public synchronized void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Is active boolean.
     *
     * @return active flag
     */
    public synchronized boolean isActive() {
        return this.active;
    }

    /**
     * Sets active.
     *
     * @param active the active flag
     */
    public synchronized void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Async read from socket thread.
     *
     * @param socketIn the socket in
     * @return the thread
     */
    public Thread asyncReadFromSocket(final ObjectInputStream socketIn) {
        Thread t = new Thread(() -> {
            try {
                while(isActive()) {
                    Object inputObject = socketIn.readObject();
                    clientController.handleIn(inputObject);
                }
            } catch (Exception e){
                setActive(false);
            }
        });
        t.start();
        return t;
    }

    /*
    public Thread asyncWriteToSocket(final ObjectOutputStream socketOut){
        Thread t = new Thread(() -> {
            try {
                while (isActive()) {

                }
            }catch(Exception e){
                setActive(false);
                System.err.println(e.getMessage());
            }
        });
        t.start();
        return t;
    }*/

    /**
     * Run.
     *
     * @throws IOException if crashes
     */
    public void run() throws IOException {
        Socket socket = new Socket(ip, port);
        System.out.println("Connection established with " + ip + ":" + port);
        this.socketIn = new ObjectInputStream(socket.getInputStream());
        this.socketOut = new ObjectOutputStream(socket.getOutputStream());
        Scanner keyboardInput = new Scanner(System.in);

        try {
            Thread in = asyncReadFromSocket(socketIn);
            //Thread out = asyncWriteToSocket(socketOut);
            in.join();
            //out.join();
        } catch (InterruptedException | NoSuchElementException e) {
            System.err.println(e.getMessage());
        } finally {
            keyboardInput.close();
            socketIn.close();
            socketOut.close();
            socket.close();
        }
    }

    public void sendObject(ObjectOutputStream socketOut, Object o) {
        try {
            socketOut.writeObject(o);
            socketOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObjectOutputStream getSocketOut() {
        return socketOut;
    }

    public ObjectInputStream getSocketIn() {
        return socketIn;
    }
}
