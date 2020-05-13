package it.polimi.ingsw.client;

import it.polimi.ingsw.client.cli.ClientCLI;
import it.polimi.ingsw.client.interfaces.ClientServerInterface;
import it.polimi.ingsw.client.interfaces.ClientUserInterface;
import it.polimi.ingsw.connection.ActionMessage;
import it.polimi.ingsw.connection.ActionRequestMessage;
import it.polimi.ingsw.connection.BoardStateMessage;
import it.polimi.ingsw.connection.Message;
import it.polimi.ingsw.util.ActionType;

import java.io.IOException;
import java.util.List;

/**
 * The type RemoteClient.
 */
public class Client {
    /**
     * The enum clientStateEnum describes the client as a Finite State Machine.
     */
    private String ip;
    private int port;
    private int gameID;
    private String playerName;
    private ClientBoard gameState;
    private ClientUserInterface ui;
    private ClientServerInterface connection;
    Thread uiThread, connectionThread;

    /**
     * Instantiates a new Client.
     *
     */
    public Client() {

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
     * Run.
     */
    public void run() {
        ui = new ClientCLI();
        uiThread = new Thread((Runnable) ui);
        uiThread.start();

        // get data to start the game
        ui.setupInterface();
        ip = ui.getIP();
        port = ui.getPort();

        // setup connection with server
        connection = new ServerConnection(ip, port);
        connectionThread = new Thread((Runnable) connection);
        connectionThread.start();

        // Handshake: add the player to a new or existing game
        String playerName = ui.getPlayerName();
        Message resp;
        try {
            resp = connection.handshake(playerName);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            closeThreads();
            return;
        }
        if(resp.getStatus() == Message.Status.ERROR){
            ui.showError("Error while starting/joining game: "+resp.getPayload());
            closeThreads();
            return;
        }

        // Choose god: to do

        // Game: respond to server move requests
        while(true){
            // Read message from server
            Message serverMessage;
            try {
                serverMessage = connection.receiveMessage();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                closeThreads();
                return;
            }

            // Game is over, pack it up!
            if(serverMessage.getMessageType() == Message.MessageType.GAME_END){
                String winnerName = serverMessage.getPayload();
                ui.gameOver(winnerName);
                break;
            }
            // Board state update
            else if(serverMessage.getMessageType() == Message.MessageType.BOARD_STATE){
                BoardStateMessage boardStateMessage = (BoardStateMessage) serverMessage;
                gameState = boardStateMessage.getGameState();
                ui.showGameState(gameState);
            }
            // Server wants us to make a move
            else if(serverMessage.getMessageType() == Message.MessageType.ACTION_REQUEST){
                ActionRequestMessage actionRequestMessage = (ActionRequestMessage) serverMessage;
                List<ActionType> allowedActions = actionRequestMessage.getAllowedActions();

                ClientAction action = ui.getPlayerMove(allowedActions);
                ActionMessage actionMessage = new ActionMessage();
                actionMessage.setAction(action);
                connection.sendMessage(actionMessage);

                Message ack;
                try {
                    ack = connection.receiveMessage();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    closeThreads();
                    return;
                }
                if(ack.getStatus() == Message.Status.ERROR)
                    ui.showError(ack.getPayload());
            }
            // You can never be sure
            else{
                System.err.println("Unexpected message type: "+serverMessage);
            }
        }
    }

    void closeThreads(){
        try {
            uiThread.join();
            connectionThread.join();
        }
        catch(InterruptedException e){
            System.out.println(e.getMessage());
        }
    }
}
