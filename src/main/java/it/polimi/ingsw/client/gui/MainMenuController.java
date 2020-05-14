package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.events.ChangeSceneEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class MainMenuController {
    @FXML
    private ImageView playButtonImage;
    @FXML
    private ImageView creditsButton;
    @FXML
    private ImageView exitButton;

    @FXML
    public void handleMousePress(MouseEvent mouseEvent) {
        playButtonImage.setImage(new Image(String.valueOf(getClass().getResource("/images/cm_btn_blue_pressed.png"))));
    }

    @FXML
    public void handleMouseRelease(MouseEvent mouseEvent) {
        playButtonImage.setImage(new Image(String.valueOf(getClass().getResource("/images/cm_btn_blue.png"))));
    }

    @FXML
    public void handleCreditMousePress(MouseEvent mouseEvent) {
        creditsButton.setImage(new Image(String.valueOf(getClass().getResource("/images/btn_blue_pressed.png"))));
    }

    @FXML
    public void handleCreditMouseRelease(MouseEvent mouseEvent) {
        creditsButton.setImage(new Image(String.valueOf(getClass().getResource("/images/btn_blue.png"))));
    }

    @FXML
    public void handleCreditsClicked(MouseEvent mouseEvent) {
        creditsButton.fireEvent(new ChangeSceneEvent("credits"));
    }

    @FXML
    public void handleExitClicked(MouseEvent mouseEvent) {
        System.exit(1);
    }

    @FXML
    public void handleExitPressed(MouseEvent mouseEvent) {
        exitButton.setImage(new Image(String.valueOf(getClass().getResource("/images/btn_coral_pressed.png"))));
    }

    @FXML
    public void handleExitReleased(MouseEvent mouseEvent) {
        exitButton.setImage(new Image(String.valueOf(getClass().getResource("/images/btn_coral.png"))));
    }
}
