package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.ClientBoard;
import it.polimi.ingsw.client.interfaces.ClientUserInterface;
import it.polimi.ingsw.client.ClientAction;
import it.polimi.ingsw.util.ActionType;
import it.polimi.ingsw.util.ConsoleColor;
import it.polimi.ingsw.util.Vector2;

import java.util.List;
import java.util.Scanner;

public class ClientCLI implements ClientUserInterface, Runnable {
    private final Scanner stdin = new Scanner(System.in);
    private String playerName;

    public ClientCLI () { }

    @Override
    public void run(){

    }

    @Override
    public void setupInterface() {
        System.out.println("SANTORINI OFFICIAL GAME SPONSORED BY Java 14©");
        System.out.println("--------- Press a key to continue ---------");
        stdin.next();
    }

    @Override
    public String getIP() {
        return "127.0.0.1";
    }

    @Override
    public int getPort() {
        return 27000;
    }

    @Override
    public String getPlayerName() {
        System.out.println("What is your name?");
        playerName = stdin.next();
        return playerName;
    }

    @Override
    public int getGameID() {
        System.out.println("Enter the ID of the match you want to join. Type -1 if you want to host a new match");
        return stdin.nextInt();
    }

    @Override
    public ClientAction getPlayerMove(List<ActionType> allowedActions) {
        System.out.println("What is your move?");
        return new ClientAction(new Vector2(0, 0), new Vector2(0, 0), allowedActions.get(0));
    }

    @Override
    public void showGameState(ClientBoard gameState) {

    }

    @Override
    public void showError(String message) {
        System.out.println(ConsoleColor.RED_BOLD + message + ConsoleColor.RESET);
    }

    @Override
    public void gameOver(String winnerName) {
        if(winnerName.equals(playerName))
            System.out.println("You won! Congratulations :D");
        else
            System.out.println("Game is over! "+winnerName+" won, better luck next time!");
    }
}
