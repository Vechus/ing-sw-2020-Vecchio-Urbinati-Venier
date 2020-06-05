package it.polimi.ingsw.client.gui.game;

import it.polimi.ingsw.client.ClientBoard;
import it.polimi.ingsw.util.Vector2;
import javafx.scene.Group;
import javafx.scene.PointLight;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;
import java.util.List;

public class Board3D {
    private final Group group;
    private final Building3D[][] board = new Building3D[5][5];
    private final List<Worker3D> playerWorkers = new ArrayList<>();

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

    public void build(Vector2 v) {
        Building3D building3D = getBuilding(v);
        if(!building3D.hasDome())
            building3D.increaseHeight();
        else
            System.out.println("You can't build there, there is already a dome!");
    }

    public Building3D getBuilding(Vector2 v) {
        int x = v.getX();
        int y = v.getY();
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

    public void unselectNotRedBuildings() {
        for (Building3D[] building3DS : board) {
            for (Building3D building3D: building3DS) {
                building3D.unselectNotRed();
            }
        }
    }

    public void fromClientBoard(ClientBoard board) {
        // fixme review this function
        Vector2 diff = null;
        boolean hasDiff = false;
        for(int i = 0; i < 5; i ++) {
            for(int j = 0; j < 5; j ++) {
                Vector2 v = new Vector2(i, j);
                // building differences
                for(int k = getBuilding(v).getHeight(); k < board.getHeight(v); k++) {
                    getBuilding(v).increaseHeight();
                }
                if(board.getDome(v) && !getBuilding(v).hasDome())
                    getBuilding(v).buildDome();
                // workers movement difference tracking
                if(board.getWorkerPlayer(v) != (getWorkerAt(v) == null ? -1 : getWorkerAt(v).getWorkerId())) {
                    if(diff == null) {
                        // difference point 1 found
                        diff = v;
                    }
                    else {
                        // difference point 2 found
                        hasDiff = true;
                        if(getWorkerAt(diff) != null) {
                            // move from diff to v or switch places between the two
                            if(getWorkerAt(v) != null) {
                                Vector2 tmp = new Vector2(-1, -1);
                                getWorkerAt(v).setPosition(tmp, -5);
                                getWorkerAt(diff).setPosition(v, getBuilding(v).getHeight());
                                getWorkerAt(tmp).setPosition(diff, getBuilding(diff).getHeight());
                            } else
                                getWorkerAt(diff).setPosition(v, getBuilding(v).getHeight());
                        } else {
                            // move from v to diff
                            if(getWorkerAt(v) != null)
                                getWorkerAt(v).setPosition(diff, getBuilding(diff).getHeight());
                            else {
                                // new pair of workers (should not happen)
                                System.err.println("Double workers parsed from ClientBoard");
                            }
                        }
                        if(getWorkerAt(v) != null && getWorkerAt(diff) != null && board.getWorkerPlayer(v) == -1 && board.getWorkerPlayer(diff) == -1) {
                            // double worker removal
                            removeWorker(v);
                            removeWorker(diff);
                        }
                    }
                }
            }
        }
        if(!hasDiff && diff != null) {
            if(board.getWorkerPlayer(diff) != -1) {
                // new single worker
                Vector2 finalDiff = diff;
                addWorker(diff, board.getWorkerPlayer(diff), playerWorkers.stream()
                        .filter(w -> w.getWorkerId() == board.getWorkerPlayer(finalDiff))
                        .count() == 1);
                /*Worker3D worker3D = new Worker3D(board.getWorkerPlayer(diff), playerWorkers.stream()
                        .filter(w -> w.getWorkerId() == board.getWorkerPlayer(finalDiff)).count() == 1);
                worker3D.setPosition(diff, getBuilding(diff).getHeight());
                playerWorkers.add(worker3D);
                group.getChildren().add(worker3D);*/
            } else {
                // single worker deleted (should it happen? not very convenient imo)
                removeWorker(diff);
            }
        }
    }

    public void removeWorker(Vector2 pos) {
        group.getChildren().remove(getWorkerAt(pos));
        playerWorkers.remove(getWorkerAt(pos));
    }

    public void addWorker(Vector2 pos, int id, boolean isWoman) {
        Worker3D worker3D = new Worker3D(id, isWoman);
        worker3D.setPosition(pos, getBuilding(pos).getHeight());
        playerWorkers.add(worker3D);
        group.getChildren().add(worker3D);
    }

    public Worker3D getWorkerAt(Vector2 pos) {
        for(Worker3D worker3D: playerWorkers) {
            if(worker3D.getPosition().equals(pos)) {
                return worker3D;
            }
        }
        return null;
    }

    public int getPlayerWorkersSize() {
        return playerWorkers.size();
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