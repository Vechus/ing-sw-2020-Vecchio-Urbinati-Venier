package it.polimi.ingsw.client.interfaces;

import it.polimi.ingsw.client.ClientAction;
import it.polimi.ingsw.client.ClientBoard;
import it.polimi.ingsw.util.ActionType;

import java.util.List;

/**
 *Class that defines the methods that a client must have
 */
public interface ClientUserInterface {

    /**
     * Method that initiate of the interface
     * The first client to connect with the server is called the host.
     */
    void setupInterface();

    /**
     * Method that gets the IP from the user
     */
    String getIP();

    /**
     * Method that gets the Port from the user
     */
    int getPort();

    /**
     * Method that gets the Name of the Player from the user
     */
    String getPlayerName();

    /**
     * Method that gets the number of player wanted in the game.
     * This method is called only for the host.
     */
    int getPlayerNumber();

    /**
     * Method that allows the client to select which gods will be used in the game, based on the number of players of the game.
     * This method is called only for the host.
     */
    List<String> selectAvailableGods(int num);

    /**
     * Method that allows the client to choose their god from the list of available gods.
     */
    String chooseGod(List<String> gods);

    /**
     * Method that gives the user the list of the available action.
     * It also get the move made by the player.
     */
    ClientAction getPlayerMove(List<ActionType> allowedActions);

    /**
     * Method that sends the updated board to the clients.
     */
    void showGameState(ClientBoard gameState);

    /**
     * Method that signals a error to the user when an invalid move is made.
     */
    void showError(String message);

    /**
     * Method that signals a fatal error, due to connection problems or disconnection caused by other clients.
     */
    void showFatalError(String message);

    /**
     * Method that sends a message to the user.
     */
    void showMessage(String s);

    /**
     * Method that sends the outcome of the game when it is over.
     * @param winnerName This is the name of the player that won.
     */
    void gameOver(String winnerName);

}