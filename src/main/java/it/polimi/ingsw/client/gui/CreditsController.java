package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.events.ChangeSceneEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * The Credits scene controller.
 */
public class CreditsController {
    @FXML
    private Button backButton;

    /**
     * Handle back button clicked event.
     */
    @FXML
    public void handleBackClicked() {
        backButton.fireEvent(new ChangeSceneEvent("menu"));
    }

}
