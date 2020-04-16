package it.polimi.ingsw.view.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {
    private final String ip;
    private final int port;
    private boolean active = true;
    private int gameID;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public synchronized boolean isActive() {
        return this.active;
    }

    public synchronized void setActive(boolean active) {
        this.active = active;
    }

    public Thread asyncReadFromSocket(final ObjectInputStream socketIn) {
        Thread t = new Thread(() -> {
            try {
                while(isActive()) {
                    Object inputObject = socketIn.readObject();
                    if(inputObject instanceof String) System.out.println((String)inputObject);
                    else throw new IllegalArgumentException();
                }
            } catch (Exception e){
                setActive(false);
            }
        });
        t.start();
        return t;
    }

    public Thread asyncWriteToSocket(final Scanner stdin, final PrintWriter socketOut){
        Thread t = new Thread(() -> {
            try {
                while (isActive()) {
                    String inputLine = stdin.nextLine();
                    socketOut.println(inputLine);
                    socketOut.flush();
                }
            }catch(Exception e){
                setActive(false);
                System.err.println(e.getMessage());
            }
        });
        t.start();
        return t;
    }

    public void run(boolean isNewGame) throws IOException {
        Socket socket = new Socket(ip, port);
        System.out.println("Connection established with " + ip + ":" + port);
        ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
        Scanner keyboardInput = new Scanner(System.in);

        /* TODO: define behaviour
        if(isNewGame) {
            // contact server and tell him that the client wants to create a new game
            socketOut.println("newGameRequest");
            gameID = socketIn.readInt();
        } else {
            // contact sever and tell him that the client wants to join an existing game
            System.out.println("Input your game ID: ");
            socketOut.println(keyboardInput.nextInt()); // todo: handle errors (FIRST define all behaviour server-side)
        }
        */

        try {
            Thread in = asyncReadFromSocket(socketIn);
            Thread out = asyncWriteToSocket(keyboardInput, socketOut);
            in.join();
            out.join();
        } catch (InterruptedException | NoSuchElementException e) {
            System.err.println(e.getMessage());
        } finally {
            keyboardInput.close();
            socketIn.close();
            socketOut.close();
            socket.close();
        }
    }
}
