package it.polimi.ingsw.client.gui.game;

import it.polimi.ingsw.util.Vector2;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;

/**
 * The type Building block.
 */
public class BuildingBlock extends MeshView {
    private static final double BOARD_HEIGHT = -3;
    private final Vector2 pos;

    /**
     * Instantiates a new Building block.
     *
     * @param coordX the x coordinate relative to the board
     * @param coordY the y coordinate relative to the board
     * @param height the height of this block
     */
    public BuildingBlock(int coordX, int coordY, int height) {
        this.pos = new Vector2(coordX, coordY);

        PhongMaterial material = new PhongMaterial();
        setMesh(Loader.loadObj("/models/BuildingBlock0" + height + ".obj").getMesh());
        if(height != 4) {
            translateYProperty().set(BOARD_HEIGHT - (1.5 * (height-1)));
            material.setDiffuseMap(new Image(String.valueOf(getClass().getResource("/textures/BuildingBlock0" + height + ".png"))));
            material.setSpecularColor(Color.WHITE);
            translateXProperty().set(coordX * 3.19 - 6.2);
        }
        else {
            material.setSpecularColor(Color.DARKGREY);
            material.setDiffuseColor(Color.ROYALBLUE);
            translateYProperty().set(BOARD_HEIGHT - 4.1);
            translateXProperty().set(coordX * 3.19 - 6.15);
        }
        setMaterial(material);
        getTransforms().add(new Rotate(180, Rotate.X_AXIS));
        translateZProperty().set(coordY * 3.05 - 6.2);
        getTransforms().add(new Scale(0.4, 0.4, 0.4));
    }

    /**
     * Gets position (in the board).
     *
     * @return the position
     */
    public Vector2 getPos() {
        return pos;
    }
}
