package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.events.HostSelectGodsEvent;
import it.polimi.ingsw.client.events.PlayerGodChangeEvent;
import it.polimi.ingsw.client.gui.game.ClientGuiGod;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class AfterLobbyController {
    @FXML
    private AnchorPane anchor;
    private int playerNumber;
    private boolean isHost;
    private List<String> selected;
    private GridPane grid;

    public void displayGods(List<String> gods) {
        grid = new GridPane();
        selected = new ArrayList<>();
        anchor.getChildren().add(grid);
        grid.setPrefSize(1300, 750);
        grid.setAlignment(Pos.CENTER);

        grid.setLayoutY(50);

        isHost = gods.size() > 3;

        if(isHost) {
            int i = 0, j = 0;
            for(String god: gods) {
                grid.add(parseGod(god, gods.size()), i, j);
                if(i == 6) {
                    i = -1;
                    j++;
                }
                i++;
            }
        } else {
            int i = 0;
            for(String god:gods) {
                grid.add(parseGod(god, gods.size()), i, 0);
                i++;
            }
        }
    }

    private VBox parseGod(String god, int size) {
        VBox pane = new VBox();
        pane.setPadding(new Insets(10, 10, 10, 10));
        ClientGuiGod guiGod = new ClientGuiGod(god);
        if(isHost) guiGod.setFitWidth((int)(2000 / size));
        else guiGod.setFitWidth((int)(700/size));
        guiGod.setPreserveRatio(true);
        pane.getChildren().add(guiGod);

        guiGod.addEventHandler(MouseEvent.MOUSE_CLICKED, (mouseEvent -> {
            selectGod(guiGod);
        }));
        return pane;
    }

    public void setNumber(int number) {
        this.playerNumber = number;
    }

    private void selectGod(ClientGuiGod god) {
        if(isHost) {
            if(selected.contains(god.getGodName()))
                return;
            selected.add(god.getGodName());
            if(selected.size() == playerNumber) {
                grid.getChildren().clear();
                grid.fireEvent(new HostSelectGodsEvent(selected));
            }
        } else {
            grid.getChildren().clear();
            grid.fireEvent(new PlayerGodChangeEvent(god.getGodName()));
        }
    }
}
