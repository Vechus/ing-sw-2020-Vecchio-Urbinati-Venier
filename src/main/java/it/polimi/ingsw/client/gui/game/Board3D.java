package it.polimi.ingsw.client.gui.game;

import javafx.scene.Group;
import javafx.scene.PointLight;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Rotate;

public class Board3D {
    private final Group group;
    private Building3D[][] board = new Building3D[5][5];

    public Board3D(Group group) {
        this.group = group;
        prepareModel("Cliff", group, 0, 0, 0);
        prepareModel("Sea", group, 0, 1, 0);
        for(int i = 0; i < 5; i ++) {
            for (int j = 0; j < 5; j++) {
                board[i][j] = new Building3D(group, i, j);
            }
        }
        PointLight sun = new PointLight();
        sun.setColor(Color.WHITE);
        sun.translateXProperty().set(0);
        sun.translateYProperty().set(-10);
        sun.translateZProperty().set(0);
    }

    public void build(int x, int y) {
        Building3D building3D = getBuilding(x, y);
        if(!building3D.hasDome())
            building3D.increaseHeight();
        else
            System.out.println("You can't build there, there is already a dome!");
    }

    public Building3D getBuilding(int x, int y) {
        if(x < 0 || x > 4 || y < 0 || y > 4) {
            return null;
        } else {
            return board[x][y];
        }
    }

    public void unselectBuildings() {
        for (Building3D[] building3DS : board) {
            for (Building3D building3D: building3DS) {
                building3D.unselect();
            }
        }
    }

    private void prepareModel(String model, Group group, int x, int y, int z) {
        MeshView meshView;
        PhongMaterial material = new PhongMaterial();
        meshView = Loader.loadObj(getClass().getResource("/models/" + model + ".obj").getPath());
        material.setDiffuseMap(new Image(String.valueOf(getClass().getResource("/textures/" + model + ".png"))));
        if(model.equals("Cliff")) {
            material.setSpecularMap(new Image(String.valueOf(getClass().getResource("/textures/Cliff_Emission.png"))));
            meshView.setScaleX(10);
            meshView.setScaleY(10);
            meshView.setScaleZ(10);
        }
        material.setSpecularColor(Color.WHITE);
        meshView.setMaterial(material);
        meshView.getTransforms().add(new Rotate(180, Rotate.X_AXIS));
        meshView.translateXProperty().set(x);
        meshView.translateYProperty().set(y);
        meshView.translateZProperty().set(z);
        group.getChildren().add(meshView);
    }
}
