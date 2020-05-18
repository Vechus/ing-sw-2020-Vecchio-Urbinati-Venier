package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.events.ChangeSceneEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class MainMenuController {
    @FXML
    private ImageView playButtonImage;
    @FXML
    private Button creditsButton;
    @FXML
    private Button exitButton;

    @FXML
    public void handleMousePress(MouseEvent mouseEvent) {
        playButtonImage.setImage(new Image(String.valueOf(getClass().getResource("/images/cm_btn_blue_pressed.png"))));
    }

    @FXML
    public void handleMouseRelease(MouseEvent mouseEvent) {
        playButtonImage.setImage(new Image(String.valueOf(getClass().getResource("/images/cm_btn_blue.png"))));
    }

    @FXML
    public void handleCreditsClicked() {
        creditsButton.fireEvent(new ChangeSceneEvent("credits"));
    }

    @FXML
    public void handlePlayClicked(MouseEvent mouseEvent) {
        playButtonImage.fireEvent(new ChangeSceneEvent("lobby"));
    }

    @FXML
    public void handleExitClicked() {
        System.exit(1);
    }
}
