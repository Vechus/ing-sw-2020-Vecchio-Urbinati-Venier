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

/**
 * The type Board3D.
 */
public class Board3D {
    private final Group group;
    private final Building3D[][] board = new Building3D[5][5];
    private final List<Worker3D> playerWorkers = new ArrayList<>();

    /**
     * Instantiates a new Board 3D.
     *
     * @param group the group to which append the board to.
     */
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

    /**
     * Increases the height of a building. If the building reaches level 4 it builds a dome instead.
     *
     * @param v the position to build onto.
     */
    public void build(Vector2 v) {
        Building3D building3D = getBuilding(v);
        if(!building3D.hasDome())
            building3D.increaseHeight();
        else
            System.out.println("You can't build there, there is already a dome!");
    }

    /**
     * Gets the building at a specified position.
     *
     * @param v the position of the building
     * @return the building at position v
     */
    public Building3D getBuilding(Vector2 v) {
        int x = v.getX();
        int y = v.getY();
        if(x < 0 || x > 4 || y < 0 || y > 4) {
            return null;
        } else {
            return board[x][y];
        }
    }

    /**
     * Unselect buildings. This method hides all building colliders.
     */
    public void unselectBuildings() {
        for (Building3D[] building3DS : board) {
            for (Building3D building3D: building3DS) {
                building3D.unselect();
            }
        }
    }

    /**
     * Unselect not red buildings. This method hides all building colliders that are not colored red.
     */
    public void unselectNotRedBuildings() {
        for (Building3D[] building3DS : board) {
            for (Building3D building3D: building3DS) {
                building3D.unselectNotRed();
            }
        }
    }

    /**
     * Converts a ClientBoard object into a Board3D object.
     *
     * @param board the ClientBoard object
     */
    public void fromClientBoard(ClientBoard board) {
        for(int i = 0; i < 5; i ++) {
            for(int j = 0; j < 5; j ++) {
                Vector2 v = new Vector2(i, j);
                // building differences
                for(int k = getBuilding(v).getHeight(); k < board.getHeight(v); k++) {
                    getBuilding(v).increaseHeight();
                }
                if(board.getDome(v) && !getBuilding(v).hasDome())
                    getBuilding(v).buildDome();
            }
        }

        int workersCount = countWorkers(board);
        if(workersCount == playerWorkers.size()) {
            updateBoard(board);
        }else if(workersCount > playerWorkers.size()) {
            Vector2 pos = findWorkerDiff(board);
            addWorker(pos, board.getWorkerPlayer(pos), false);
        } else {
            while (workersCount < playerWorkers.size()) {
                removeWorker(findWorkerDiff(board));
            }
        }
    }

    /**
     * Remove worker at a specified position.
     *
     * @param pos the position.
     */
    public void removeWorker(Vector2 pos) {
        group.getChildren().remove(getWorkerAt(pos));
        playerWorkers.remove(getWorkerAt(pos));
    }

    /**
     * Add worker at a specified position, with specified id.
     *
     * @param pos     the position
     * @param id      the id of the worker
     * @param isWoman flag that sets which model to load, if female or male
     */
    public void addWorker(Vector2 pos, int id, boolean isWoman) {
        Worker3D worker3D = new Worker3D(id, isWoman);
        worker3D.setPosition(pos, getBuilding(pos).getHeight());
        playerWorkers.add(worker3D);
        group.getChildren().add(worker3D);
    }

    /**
     * Gets worker at a specified position.
     *
     * @param pos the position
     * @return the worker at pos
     */
    public Worker3D getWorkerAt(Vector2 pos) {
        for(Worker3D worker3D: playerWorkers) {
            if(worker3D.getPosition().equals(pos)) {
                return worker3D;
            }
        }
        return null;
    }

    /**
     * Gets the number of player workers on the board.
     *
     * @return the number of player workers on the board
     */
    public int getPlayerWorkersSize() {
        return playerWorkers.size();
    }


    private int countWorkers(ClientBoard board) {
        int count = 0;
        for(int i = 0; i < 5; i ++) {
            for(int j = 0; j < 5; j++) {
                Vector2 v = new Vector2(i, j);
               if(board.getWorkerPlayer(v) != -1) {
                   count ++;
               }
            }
        }
        return count;
    }

    private Vector2 findWorkerDiff(ClientBoard board) {
        for(int i = 0; i < 5; i ++) {
            for (int j = 0; j < 5; j++) {
                Vector2 v = new Vector2(i, j);
                if((board.getWorkerPlayer(v) != -1 && getWorkerAt(v) == null) || (board.getWorkerPlayer(v) == -1 && getWorkerAt(v) != null)) {
                    return v;
                }
            }
        }
        return null;
    }

    private void updateBoard(ClientBoard board) {
        List<Worker3D> moved = new ArrayList<>();
        for(int i = 0; i < 5; i ++) {
            for (int j = 0; j < 5; j++) {
                Vector2 v = new Vector2(i, j);
                if(board.getWorkerPlayer(v) != -1) {
                    for(Worker3D worker3D: playerWorkers) {
                        if(!moved.contains(worker3D) && worker3D.getWorkerId() == board.getWorkerPlayer(v)) {
                            moved.add(worker3D);
                            worker3D.setPosition(v, getBuilding(v).getHeight());
                            break;
                        }
                    }
                }
            }
        }
    }

    private void prepareModel(String model, Group group, int x, int y, int z) {
        MeshView meshView;
        PhongMaterial material = new PhongMaterial();
        meshView = Loader.loadObj("/models/" + model + ".obj");
        material.setDiffuseMap(new Image(String.valueOf(getClass().getResource("/textures/" + model + ".png"))));
        if(model.equals("Cliff")) {
            //material.setSpecularMap(new Image(String.valueOf(getClass().getResource("/textures/Cliff_Emission.png"))));
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