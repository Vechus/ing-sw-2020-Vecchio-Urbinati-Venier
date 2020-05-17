package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.events.ChangeSceneEvent;
import it.polimi.ingsw.client.events.PlayerNameChangeEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import javax.swing.*;

public class LobbyController {
    @FXML
    private ImageView backButton;
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
    private ImageView playerNameConfirmButton;
    @FXML
    private Label playerNameConfirmLabel;
    @FXML
    private Label errorLabel;
    @FXML
    private ImageView errorImage;
    @FXML
    private TextField textField;

    @FXML
    public void handleMousePress(MouseEvent mouseEvent) {
        backButton.setImage(new Image(String.valueOf(getClass().getResource("/images/btn_coral_pressed.png"))));
    }

    @FXML
    public void handleMouseRelease(MouseEvent mouseEvent) {
        backButton.setImage(new Image(String.valueOf(getClass().getResource("/images/btn_coral.png"))));
    }

    @FXML
    public void handleBackClicked(MouseEvent mouseEvent) {
        backButton.fireEvent(new ChangeSceneEvent("menu"));
        initLobbyScene();
    }

    @FXML
    public void handleConfirmMousePress(MouseEvent mouseEvent) {
        playerNameConfirmButton.setImage(new Image(String.valueOf(getClass().getResource("/images/btn_blue_pressed.png"))));
    }

    @FXML
    public void handleConfirmMouseRelease(MouseEvent mouseEvent) {
        playerNameConfirmButton.setImage(new Image(String.valueOf(getClass().getResource("/images/btn_blue.png"))));
    }

    @FXML
    public void handleConfirmClicked(MouseEvent mouseEvent) {
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
