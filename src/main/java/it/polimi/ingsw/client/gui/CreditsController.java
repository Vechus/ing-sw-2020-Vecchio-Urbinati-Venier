package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.events.ChangeSceneEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class CreditsController {
    @FXML
    private ImageView backButton;

    @FXML
    public void handleMousePress(MouseEvent mouseEvent) {
        backButton.setImage(new Image(String.valueOf(getClass().getResource("/images/btn_blue_pressed.png"))));
    }

    @FXML
    public void handleMouseRelease(MouseEvent mouseEvent) {
        backButton.setImage(new Image(String.valueOf(getClass().getResource("/images/btn_blue.png"))));
    }

    @FXML
    public void handleBackClicked(MouseEvent mouseEvent) {
        backButton.fireEvent(new ChangeSceneEvent("menu"));
    }

}
