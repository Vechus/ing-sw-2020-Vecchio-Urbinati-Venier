package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.ClientAction;
import it.polimi.ingsw.client.ClientBoard;
import it.polimi.ingsw.client.events.ChangeSceneEvent;
import it.polimi.ingsw.client.events.CustomEvent;
import it.polimi.ingsw.client.events.CustomEventHandler;
import it.polimi.ingsw.client.gui.game.GameScene;
import it.polimi.ingsw.client.interfaces.ClientUserInterface;
import it.polimi.ingsw.util.ActionType;
import it.polimi.ingsw.util.ConsoleColor;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientGUI extends Application implements ClientUserInterface, Runnable {
    private static final List<String> allGods = new ArrayList<>() {{
        add("Apollo");
        add("Artemis");
        add("Athena");
        add("Atlas");
        add("Cronus");
        add("Demeter");
        add("Hephaestus");
        add("Hera");
        add("Hestia");
        add("Minotaur");
        add("Pan");
        add("Prometheus");
        add("Triton");
        add("Zeus");
    }};
    private static Parent mainMenu;
    private static Parent credits;
    private static FXMLLoader lobbyLoader;
    private static LobbyController lobbyController;
    private static Parent lobby;
    private static FXMLLoader afterLobbyLoader;
    private static AfterLobbyController afterLobbyController;
    private static Parent afterLobby;
    private static List<String> hostSelected;
    private static String playerName;
    private static String ip;
    private static int port;
    private static int playerNumber;
    private static String playerGod;
    private static boolean sync = false;
    private static ClientAction playerAction;
    private static GameScene gameScene;
    private static Stage mainStage;
    private static Scene menuScene;

    public ClientGUI() {
        initParameters();
    }

    @Override
    public void start(Stage stage) throws IOException {
        mainMenu = FXMLLoader.load(getClass().getResource("/scenes/MainMenu.fxml"));
        credits = FXMLLoader.load(getClass().getResource("/scenes/Credits.fxml"));
        lobbyLoader = new FXMLLoader(getClass().getResource("/scenes/Lobby.fxml"));
        afterLobbyLoader = new FXMLLoader(getClass().getResource("/scenes/AfterLobby.fxml"));

        mainStage = stage;
        menuScene = new Scene(mainMenu, 1300, 750);
        lobby = lobbyLoader.load();
        lobbyController = lobbyLoader.getController();
        afterLobby = afterLobbyLoader.load();
        afterLobbyController = afterLobbyLoader.getController();

        stage.addEventHandler(CustomEvent.CUSTOM_EVENT_TYPE, new CustomEventHandler() {
            @Override
            public void onSceneChange(String sceneName) {
                switch (sceneName) {
                    case "credits" -> menuScene.setRoot(credits);
                    case "menu" -> {
                        initParameters();
                        menuScene.setRoot(mainMenu);
                    }
                    case "lobby" -> menuScene.setRoot(lobby);
                    case "gameToMenu" -> {
                        menuScene.setRoot(mainMenu);
                        initParameters();
                        stage.setScene(menuScene);
                    }
                }
            }

            @Override
            public void onPlayerIpPortChange(Pair<String, Integer> input) {
                ip = input.getKey();
                port = input.getValue();
                System.out.println(ip + ":" + port);
                sync = true;
            }

            @Override
            public void onPlayerNumberChange(int num) {
                playerNumber = num;
                System.out.println(num);
                sync = true;
            }

            @Override
            public void onPlayerNameChange(String playerName) {
                setPlayerName(playerName);
                System.out.println(playerName);
                sync = true;
            }

            @Override
            public void onPlayerGodChange(String god) {
                playerGod = god;
                System.out.println(god);
                menuScene.setRoot(lobby);
                sync = true;
            }

            @Override
            public void onHostSelectGods(List<String> gods) {
                hostSelected = gods;
                gods.forEach(System.out::println);
                menuScene.setRoot(lobby);
                sync = true;
            }

            @Override
            public void onPlayerAction(ClientAction action) {
                playerAction = action;
                sync = true;
                System.out.println("New action: " + action.getType().name() + " from " + action.getFrom().toString() + " to " + action.getTo().toString());
            }
        });
        // testing only, REMOVE ON RELEASE
        stage.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent ->{
            if(keyEvent.getCode() == KeyCode.NUMPAD1) stage.fireEvent(new ChangeSceneEvent("game"));
        });

        stage.setTitle("Santorini - GC18");
        stage.setScene(menuScene);
        stage.show();
    }

    public void initParameters() {
        playerName = null;
        ip = null;
        port = 0;
        playerNumber = 0;
        playerAction = null;
    }

    @Override
    public void setupInterface() {

    }

    void loopWait(){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getIP() {
        while(true) {
            if(sync) {
                return ip;
            }
            loopWait();
        }
    }

    @Override
    public int getPort() {
        while(true) {
            if(sync) {
                sync = false;
                return port;
            }
            loopWait();
        }
    }

    @Override
    public int getPlayerNumber() {
        lobbyController.showHostPane();
        while(true) {
            if(sync) {
                sync = false;
                return playerNumber;
            }
            loopWait();
        }
    }

    @Override
    public List<String> selectAvailableGods(int num) {
        Platform.runLater(() -> {
            afterLobbyController.displayGods(allGods);
            afterLobbyController.setNumber(playerNumber);
            menuScene.setRoot(afterLobby);
        });
        while(true) {
            if(sync) {
                sync = false;
                return hostSelected;
            }
            loopWait();
        }
    }

    @Override
    public String chooseGod(List<String> gods) {
        Platform.runLater(() -> {
            afterLobbyController.displayGods(gods);
            afterLobbyController.setNumber(playerNumber);
            menuScene.setRoot(afterLobby);
        });
        while(true) {
            if(sync) {
                sync = false;
                return playerGod;
            }
            loopWait();
        }
    }

    @Override
    public ClientAction getPlayerMove(List<ActionType> allowedActions) {
        // notify GameScene
        Platform.runLater(() -> gameScene.getAllowedActions(allowedActions));
        while(true) {
            if(sync) {
                sync = false;
                return playerAction;
            }
            loopWait();
        }
    }

    @Override
    public void showGameState(ClientBoard gameState) {
        Platform.runLater(() -> {
            if(gameScene == null) {
                gameScene = new GameScene(mainStage);
                gameScene.setPlayerGod(playerGod);
                gameScene.setPlayerName(playerName);
                mainStage.setScene(gameScene.getScene());
            }
            gameScene.updateBoard(gameState);
        });
    }

    @Override
    public void showError(String message) {
        System.err.println(message);
    }

    @Override
    public void showMessage(String s) {
        System.out.println(ConsoleColor.GREEN + s);
    }

    @Override
    public void gameOver(String winnerName) {
        System.out.println(ConsoleColor.CYAN_BOLD_BRIGHT + winnerName + " won!");
    }

    public String getPlayerName() {
        while(true) {
            if(sync) {
                sync = false;
                return playerName;
            }
            loopWait();
        }
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Parent getMainMenu() {
        return mainMenu;
    }

    public Parent getCredits() {
        return credits;
    }

    public Parent getLobby() {
        return lobby;
    }

    public GameScene getGameScene() {
        return gameScene;
    }

    @Override
    public void run() {
        Application.launch();
    }
}
