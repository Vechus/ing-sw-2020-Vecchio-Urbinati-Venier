package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.ClientStageEnum;
import it.polimi.ingsw.client.listeners.ClientStageListener;
import it.polimi.ingsw.util.ConsoleColor;

import java.util.Scanner;

public class ClientCLI implements ClientStageListener {
    private final Scanner stdin = new Scanner(System.in);
    private final ClientController clientController;

    public ClientCLI (ClientController clientController) {
        this.clientController = clientController;
        clientController.addClientStageListener(this);
    }

    private void mainMenu() {
        System.out.println("SANTORINI OFFICIAL GAME SPONSORED BY Java 14©");
        System.out.println("--------- Press a key to continue ---------");
        stdin.next();
        clientController.setPlayerName(getPlayerNamefromUser(stdin));
        clientController.setState(ClientStageEnum.NET_MENU);
    }

    private void netMenu() {
        String ans;
        System.out.println("Wanna host a new match?[y/n] ");
        ans = stdin.next();
        while (!ans.equals("y") && !ans.equals("n")) {
            System.out.println("Input error! Do you want to host a new match?[y/n] ");
            ans = stdin.next();
        }
        clientController.setHost(ans.equals("y"));
        clientController.setState(ClientStageEnum.HANDSHAKE);
    }

    private int getGameIDfromUser(final Scanner stdin) {
        System.out.println("Enter your game ID: ");
        int id = stdin.nextInt();
        while (id <= 0) {
            System.out.println(ConsoleColor.RED_UNDERLINED + "Invalid game ID, please re-enter your game ID: ");
            id = stdin.nextInt();
        }
        return id;
    }

    private String getPlayerNamefromUser(final Scanner stdin) {
        System.out.println("What is your name? ");
        return stdin.next();
    }

    public void stateHandshake() {
        if(clientController.isHost()) {
            clientController.setGameID(-1);
            clientController.constructHandshake(true);
        } else {
            clientController.setGameID(getGameIDfromUser(stdin));
            clientController.constructHandshake(false);
        }
        clientController.setState(ClientStageEnum.WAITING_HANDSHAKE);
    }

    public void doingHandshake() {
        System.out.print(".");
    }

    public void stateLobby() {
        System.out.println("You're in lobby.");
        if(clientController.isHost()) {
            System.out.println("You're the host, press start to start the match or write 'close' to close this lobby.");
            if(stdin.hasNext()) {
                System.out.println("Starting game");
                clientController.setState(ClientStageEnum.NOT_TURN);
            }
        } else {
            System.out.println("Waiting for match start...");
        }
    }

    public void stateTurn() {
        System.out.println("Your turn!");
        // TODO: manage turn
        /*
         - Print board
         - Ask move
         - loop until end turn
         */
    }

    public void stateNotTurn() {
        System.out.println("You end your turn.");
    }

    public void stateEnd() {
        System.out.println("The match has ended. Press start to back to the main menu.");
        stdin.next();
        clientController.setState(ClientStageEnum.MAIN_MENU);
    }

    @Override
    public void onClientStageChange(ClientStageEnum stage) {
        if(stage == ClientStageEnum.MAIN_MENU) {
            mainMenu();
        } else if (stage == ClientStageEnum.NET_MENU) {
            netMenu();
        } else if (stage == ClientStageEnum.HANDSHAKE) {
            stateHandshake();
        } else if (stage == ClientStageEnum.WAITING_HANDSHAKE) {
            doingHandshake();
        } else if (stage == ClientStageEnum.LOBBY) {
            stateLobby();
        } else if (stage == ClientStageEnum.TURN) {
            stateTurn();
        } else if (stage == ClientStageEnum.NOT_TURN) {
            stateNotTurn();
        } else if(stage == ClientStageEnum.END) {
            stateEnd();
        }
    }
}
