package it.polimi.ingsw.client.interfaces;

import it.polimi.ingsw.client.ClientAction;
import it.polimi.ingsw.client.ClientBoard;
import it.polimi.ingsw.util.ActionType;

import java.util.List;

public interface ClientUserInterface {
    void setupInterface();
    String getIP();
    int getPort();
    String getPlayerName();
    int getGameID();

    ClientAction getPlayerMove(List<ActionType> allowedActions);
    void showGameState(ClientBoard gameState);
    void showError(String message);

    void gameOver(String winnerName);
}