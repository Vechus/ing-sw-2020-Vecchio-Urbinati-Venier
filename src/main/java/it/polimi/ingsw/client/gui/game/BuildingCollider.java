package it.polimi.ingsw.client.gui.game;

import it.polimi.ingsw.util.Vector2;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class BuildingCollider extends Box {
    private static final double BOX_WIDTH = 2.9;
    private static final double BOX_HEIGHT = .1;
    private static final double BOX_DEPTH = 2.8;
    private double x;
    private double y;
    private double z;
    private Vector2 pos;
    PhongMaterial colliderMaterial = new PhongMaterial();

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

    public void select() {
        colliderMaterial.setDiffuseColor(Color.YELLOW);
    }

    public void unselect() {
        colliderMaterial.setDiffuseColor(Color.TRANSPARENT);
    }

    public Vector2 getPos() {
        return pos;
    }
}
