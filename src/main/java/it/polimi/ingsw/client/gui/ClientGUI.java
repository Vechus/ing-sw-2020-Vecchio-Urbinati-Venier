package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.ClientAction;
import it.polimi.ingsw.client.ClientBoard;
import it.polimi.ingsw.client.events.ChangeSceneEvent;
import it.polimi.ingsw.client.events.CustomEvent;
import it.polimi.ingsw.client.events.CustomEventHandler;
import it.polimi.ingsw.client.events.NewAllowedActionsEvent;
import it.polimi.ingsw.client.gui.game.GameScene;
import it.polimi.ingsw.client.interfaces.ClientUserInterface;
import it.polimi.ingsw.util.ActionType;
import it.polimi.ingsw.util.ConsoleColor;
import javafx.application.Application;
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

public class ClientGUI extends Application implements ClientUserInterface {
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
    private Parent mainMenu;
    private Parent credits;
    private FXMLLoader lobbyLoader;
    private LobbyController lobbyController;
    private Parent lobby;
    private FXMLLoader afterLobbyLoader;
    private AfterLobbyController afterLobbyController;
    private Parent afterLobby;
    private List<String> hostSelected;
    private String playerName;
    private String ip;
    private int port;
    private int playerNumber;
    private String playerGod;
    private boolean sync = false;
    private ClientAction playerAction;
    private GameScene gameScene;
    private Stage mainStage;

    public ClientGUI() { }

    @Override
    public void start(Stage stage) throws IOException {
        mainMenu = FXMLLoader.load(getClass().getResource("/scenes/MainMenu.fxml"));
        credits = FXMLLoader.load(getClass().getResource("/scenes/Credits.fxml"));
        lobbyLoader = new FXMLLoader(getClass().getResource("/scenes/Lobby.fxml"));
        afterLobbyLoader = new FXMLLoader(getClass().getResource("/scenes/AfterLobby.fxml"));

        mainStage = stage;
        Scene menuScene = new Scene(mainMenu, 1300, 750);
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
                    case "game" -> {
                        gameScene = new GameScene(stage);
                        stage.setScene(gameScene.getScene());
                    }
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
                afterLobbyController.displayGods(allGods);
                afterLobbyController.setNumber(playerNumber);
                menuScene.setRoot(afterLobby);
            }

            @Override
            public void onPlayerNameChange(String playerName) {
                setPlayerName(playerName);
                System.out.println(playerName);
                sync = true;
                lobbyController.showHostPane();
            }

            @Override
            public void onPlayerGodChange(String god) {
                playerGod = god;
                System.out.println(god);
                menuScene.setRoot(mainMenu);
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
            }
        });
        // testing only, REMOVE ON RELEASE
        stage.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent ->{
            if(keyEvent.getCode() == KeyCode.NUMPAD1) stage.fireEvent(new ChangeSceneEvent("game"));
            if(keyEvent.getCode() == KeyCode.NUMPAD4) {
                ArrayList<String> gods = new ArrayList<>();
                /*gods.add("Apollo");
                gods.add("Hera");
                gods.add("Minotaur");*/
                playerNumber = 2;
                afterLobbyController.displayGods(allGods);
                afterLobbyController.setNumber(playerNumber);
                menuScene.setRoot(afterLobby);
            }
            if(keyEvent.getCode() == KeyCode.NUMPAD5) {
                ArrayList<String> gods = new ArrayList<>();
                gods.add("Apollo");
                gods.add("Hera");

                playerNumber = 3;
                afterLobbyController.displayGods(gods);
                afterLobbyController.setNumber(playerNumber);
                menuScene.setRoot(afterLobby);
            }
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
        Application.launch();
    }

    @Override
    public String getIP() {
        while(true) {
            if(sync) {
                sync = false;
                return ip;
            }
        }
    }

    @Override
    public int getPort() {
        while(true) {
            if(sync) {
                sync = false;
                return port;
            }
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
        }
    }

    @Override
    public List<String> selectAvailableGods(int num) {
        mainStage.setScene(afterLobby.getScene());
        afterLobbyController.displayGods(allGods);
        afterLobbyController.setNumber(playerNumber);
        while(true) {
            if(sync) {
                sync = false;
                return hostSelected;
            }
        }
    }

    @Override
    public String chooseGod(List<String> gods) {
        mainStage.setScene(afterLobby.getScene());
        afterLobbyController.displayGods(gods);
        afterLobbyController.setNumber(playerNumber);
        while(true) {
            if(sync) {
                sync = false;
                return playerGod;
            }
        }
    }

    @Override
    public ClientAction getPlayerMove(List<ActionType> allowedActions) {
        // notify GameScene
        mainStage.fireEvent(new NewAllowedActionsEvent(allowedActions));
        while(true) {
            if(sync) {
                sync = false;
                return playerAction;
            }
        }
    }

    @Override
    public void showGameState(ClientBoard gameState) {
        gameScene.updateBoard(gameState);
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
        System.out.println(ConsoleColor.CYAN_BOLD_BRIGHT + winnerName);
    }

    /*public static void main(String[] args) {
        launch(args);
    }*/

    public String getPlayerName() {
        return playerName;
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
}
