package it.polimi.ingsw.client;

import it.polimi.ingsw.connection.Handshake;
import it.polimi.ingsw.connection.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {
    public enum clientStateEnum {HANDSHAKE, WAITING_HANDSHAKE, LOBBY, TURN, NOT_TURN};
    private final String ip;
    private final int port;
    private boolean active = true;
    private int gameID;
    private clientStateEnum state = clientStateEnum.HANDSHAKE;
    private String playerName;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public synchronized void setState(clientStateEnum state) {
        this.state = state;
    }

    public synchronized clientStateEnum getState() {
        return this.state;
    }

    public synchronized int getGameID() {
        return gameID;
    }

    public synchronized void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public synchronized String getPlayerName() {
        return playerName;
    }

    public synchronized void setPlayerName(String playerName) {
        this.playerName = playerName;
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
                    handleRead(inputObject);
                }
            } catch (Exception e){
                setActive(false);
            }
        });
        t.start();
        return t;
    }

    public Thread asyncWriteToSocket(final Scanner stdin, final ObjectOutputStream socketOut){
        Thread t = new Thread(() -> {
            try {
                while (isActive()) {
                    handleWrite(stdin, socketOut);
                }
            }catch(Exception e){
                setActive(false);
                System.err.println(e.getMessage());
            }
        });
        t.start();
        return t;
    }

    public void run() throws IOException {
        Socket socket = new Socket(ip, port);
        System.out.println("Connection established with " + ip + ":" + port);
        ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream socketOut = new ObjectOutputStream(socket.getOutputStream());
        Scanner keyboardInput = new Scanner(System.in);

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

    private void handleRead(final Object inputObject) {
        clientStateEnum s = getState();
        if(s == clientStateEnum.WAITING_HANDSHAKE) {
            // manage reading of handshake server answer
            if(!(inputObject instanceof Handshake)) throw new IllegalArgumentException();
            Handshake answer = (Handshake) inputObject;
            if(answer.getStatus() == Message.status.OK) {
                // server answered with an OK status Handshake. We can proceed to LOBBY state.
                setState(clientStateEnum.LOBBY);
                System.out.println("Handshake with server successful.");
                if(answer.getType() == Handshake.handshakeType.HOST) {
                    // this client sent a HOST handshake
                    setGameID(answer.getGameId());
                }
                System.out.println("You're now in lobby with game ID: " + getGameID() + "\nWhen you're ready, just write 'start': ");
            } else if(answer.getStatus() == Message.status.ERROR){
                if(answer.getPayloadType() == Message.payloadType.GAME_ID_INVALID_ERROR) {
                    // this client sent a JOIN handshake, and the gameID is not valid (server replied with ERROR status)
                    System.out.println("Invalid Game ID.");
                    setState(clientStateEnum.HANDSHAKE);
                } else if(answer.getPayloadType() == Message.payloadType.LOBBY_FULL_ERROR) {
                    // lobby is full
                    System.out.println("Lobby is full.");
                    setState(clientStateEnum.HANDSHAKE);
                }
            }
        }
        // TODO: cover other cases
    }

    private void handleWrite(final Scanner stdin, final ObjectOutputStream out) {
        clientStateEnum s = getState();
        if(s == clientStateEnum.HANDSHAKE) {
            // manage handshake
            Handshake handshake = new Handshake();
            boolean newGame = mainMenu(stdin);
            if (playerName == null) {
                setPlayerName(getPlayerNamefromUser(stdin));
            }
            if(newGame) {
                handshake.setGameId(-1);
                handshake.setType(Handshake.handshakeType.HOST);
            } else {
                setGameID(getGameIDfromUser(stdin));
                handshake.setGameId(getGameID());
                handshake.setType(Handshake.handshakeType.JOIN);
            }
            handshake.setPlayerName(getPlayerName());

            System.out.println("Handshake with host...");
            sendObject(out, handshake);
            setState(clientStateEnum.WAITING_HANDSHAKE);

        } else if(s == clientStateEnum.LOBBY) {
            // manage lobby behaviour
            if(stdin.next().equals("start")) setState(clientStateEnum.NOT_TURN);
        } else if(s == clientStateEnum.TURN) {
            // manage turn behaviour
        } else if(s == clientStateEnum.NOT_TURN) {
            // manage not in turn behaviour
        } else if(s == clientStateEnum.WAITING_HANDSHAKE){
            // waiting
            System.out.print(".");
        } else {
            System.err.println("clientStateEnum FAILURE: INVALID STATE");
        }
    }

    private boolean mainMenu(final Scanner input) {
        System.out.println("SANTORINI OFFICIAL GAME SPONSORED BY Java 13©");
        System.out.println("Wanna host a new match?[y/n] ");
        String ans = input.next();
        while (!ans.equals("y") && !ans.equals("n")) {
            System.out.println("Input error! Do you want to host a new match?[y/n] ");
            ans = input.next();
        }
        return ans.equals("y");
    }

    private int getGameIDfromUser(final Scanner stdin) {
        System.out.println("Enter your game ID: ");
        int id = stdin.nextInt();
        while (id <= 0) {
            System.out.println("Invalid game ID, please re-enter your game ID: ");
            id = stdin.nextInt();
        }
        return id;
    }

    private String getPlayerNamefromUser(final Scanner stdin) {
        System.out.println("What is your name? ");
        return stdin.next();
    }

    private void sendObject(ObjectOutputStream socketOut, Object o) {
        try {
            socketOut.writeObject(o);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
