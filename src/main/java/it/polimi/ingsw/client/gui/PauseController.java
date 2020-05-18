package it.polimi.ingsw.client.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PauseController {
    @FXML
    private Label quitLabel;
    @FXML
    private Label resumeLabel;
    @FXML
    private ImageView quitButton;
    @FXML
    private ImageView resumeButton;

    @FXML
    public void resumePressed() {
        resumeButton.setImage(new Image(String.valueOf(getClass().getResource("/images/btn_green_pressed.png"))));
    }

    @FXML
    public void resumeReleased() {
        resumeButton.setImage(new Image(String.valueOf(getClass().getResource("/images/btn_green.png"))));
    }

    @FXML
    public void resumeClicked() {

    }

    @FXML
    public void quitPressed() {
        quitButton.setImage(new Image(String.valueOf(getClass().getResource("/images/btn_coral_pressed.png"))));
    }

    @FXML
    public void quitReleased() {
        quitButton.setImage(new Image(String.valueOf(getClass().getResource("/images/btn_coral.png"))));
    }

    @FXML
    public void quitClicked() {

    }

}
