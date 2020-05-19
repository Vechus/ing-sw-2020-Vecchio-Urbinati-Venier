package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.events.ChangeSceneEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class CreditsController {
    @FXML
    private Button backButton;

    @FXML
    public void handleBackClicked() {
        backButton.fireEvent(new ChangeSceneEvent("menu"));
    }

}
