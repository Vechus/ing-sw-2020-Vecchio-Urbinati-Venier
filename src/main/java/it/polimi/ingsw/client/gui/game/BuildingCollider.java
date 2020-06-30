package it.polimi.ingsw.client.gui.game;

import it.polimi.ingsw.util.Vector2;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

/**
 * The type Building collider.
 */
public class BuildingCollider extends Box {
    private static final double BOX_WIDTH = 2.9;
    private static final double BOX_HEIGHT = .1;
    private static final double BOX_DEPTH = 2.8;
    private double x;
    private double y;
    private double z;
    private final Vector2 pos;
    private final PhongMaterial colliderMaterial = new PhongMaterial();

    /**
     * Instantiates a new Building collider.
     *
     * @param x the x coordinate (relative to the 3d space)
     * @param y the y coordinate (relative to the 3d space)
     * @param z the z coordinate (relative to the 3d space)
     * @param i the x coordinate (relative to the board)
     * @param j the y coordinate (relative to the board)
     */
    public BuildingCollider(double x, double y, double z, int i, int j) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.pos = new Vector2(i, j);
        translateXProperty().set(x);
        translateYProperty().set(y);
        translateZProperty().set(z);
        widthProperty().set(BOX_WIDTH);
        heightProperty().set(BOX_HEIGHT);
        depthProperty().set(BOX_DEPTH);
        colliderMaterial.setSpecularPower(0.1);
        colliderMaterial.setDiffuseColor(Color.TRANSPARENT);
        colliderMaterial.setSpecularColor(Color.BLACK);
        setMaterial(colliderMaterial);
    }

    /**
     * Shows the collider with yellow color.
     */
    public void select() {
        colliderMaterial.setDiffuseColor(Color.YELLOW);
    }

    /**
     * Shows the collider with red color.
     */
    public void selectRed() {
        colliderMaterial.setDiffuseColor(Color.RED);
    }

    /**
     * Hides the collider.
     */
    public void unselect() {
        colliderMaterial.setDiffuseColor(Color.TRANSPARENT);
    }

    /**
     * Gets position relative to the board.
     *
     * @return the position
     */
    public Vector2 getPos() {
        return pos;
    }

    /**
     * Hides the collider if not red.
     */
    public void unselectNotRed() {
        if(colliderMaterial.getDiffuseColor() != Color.RED) {
            colliderMaterial.setDiffuseColor(Color.TRANSPARENT);
        }
    }
}
