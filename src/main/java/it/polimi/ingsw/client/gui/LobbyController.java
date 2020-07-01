package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.events.PlayerIpPortEvent;
import it.polimi.ingsw.client.events.PlayerNameChangeEvent;
import it.polimi.ingsw.client.events.PlayerNumberChangeEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

/**
 * The Lobby scene controller.
 */
public class LobbyController {
    @FXML
    private Label firstPlayer;
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
    private Button confirmIpButton;
    @FXML
    private Pane nameInputPane;
    @FXML
    private Pane ipPane;
    @FXML
    private TextField ip;
    @FXML
    private TextField port;
    @FXML
    private Label playersNumberLabel;
    @FXML
    private Button confirmPlayersButton;
    @FXML
    private Pane hostPane;
    @FXML
    private Button increaseButton;
    @FXML
    private Button decreaseButton;

    /**
     * Handle confirm button clicked event.
     */
    @FXML
    public void handleConfirmClicked() {
        String name = textField.getText();
        if(name.length() == 0) {
            errorImage.setVisible(true);
            errorLabel.setText("Invalid username.");
            errorLabel.setVisible(true);
        } else {
            errorLabel.setVisible(false);
            errorLabel.setVisible(false);
            insertPane.setVisible(false);
            firstPlayer.setText("You're in, " + name + "!");
            lobbyPane.setVisible(true);
            insertPane.fireEvent(new PlayerNameChangeEvent(name));
        }
    }

    private void initLobbyScene() {
        lobbyPane.setVisible(false);
        insertPane.setVisible(true);
        hostPane.setVisible(false);
        ipPane.setVisible(true);
        nameInputPane.setVisible(false);
        errorLabel.setVisible(false);
        errorImage.setVisible(false);
    }

    /**
     * Handle confirm ip button click event.
     */
    @FXML
    public void handleConfirmIp() {
        String i = ip.getText();
        try {
            Integer p = Integer.valueOf(port.getText());
            errorImage.setVisible(false);
            errorLabel.setVisible(false);
            ipPane.setVisible(false);
            nameInputPane.setVisible(true);
            ipPane.fireEvent(new PlayerIpPortEvent(new Pair<>(i, p)));
        } catch (Exception e) {
            errorImage.setVisible(true);
            errorLabel.setText("Invalid port.");
            errorLabel.setVisible(true);
        }
    }

    /**
     * Show host pane.
     */
    public void showHostPane() {
        hostPane.setVisible(true);
    }

    /**
     * Handle confirm players button click event.
     */
    @FXML
    public void handleConfirmPlayers() {
        hostPane.setVisible(false);
        hostPane.fireEvent(new PlayerNumberChangeEvent(Integer.parseInt(playersNumberLabel.getText())));
    }

    /**
     * Handle decrease button click event.
     */
    @FXML
    public void handleDecrease() {
        if (Integer.parseInt(playersNumberLabel.getText()) == 3) {
            playersNumberLabel.setText("2");
        }
    }

    /**
     * Handle increase button click event.
     */
    @FXML
    public void handleIncrease() {
        if (Integer.parseInt(playersNumberLabel.getText()) == 2) {
            playersNumberLabel.setText("3");
        }
    }
}