package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.ClientAction;
import it.polimi.ingsw.client.ClientBoard;
import it.polimi.ingsw.client.interfaces.ClientUserInterface;
import it.polimi.ingsw.util.ActionType;
import it.polimi.ingsw.util.ConsoleColor;
import it.polimi.ingsw.util.Vector2;

import java.util.*;

/**
 * Handles the client on the command-line interface.
 */
public class ClientCLI implements ClientUserInterface, Runnable {
    private final Scanner stdin = new Scanner(System.in);
    private String playerName;
    private ClientBoard latestState;
    private List<String> playerColors = new ArrayList<>(), playerColorsUnderlined = new ArrayList<>();

    private Map<ActionType, String> actionNames = new HashMap<>();

    private Vector2 workerPos = null, nextPos = null;

    public ClientCLI () {
        playerColors.add(ConsoleColor.RED);
        playerColors.add(ConsoleColor.GREEN);
        playerColors.add(ConsoleColor.BLUE);
        playerColorsUnderlined.add(ConsoleColor.RED_UNDERLINED);
        playerColorsUnderlined.add(ConsoleColor.GREEN_UNDERLINED);
        playerColorsUnderlined.add(ConsoleColor.BLUE_UNDERLINED);

        actionNames.put(ActionType.PLACE_WORKER, "Place worker");
        actionNames.put(ActionType.MOVE, "Move");
        actionNames.put(ActionType.BUILD, "Build block");
        actionNames.put(ActionType.BUILD_DOME, "Build dome");
        actionNames.put(ActionType.END_TURN, "Finish turn");
    }

    @Override
    public void run(){

    }

    @Override
    public void setupInterface() {
        System.out.println("SANTORINI OFFICIAL GAME SPONSORED BY Java 14©");
        System.out.println("/------ By ------\\");
        System.out.println("| Daria Urbinati |");
        System.out.println("|  Luca Vecchio  |");
        System.out.println("| Daniele Venier |");
        System.out.println("\\----------------/");
        System.out.println();
    }

    @Override
    public String getIP() {
        System.out.println("Enter the server address. [default=127.0.0.1]");
        return stdin.next();
    }

    @Override
    public int getPort() {
        System.out.println("Which port are you using? [default=27000]");
        return stdin.nextInt();
    }

    @Override
    public String getPlayerName() {
        System.out.println("What is your name?");
        playerName = stdin.next();
        return playerName;
    }

    @Override
    public int getPlayerNumber() {
        System.out.println("How many players do you want the match to have? [Enter 2 or 3]");
        int num;
        do num = stdin.nextInt();
        while(num != 2 && num != 3);
        return num;
    }

    @Override
    public List<String> selectAvailableGods(int num) {
        List<String> gods = Arrays.asList("Apollo", "Artemis", "Athena", "Atlas", "Cronus", "Demeter",
                "Hephaestus", "Hera", "Hestia", "Minotaur", "Pan", "Prometheus", "Triton", "Zeus");
        System.out.println("Select "+num+" gods [Enter 1-"+gods.size()+" "+num+" times]");
        displaySelectionList(gods);
        List<String> sel = new ArrayList<>();
        while(sel.size() < num){
            String g = gods.get(chooseInRange(gods.size()));
            if(sel.contains(g))
                System.out.println("God "+g+" has already been selected, choose another");
            else sel.add(g);
        }
        return sel;
    }

    /**
     * Displays list of strings.
     * @param list List of strings to be displayed.
     */
    void displaySelectionList(List<String> list){
        for(int i=0;i<list.size();i++)
            System.out.println("("+(i+1)+") "+list.get(i));
    }

    /**
     *Waits for a integer until it is within range.
     * @param max
     * @return the integer that is within range.
     */
    int chooseInRange(int max){
        int id;
        do id = stdin.nextInt() - 1;
        while(id < 0 || id >= max);
        return id;
    }

    @Override
    public String chooseGod(List<String> gods) {
        System.out.println("Choose a god among the following: [Enter 1-"+gods.size());
        displaySelectionList(gods);
        return gods.get(chooseInRange(gods.size()));
    }

    @Override
    public ClientAction getPlayerMove(List<ActionType> allowedActions) {
        if(allowedActions.size() == 1 && allowedActions.get(0) == ActionType.END_TURN)
            return new ClientAction(null, null, ActionType.END_TURN);
        // Choose type of action
        System.out.println("What is your move? [Enter 1 to "+allowedActions.size()+"]");
        List<String> strings = new ArrayList<>();
        for (ActionType allowedAction : allowedActions)
            strings.add(actionNames.get(allowedAction));
        displaySelectionList(strings);
        int type = chooseInRange(strings.size());
        if(allowedActions.get(type) == ActionType.END_TURN) return new ClientAction(null, null, ActionType.END_TURN);
        // Get starting pos
        Vector2 start;
        if(workerPos != null) {
            System.out.println("Using worker at "+workerPos.getX()+","+workerPos.getY());
            start = workerPos;
        }
        else{
            if (allowedActions.get(type) == ActionType.PLACE_WORKER)
                start = null;
            else{
                System.out.println("Which worker do you want to use? [1 - 2]");
                List<Vector2> pos = new ArrayList<>();
                List<String> str = new ArrayList<>();
                for(int i=0;i<5;i++)
                    for(int j=0;j<5;j++){
                        int playerId = latestState.getWorkerPlayer(new Vector2(i, j));
                        if(playerId != -1 && latestState.getPlayerNames().get(playerId).equals(playerName)) {
                            pos.add(new Vector2(i, j));
                            str.add("(" + i + " " + j + ")");
                        }
                    }
                displaySelectionList(str);
                start = pos.get(chooseInRange(2));
            }
        }
        System.out.println("What is your target? [Enter x and y coords, 0-4]");
        int xf, yf;
        do{
            xf = stdin.nextInt();
            yf = stdin.nextInt();
        } while(xf < 0 || xf >= 5 || yf < 0 || yf >= 5);
        if(allowedActions.get(type) == ActionType.MOVE)
            nextPos = new Vector2(xf, yf);
        else if(allowedActions.get(type) == ActionType.PLACE_WORKER)
            nextPos = null;
        else nextPos = start;
        return new ClientAction(start, new Vector2(xf, yf), allowedActions.get(type));
    }

    @Override
    public void showGameState(ClientBoard gameState) {
        if(!gameState.getCurrentPlayer().equals(playerName)) {
            workerPos = null;
            nextPos = null;
        }
        else if(nextPos != null)
            workerPos = nextPos;
        latestState = gameState;
        String block = ConsoleColor.WHITE_BACKGROUND + ConsoleColor.WHITE_BOLD + "█" + ConsoleColor.RESET;
        List<String> names = gameState.getPlayerNames();
        System.out.print("Players: ");
        for(int i=0;i<names.size();i++) {
            String player = names.get(i) + "(" + gameState.getGod(names.get(i)) + ")";
            if (names.get(i).equals(playerName))
                System.out.print(playerColorsUnderlined.get(i) + player + ConsoleColor.RESET + " ");
            else
                System.out.print(playerColors.get(i) + player + ConsoleColor.RESET + " ");
        }
        System.out.println();
        System.out.println("  " + block.repeat(21));
        for(int i = 0; i < 5; i++) {
            StringBuilder topRow = new StringBuilder("  " + block);
            StringBuilder centralRow = new StringBuilder("  " + block);
            StringBuilder bottomRow = new StringBuilder("  " + block);
            for(int j = 0; j < 5; j++) {
                Vector2 pos = new Vector2(i,j);
                if(gameState.getWorkerPlayer(pos) == -1 && !gameState.getDome(pos)) {
                    topRow.append("   ").append(block);
                    centralRow.append(" ").append(gameState.getHeight(pos)).append(" ").append(block);
                    bottomRow.append("   ").append(block);
                } else if(gameState.getWorkerPlayer(pos) == -1 && gameState.getDome(pos) && gameState.getHeight(pos) == 3) {
                    topRow.append(ConsoleColor.WHITE_BACKGROUND_BRIGHT).append(ConsoleColor.BLACK_BOLD_BRIGHT).append("╭─╮").append(ConsoleColor.RESET).append(block);
                    centralRow.append(ConsoleColor.WHITE_BACKGROUND_BRIGHT).append(ConsoleColor.BLACK_BOLD_BRIGHT).append("│").append(gameState.getHeight(pos)).append("│").append(ConsoleColor.RESET).append(block);
                    bottomRow.append("   ").append(block);
                } else if(gameState.getWorkerPlayer(pos) == -1 && gameState.getDome(pos) && gameState.getHeight(pos) != 3){
                    topRow.append(ConsoleColor.WHITE_BACKGROUND_BRIGHT).append(ConsoleColor.BLACK_BOLD_BRIGHT).append("╭─╮").append(ConsoleColor.RESET).append(block);
                    centralRow.append(ConsoleColor.WHITE_BACKGROUND_BRIGHT).append(ConsoleColor.BLACK_BOLD_BRIGHT).append("│").append(gameState.getHeight(pos)).append("│").append(ConsoleColor.RESET).append(block);
                    bottomRow.append("   ").append(block);
                } else {
                    topRow.append("   ").append(block);
                    centralRow.append(" ").append(playerColors.get(gameState.getWorkerPlayer(pos))).append(gameState.getHeight(pos)).append(ConsoleColor.RESET).append(" ").append(block);
                    bottomRow.append("   ").append(block);
                }
            }
            System.out.println(topRow);
            System.out.println(centralRow);
            System.out.println(bottomRow);
            System.out.println("  " + block.repeat(21));
        }
    }

    @Override
    public void showError(String message) {
        System.out.println(ConsoleColor.RED_BOLD + message + ConsoleColor.RESET);
    }

    @Override
    public void showFatalError(String message) {
        System.out.println(ConsoleColor.RED_BOLD + message + ConsoleColor.RESET);
        System.out.println("Press enter to exit the game...");
        stdin.next();
        System.exit(0);
    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void gameOver(String winnerName) {
        if(winnerName.equals(""))
            System.out.println("Nobody won! Game is a draw");
        else if(winnerName.equals(playerName))
            System.out.println("You won! Congratulations :D");
        else
            System.out.println("Game is over! "+winnerName+" won, better luck next time!");
    }
}
