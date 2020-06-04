package it.polimi.ingsw.client;

import it.polimi.ingsw.client.cli.ClientCLI;
import it.polimi.ingsw.client.gui.ClientGUI;
import it.polimi.ingsw.client.interfaces.ClientServerInterface;
import it.polimi.ingsw.client.interfaces.ClientUserInterface;
import it.polimi.ingsw.connection.*;
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
    private boolean useCli;

    /**
     * Instantiates a new Client.
     *
     */
    public Client(boolean useCli) {
        this.useCli = useCli;
    }

    /**
     * Run.
     */
    public void run() {
        if (useCli) ui = new ClientCLI();
        else{
            ui = new ClientGUI();
            Thread guiThread = new Thread((Runnable) ui);
            guiThread.start();
        }

        // get data to start the game
        ui.setupInterface();
        ip = ui.getIP();
        port = ui.getPort();

        // setup connection with server
        connection = new ServerConnection(ip, port);
        // connectionThread = new Thread((Runnable) connection);
        // connectionThread.start();

        // Handshake: add the player to a new or existing game
        String playerName = ui.getPlayerName();
        boolean godSelected = false;
        Handshake handshake = new Handshake();
        handshake.setPlayerName(playerName);
        connection.sendMessage(handshake);
        while(!godSelected) {
            Message resp;
            try {
                resp = connection.receiveMessage();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return;
            }
            if (resp.getStatus() == Message.Status.ERROR) {
                ui.showError("Error while starting/joining game: ");
                ui.showError("[" + resp.getErrorType() + "] " + resp.getPayload());
                return;
            } else if (resp.getMessageType() == Message.MessageType.SETUP_REQ) {
                int num = ui.getPlayerNumber();
                List<String> gods = ui.selectAvailableGods(num);
                Message res = new Message();
                res.setStatus(Message.Status.OK);
                res.setMessageType(Message.MessageType.SETUP);
                StringBuilder payload = new StringBuilder(String.valueOf(num));
                for(String g : gods) payload.append(" ").append(g);
                res.setPayload(payload.toString());
                connection.sendMessage(res);
            }
            // What gods are available?
            else if(resp.getMessageType() == Message.MessageType.AVAILABLE_GODS){
                AvailableGodsMessage availableGodsMessage = (AvailableGodsMessage) resp;
                if(availableGodsMessage.getCurPlayer().equals(playerName)){
                    godSelected = true;
                    Message message = new Message();
                    message.setMessageType(Message.MessageType.GOD_CHOICE);
                    message.setPayload(ui.chooseGod(availableGodsMessage.getGods()));
                    connection.sendMessage(message);
                }
            }
        }

        System.out.println("Setup done, waiting for the game to start...");
        List<ActionType> lastAllowed = null;
        // Game: respond to server move requests
        while(true){
            // Read message from server
            Message serverMessage;
            try {
                serverMessage = connection.receiveMessage();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
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
                List<ActionType> allowedActions = gameState.getAllowedMoves(playerName);
                lastAllowed = allowedActions;
                if(gameState.getCurrentPlayer().equals(playerName))
                    makeAction(allowedActions);
            }
            // Error from server
            else if(serverMessage.getStatus() == Message.Status.ERROR){
                ui.showError(serverMessage.getPayload());
                // Did we mess up a move? No problem, enter it again
                if(serverMessage.getErrorType() == Message.ErrorType.MOVE_INVALID && lastAllowed != null)
                    makeAction(lastAllowed);
            }
            // We already chose a god, se we don't really care
            else if(serverMessage.getMessageType() == Message.MessageType.AVAILABLE_GODS){}
            // Success! Whatever that might mean
            else if(serverMessage.getStatus() == Message.Status.OK)
                ui.showMessage("Successful!");
            // You can never be sure
            else{
                System.err.println("[CLIENT] Unexpected message: "+serverMessage);
            }
        }
    }

    void makeAction(List<ActionType> allowedActions){
        ClientAction action = ui.getPlayerMove(allowedActions);
        ActionMessage actionMessage = new ActionMessage();
        actionMessage.setAction(action);
        connection.sendMessage(actionMessage);
    }
}
