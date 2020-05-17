package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.events.ChangeSceneEvent;
import it.polimi.ingsw.client.events.CustomEvent;
import it.polimi.ingsw.client.events.CustomEventHandler;
import it.polimi.ingsw.client.gui.game.GameScene;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class ClientGUI extends Application {
    private String playerName;
    private GameScene gameScene;
    @Override
    public void start(Stage stage) throws Exception {
        Parent mainMenu = FXMLLoader.load(getClass().getResource("/scenes/MainMenu.fxml"));
        Parent credits = FXMLLoader.load(getClass().getResource("/scenes/Credits.fxml"));
        Parent lobby = FXMLLoader.load(getClass().getResource("/scenes/Lobby.fxml"));
        Parent panel2d = FXMLLoader.load(getClass().getResource("/scenes/Panel2d.fxml"));

        Scene menuScene = new Scene(mainMenu, 1300, 750);

        stage.addEventHandler(CustomEvent.CUSTOM_EVENT_TYPE, new CustomEventHandler() {
            @Override
            public void onSceneChange(String sceneName) {
                switch (sceneName) {
                    case "credits" -> menuScene.setRoot(credits);
                    case "menu" -> menuScene.setRoot(mainMenu);
                    case "lobby" -> menuScene.setRoot(lobby);
                    case "game" -> {
                        gameScene = new GameScene(panel2d);
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
}
