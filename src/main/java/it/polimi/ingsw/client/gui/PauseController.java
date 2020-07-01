package it.polimi.ingsw.client.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The Pause scene controller.
 */
public class PauseController {
    @FXML
    private Label quitLabel;
    @FXML
    private Label resumeLabel;
    @FXML
    private ImageView quitButton;
    @FXML
    private ImageView resumeButton;

    /**
     * Resume pressed event handler.
     */
    @FXML
    public void resumePressed() {
        resumeButton.setImage(new Image(String.valueOf(getClass().getResource("/images/btn_green_pressed.png"))));
    }

    /**
     * Resume released event handler.
     */
    @FXML
    public void resumeReleased() {
        resumeButton.setImage(new Image(String.valueOf(getClass().getResource("/images/btn_green.png"))));
    }

    /**
     * Resume clicked event handler.
     */
    @FXML
    public void resumeClicked() {

    }

    /**
     * Quit pressed event handler.
     */
    @FXML
    public void quitPressed() {
        quitButton.setImage(new Image(String.valueOf(getClass().getResource("/images/btn_coral_pressed.png"))));
    }

    /**
     * Quit released event handler.
     */
    @FXML
    public void quitReleased() {
        quitButton.setImage(new Image(String.valueOf(getClass().getResource("/images/btn_coral.png"))));
    }

    /**
     * Quit clicked event handler.
     */
    @FXML
    public void quitClicked() {

    }

}
