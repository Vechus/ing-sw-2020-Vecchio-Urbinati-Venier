package it.polimi.ingsw.client.gui.game;

import javafx.scene.Group;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Building 3D.
 */
public class Building3D {
    private static final double BOARD_HEIGHT = -3;
    private double x;
    private double y;
    private double z;
    private final int coordX, coordY;
    private final int rotFactor;
    private final BuildingCollider collider;
    private final List<BuildingBlock> pieces = new ArrayList<BuildingBlock>();
    private boolean hasDome = false;
    private final Group group;

    /**
     * Instantiates a new Building 3D.
     *
     * @param g the group to add the building, which is the same of the Board3D
     * @param i the X coordinate on the board
     * @param j the Y coordinate on the board
     */
    public Building3D(Group g, int i, int j) {
        this.x = i * 3.19 - 6.2;
        this.coordX = i;
        this.coordY = j;
        this.y = BOARD_HEIGHT;
        this.z = j * 3.05 - 6.2;
        collider = new BuildingCollider(this.x, this.y, this.z, i, j);
        this.group = g;
        rotFactor = (int)(Math.random() * 4) % 4;
        g.getChildren().add(collider);
    }

    /**
     * Increase height of this Building3D.
     */
    public void increaseHeight() {
        if(!hasDome) {
            BuildingBlock block = new BuildingBlock(coordX, coordY, pieces.size() + 1);
            block.getTransforms().add(new Rotate(90 * rotFactor, Rotate.Y_AXIS));
            pieces.add(block);
            this.group.getChildren().add(block);
            if (pieces.size() == 4) hasDome = true;
        }
    }

    /**
     * Build dome on this Building3D.
     */
    public void buildDome() {
        BuildingBlock dome = new BuildingBlock(coordX, coordY, 4);
        this.group.getChildren().add(dome);
        dome.getTransforms().add(new Rotate(90 * rotFactor, Rotate.Y_AXIS));
        dome.getTransforms().add(new Translate(0, - (3.14 * (3 - getHeight())), 0));
        pieces.add(dome);
        this.hasDome = true;
    }

    /**
     * Gets x coordinate on the 3D space.
     *
     * @return the x coordinate on the 3D space
     */
    public double getX() {
        return x;
    }

    /**
     * Sets x coordinate on the 3D space.
     *
     * @param x the x coordinate on the 3D space
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Gets y coordinate on the 3D space.
     *
     * @return the y coordinate on the 3D space
     */
    public double getY() {
        return y;
    }

    /**
     * Sets y coordinate on the 3D space.
     *
     * @param y the y coordinate on the 3D space
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Gets z coordinate on the 3D space.
     *
     * @return the z coordinate on the 3D space
     */
    public double getZ() {
        return z;
    }

    /**
     * Sets z coordinate on the 3D space.
     *
     * @param z the z coordinate on the 3D space
     */
    public void setZ(double z) {
        this.z = z;
    }

    /**
     * Gets pieces of this Building3D.
     *
     * @return the pieces
     */
    public List<BuildingBlock> getPieces() {
        return pieces;
    }

    /**
     * Gets height of this Building3D.
     *
     * @return the height
     */
    public int getHeight() {
        return pieces.size();
    }

    /**
     * Has dome boolean.
     *
     * @return the boolean
     */
    public boolean hasDome() {
        return hasDome;
    }

    /**
     * Gets collider.
     *
     * @return the collider
     */
    public BuildingCollider getCollider() {
        return collider;
    }

    /**
     * Select this Building3D. Shows this building's collider, with color yellow.
     */
    public void select() {
        collider.select();
    }

    /**
     * Select red. Shows this building's collider, with color red.
     */
    public void selectRed() {
        collider.selectRed();
    }

    /**
     * Unselect this building. Hides its collider.
     */
    public void unselect() {
        collider.unselect();
    }

    /**
     * Unselect if not red. Hides its collider if not red.
     */
    public void unselectNotRed() {
        collider.unselectNotRed();
    }
}
