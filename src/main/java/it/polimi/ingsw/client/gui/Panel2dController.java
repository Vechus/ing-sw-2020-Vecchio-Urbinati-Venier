package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.events.GameEvent;
import it.polimi.ingsw.client.events.GameEventHandler;
import it.polimi.ingsw.client.events.PlaceWorkerEvent;
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

    public void init() {
        anchorPane.addEventHandler(GameEvent.GAME_EVENT_TYPE, new GameEventHandler() {
            @Override
            public void onNewAllowedActions(List<ActionType> allowedActions) {
                addButtons(allowedActions);
            }

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
            Button button;
            switch (action) {
                case MOVE -> {
                    button = new Button("Move");
                    button.getStyleClass().add("blueButton");
                }
                case BUILD -> {
                    button = new Button("Build");
                    button.getStyleClass().add("greenButton");
                }
                case BUILD_DOME -> {
                    button = new Button("Build dome");
                    button.getStyleClass().add("blueButton");
                }
                case PLACE_WORKER -> {
                    button = new Button("Place worker");
                    button.getStyleClass().add("greenButton");
                    button.setOnAction(actionEvent -> {
                        if(selected != null)
                            button.fireEvent(new PlaceWorkerEvent(selected));
                    });
                }
                case END_TURN -> {
                    button = new Button("End turn");
                    button.getStyleClass().add("redButton");
                }
                default -> throw new IllegalStateException("Unexpected value: " + action);
            }
            buttonBox.getChildren().add(button);
        }
        Button button = new Button("Testodentro");
        button.getStylesheets().add(getClass().getResource("/scenes/css/Buttons.css").toExternalForm());
    }

    public void setSelected(Vector2 selected) {
        this.selected = selected;
    }
}
