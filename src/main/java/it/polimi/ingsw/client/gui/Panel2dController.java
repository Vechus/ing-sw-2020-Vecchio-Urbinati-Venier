package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.ClientAction;
import it.polimi.ingsw.client.events.GameEvent;
import it.polimi.ingsw.client.events.GameEventHandler;
import it.polimi.ingsw.client.events.PlayerActionEvent;
import it.polimi.ingsw.util.ActionType;
import it.polimi.ingsw.util.Vector2;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.List;

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
                        if(selected != null) {
                            buttonBox.getChildren().clear();
                            ClientAction clientAction = new ClientAction(selected != null ? selected:pos, selected != null ? selected:pos, ActionType.PLACE_WORKER);
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
        //Button button = new Button("Testodentro");
    }

    public void setSelected(Vector2 selected) {
        this.selected = selected;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }

    public Vector2 getSelected() {
        return selected;
    }

    public Vector2 getPos() {
        return pos;
    }
}
