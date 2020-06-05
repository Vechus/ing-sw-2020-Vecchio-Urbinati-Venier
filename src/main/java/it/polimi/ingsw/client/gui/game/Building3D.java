package it.polimi.ingsw.client.gui.game;

import javafx.scene.Group;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import java.util.ArrayList;
import java.util.List;

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
    Group group;

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

    public void increaseHeight() {
        if(!hasDome) {
            BuildingBlock block = new BuildingBlock(coordX, coordY, pieces.size() + 1);
            block.getTransforms().add(new Rotate(90 * rotFactor, Rotate.Y_AXIS));
            pieces.add(block);
            this.group.getChildren().add(block);
            if (pieces.size() == 4) hasDome = true;
        }
    }

    public void buildDome() {
        BuildingBlock dome = new BuildingBlock(coordX, coordY, 4);
        this.group.getChildren().add(dome);
        dome.getTransforms().add(new Rotate(90 * rotFactor, Rotate.Y_AXIS));
        dome.getTransforms().add(new Translate(0, - (3.14 * (3 - getHeight())), 0));
        pieces.add(dome);
        this.hasDome = true;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public List<BuildingBlock> getPieces() {
        return pieces;
    }

    public int getHeight() {
        return pieces.size();
    }

    public boolean hasDome() {
        return hasDome;
    }

    public BuildingCollider getCollider() {
        return collider;
    }

    public void select() {
        collider.select();
    }

    public void selectRed() {
        collider.selectRed();
    }

    public void unselect() {
        collider.unselect();
    }

    public void unselectNotRed() {
        collider.unselectNotRed();
    }
}
