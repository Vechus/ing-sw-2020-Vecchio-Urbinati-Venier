package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.ClientAction;
import it.polimi.ingsw.client.events.GameEvent;
import it.polimi.ingsw.client.events.GameEventHandler;
import it.polimi.ingsw.client.events.PlaceWorkerEvent;
import it.polimi.ingsw.client.events.PlayerActionEvent;
import it.polimi.ingsw.client.gui.game.ClientGuiGod;
import it.polimi.ingsw.util.ActionType;
import it.polimi.ingsw.util.Vector2;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * The Panel2d sub-scene controller.
 */
public class Panel2dController {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ImageView playerGodImage;
    @FXML
    private Label playerNameLabel;
    @FXML
    private VBox buttonBox;
    private Vector2 selected = null;
    private Vector2 pos = null;

    /**
     * Initialises parameters.
     */
    public void init() {
        anchorPane.addEventHandler(GameEvent.GAME_EVENT_TYPE, new GameEventHandler() {
            @Override
            public void onSelectGrid(Vector2 pos) {
                selected = pos;
            }

            @Override
            public void onPlaceWorker(Vector2 pos) { }
        });
    }

    /**
     * Adds actions buttons and binds their respective events.
     *
     * @param actions the actions
     */
    public void addButtons(List<ActionType> actions) {
        for(ActionType action : actions) {
            Button button = new Button();
            button.getStylesheets().add(getClass().getResource("/scenes/css/Buttons.css").toExternalForm());
            switch (action) {
                case MOVE -> {
                    button.setText("Move");
                    button.getStyleClass().add("blueButton");
                    button.setOnAction(actionEvent -> {
                        if(selected != null && pos != null) {
                            buttonBox.getChildren().clear();
                            ClientAction clientAction = new ClientAction(pos, selected, ActionType.MOVE);
                            buttonBox.fireEvent(new PlayerActionEvent(clientAction));
                        }
                    });
                }
                case BUILD -> {
                    button.setText("Build");
                    button.getStyleClass().add("greenButton");
                    button.setOnAction(actionEvent -> {
                        if(selected != null && pos != null) {
                            buttonBox.getChildren().clear();
                            ClientAction clientAction = new ClientAction(pos, selected, ActionType.BUILD);
                            buttonBox.fireEvent(new PlayerActionEvent(clientAction));
                        }
                    });
                }
                case BUILD_DOME -> {
                    button.setText("Build dome");
                    button.getStyleClass().add("blueButton");
                    button.setOnAction(actionEvent -> {
                        if(selected != null && pos != null) {
                            buttonBox.getChildren().clear();
                            ClientAction clientAction = new ClientAction(pos, selected, ActionType.BUILD_DOME);
                            buttonBox.fireEvent(new PlayerActionEvent(clientAction));
                        }
                    });
                }
                case PLACE_WORKER -> {
                    button.setText("Place worker");
                    button.getStyleClass().add("greenButton");
                    button.setOnAction(actionEvent -> {
                        if(pos != null) {
                            buttonBox.getChildren().clear();
                            buttonBox.fireEvent(new PlaceWorkerEvent(pos));
                            ClientAction clientAction = new ClientAction(pos, pos, ActionType.PLACE_WORKER);
                            buttonBox.fireEvent(new PlayerActionEvent(clientAction));
                        }
                    });
                }
                case END_TURN -> {
                    button.setText("End turn");
                    button.getStyleClass().add("redButton");
                    button.setOnAction(actionEvent -> {
                        buttonBox.getChildren().clear();
                        ClientAction clientAction = new ClientAction(pos, pos, ActionType.END_TURN);
                        buttonBox.fireEvent(new PlayerActionEvent(clientAction));
                    });
                }
                default -> throw new IllegalStateException("Unexpected value: " + action);
            }
            buttonBox.getChildren().add(button);
        }
    }

    /**
     * Sets player name label content.
     *
     * @param playerName the player name
     */
    public void setPlayerNameLabel(String playerName) {
        this.playerNameLabel.setText(playerName);
    }

    /**
     * Sets player god image content.
     *
     * @param god the god
     */
    public void setPlayerGodImage(String god) {
        ClientGuiGod guiGod = new ClientGuiGod(god);
        this.playerGodImage.setImage(guiGod.getImage());
    }

    /**
     * Sets selected position.
     *
     * @param selected the selected
     */
    public void setSelected(Vector2 selected) {
        this.selected = selected;
    }

    /**
     * Sets pos parameter.
     *
     * @param pos the position to set
     */
    public void setPos(Vector2 pos) {
        this.pos = pos;
    }

    /**
     * Gets selected position.
     *
     * @return the selected
     */
    public Vector2 getSelected() {
        return selected;
    }

    /**
     * Gets pos parameter.
     *
     * @return the pos parameter
     */
    public Vector2 getPos() {
        return pos;
    }
}
