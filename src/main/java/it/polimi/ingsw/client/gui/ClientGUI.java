package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.events.SceneEvent;
import it.polimi.ingsw.client.events.SceneEventHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientGUI extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent mainMenu = FXMLLoader.load(getClass().getResource("/scenes/MainMenu.fxml"));
        Parent credits = FXMLLoader.load(getClass().getResource("/scenes/Credits.fxml"));
        Scene scene = new Scene(mainMenu, 1300, 750);

        stage.addEventHandler(SceneEvent.SCENE_EVENT_TYPE, new SceneEventHandler() {
            @Override
            public void onSceneChange(String sceneName) {
                switch (sceneName) {
                    case "credits":
                        scene.setRoot(credits);
                        break;
                    case "menu":
                        scene.setRoot(mainMenu);
                        break;
                }
            }
        });

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
