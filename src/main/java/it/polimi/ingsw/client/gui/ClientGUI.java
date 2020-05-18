package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.ClientAction;
import it.polimi.ingsw.client.events.ChangeSceneEvent;
import it.polimi.ingsw.client.events.CustomEvent;
import it.polimi.ingsw.client.events.CustomEventHandler;
import it.polimi.ingsw.client.gui.game.GameScene;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientGUI extends Application {
    private final Parent mainMenu = FXMLLoader.load(getClass().getResource("/scenes/MainMenu.fxml"));
    private final Parent credits = FXMLLoader.load(getClass().getResource("/scenes/Credits.fxml"));
    private final Parent lobby = FXMLLoader.load(getClass().getResource("/scenes/Lobby.fxml"));
    private final Parent panel2d = FXMLLoader.load(getClass().getResource("/scenes/Panel2d.fxml"));
    //private final Parent pauseMenu = FXMLLoader.load(getClass().getResource("/scenes/Pause.fxml"));
    private String playerName;
    private GameScene gameScene;

    public ClientGUI() throws IOException {
    }

    @Override
    public void start(Stage stage) {
        Scene menuScene = new Scene(mainMenu, 1300, 750);

        stage.addEventHandler(CustomEvent.CUSTOM_EVENT_TYPE, new CustomEventHandler() {
            @Override
            public void onSceneChange(String sceneName) {
                switch (sceneName) {
                    case "credits" -> menuScene.setRoot(credits);
                    case "menu" -> menuScene.setRoot(mainMenu);
                    case "lobby" -> menuScene.setRoot(lobby);
                    case "game" -> {
                        gameScene = new GameScene(stage, panel2d);
                        stage.setScene(gameScene.getScene());
                    }
                    case "gameToMenu" -> {
                        menuScene.setRoot(mainMenu);
                        stage.setScene(menuScene);
                    }
                }
            }

            @Override
            public void onPlayerNameChange(String playerName) {
                setPlayerName(playerName);
            }

            @Override
            public void onPlayerAction(ClientAction action) {

            }
        });
        stage.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent ->{
            if(keyEvent.getCode() == KeyCode.NUMPAD1) stage.fireEvent(new ChangeSceneEvent("game"));
        });

        stage.setTitle("Santorini - GC18");
        stage.setScene(menuScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

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

    public Parent getPanel2d() {
        return panel2d;
    }

    public GameScene getGameScene() {
        return gameScene;
    }
}
