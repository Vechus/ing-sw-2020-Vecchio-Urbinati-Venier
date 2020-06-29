package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.ClientAction;
import it.polimi.ingsw.client.ClientBoard;
import it.polimi.ingsw.client.events.CustomEvent;
import it.polimi.ingsw.client.events.CustomEventHandler;
import it.polimi.ingsw.client.gui.game.GameScene;
import it.polimi.ingsw.client.interfaces.ClientUserInterface;
import it.polimi.ingsw.util.ActionType;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;
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
    private boolean isLobby;

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
        menuScene = new Scene(mainMenu, 1200, 700);
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
                System.out.println("Connecting to" + ip + ":" + port);
                sync = true;
            }

            @Override
            public void onPlayerNumberChange(int num) {
                playerNumber = num;
                System.out.println(num + " players selected.");
                sync = true;
            }

            @Override
            public void onPlayerNameChange(String playerName) {
                setPlayerName(playerName);
                System.out.println("You entered username: " + playerName);
                sync = true;
            }

            @Override
            public void onPlayerGodChange(String god) {
                playerGod = god;
                System.out.println("You selected god: " + god);
                menuScene.setRoot(lobby);
                sync = true;
            }

            @Override
            public void onHostSelectGods(List<String> gods) {
                hostSelected = gods;
                System.out.println("You are the host. You are selecting the gods:");
                gods.forEach(System.out::println);
                menuScene.setRoot(lobby);
                sync = true;
            }

            @Override
            public void onPlayerAction(ClientAction action) {
                if(gameScene.getClientGameStage3D() == GameScene.ClientGameStage3D.ACTION)
                    gameScene.setClientGameStage3D(GameScene.ClientGameStage3D.WAIT);
                gameScene.unselectAll();
                playerAction = action;
                sync = true;
            }
        });

        stage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
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
        isLobby = true;
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
                isLobby = false;
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
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Something is wrong!");
            alert.setContentText(message);

            alert.showAndWait();
        });
    }

    @Override
    public void showFatalError(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error dialog");
            alert.setHeaderText("Fatal error!");
            alert.setContentText(message);

            alert.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> {
                        Platform.exit();
                        System.exit(0);
                    });;
        });
    }

    @Override
    public void showMessage(String s) {
        Platform.runLater(() -> gameScene.displayBottomMessage(s, Color.WHITE));
    }

    @Override
    public void gameOver(String winnerName) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText("The game is now over, " + (playerName.equals(winnerName) ? " you win!" : " you lose!"));
            alert.setContentText(winnerName + " won!");

            alert.showAndWait();
        });
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
        ClientGUI.playerName = playerName;
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
