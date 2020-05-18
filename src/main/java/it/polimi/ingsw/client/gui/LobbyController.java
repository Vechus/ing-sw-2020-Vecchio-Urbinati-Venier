package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.events.ChangeSceneEvent;
import it.polimi.ingsw.client.events.PlayerNameChangeEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class LobbyController {
    @FXML
    private Button backButton;
    @FXML
    private Label firstPlayer;
    @FXML
    private Label secondPlayer;
    @FXML
    private Label thirdPlayer;
    @FXML
    private Pane lobbyPane;
    @FXML
    private Pane insertPane;
    @FXML
    private Button confirmButton;
    @FXML
    private Label errorLabel;
    @FXML
    private ImageView errorImage;
    @FXML
    private TextField textField;

    @FXML
    public void handleBackClicked() {
        backButton.fireEvent(new ChangeSceneEvent("menu"));
        initLobbyScene();
    }

    @FXML
    public void handleConfirmClicked() {
        String name = textField.getText();
        if(name.length() == 0) {
            errorImage.setVisible(true);
            errorLabel.setVisible(true);
        } else {
            errorLabel.setVisible(false);
            errorLabel.setVisible(false);
            insertPane.setVisible(false);
            firstPlayer.setText(name);
            lobbyPane.setVisible(true);
            insertPane.fireEvent(new PlayerNameChangeEvent(name));
        }
    }

    private void initLobbyScene() {
        lobbyPane.setVisible(false);
        insertPane.setVisible(true);
        errorLabel.setVisible(false);
        errorImage.setVisible(false);
    }
}
