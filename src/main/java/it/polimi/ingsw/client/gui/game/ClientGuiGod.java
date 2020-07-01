package it.polimi.ingsw.client.gui.game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The type Client gui god.
 */
public class ClientGuiGod extends ImageView {
    private final String godName;

    /**
     * Instantiates a new Client gui god.
     *
     * @param godName the god name
     */
    public ClientGuiGod (String godName) {
        this.godName = godName;

        setImage(new Image(String.valueOf(getClass().getResource("/images/godCards/" + godName + ".png"))));
        setStyle("-fx-max-width: 240px");
    }

    /**
     * Gets god name.
     *
     * @return the god name
     */
    public String getGodName() {
        return godName;
    }
}
