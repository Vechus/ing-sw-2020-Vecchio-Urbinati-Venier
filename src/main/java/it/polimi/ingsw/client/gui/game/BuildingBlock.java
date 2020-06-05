package it.polimi.ingsw.client.gui.game;

import it.polimi.ingsw.util.Vector2;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;

public class BuildingBlock extends MeshView {
    private static final double BOARD_HEIGHT = -3;
    private Vector2 pos;
    public BuildingBlock(int coordX, int coordY, int height) {
        this.pos = new Vector2(coordX, coordY);

        PhongMaterial material = new PhongMaterial();
        setMesh(Loader.loadObj(getClass().getResource("/models/BuildingBlock0" + height + ".obj").getPath()).getMesh());
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

    public Vector2 getPos() {
        return pos;
    }
}
