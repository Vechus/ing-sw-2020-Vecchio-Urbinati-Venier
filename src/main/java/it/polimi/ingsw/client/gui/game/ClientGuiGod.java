package it.polimi.ingsw.client.gui.game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ClientGuiGod extends ImageView {
    private final String godName;

    public ClientGuiGod (String godName) {
        this.godName = godName;

        setImage(new Image(String.valueOf(getClass().getResource("/images/godCards/" + godName + ".png"))));
        setStyle("-fx-max-width: 240px");
    }

    public String getGodName() {
        return godName;
    }
}
