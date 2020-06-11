package it.polimi.ingsw.client.gui.game;

import it.polimi.ingsw.util.Vector2;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Rotate;

public class Worker3D extends MeshView {
    private static final double BOARD_HEIGHT = -4;
    private Vector2 position;
    private final int id;

    public Worker3D(int id, boolean isWoman) {
        this.id = id;
        setMesh(Loader.loadObj("/models/" + (isWoman ? "FemaleBuilder" : "MaleBuilder") + ".obj").getMesh());
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(new Image(String.valueOf(getClass().getResource("/textures/" + (isWoman ? "FemaleBuilder_" : "MaleBuilder_") + id + ".png"))));
        switch (id) {
            case 0 -> material.setSpecularColor(Color.RED);
            case 1 -> material.setSpecularColor(Color.GREEN);
            case 2 -> material.setSpecularColor(Color.BLUE);
        }
        material.setSpecularPower(.05);
        setMaterial(material);
        getTransforms().add(new Rotate(180, Rotate.X_AXIS));
    }

    public void setPosition(Vector2 pos, double height) {
        position = pos;
        translateXProperty().set(pos.getX() * 3.19 - 6.05);
        translateZProperty().set(pos.getY() * 3.05 - 6.2);
        translateYProperty().set(BOARD_HEIGHT - (1.5 * (height)));
    }

    public Vector2 getPosition() {
        return position;
    }

    public int getWorkerId() {
        return id;
    }
}
