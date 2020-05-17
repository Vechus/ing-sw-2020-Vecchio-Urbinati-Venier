package it.polimi.ingsw.client.gui.game;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.io.IOException;

public class GameScene {
    private static final int WIDTH = 1100;
    private static final int HEIGHT = 750;
    private static final double CAMERA_RAY = 50;
    private int cameraAngle = 0;
    private final Board3D board3D;
    private final Group group;
    private Camera camera;
    private SubScene subScene3D;
    private final BorderPane pane = new BorderPane();
    private Scene scene;
    private Rotate cameraRotate;

    public GameScene(Parent panel2d) {
        this.group = new Group();
        this.board3D = new Board3D(group);
        scene = new Scene(pane);
        initCamera();
        initSubScene(panel2d);
    }

    public Board3D getBoard3D() {
        return board3D;
    }

    public Group getGroup() {
        return group;
    }

    private void initCamera() {
        camera = new PerspectiveCamera(true);
        camera.setNearClip(1);
        camera.setFarClip(200);
        Translate cameraPivot = new Translate();
        cameraPivot.setX(0);
        cameraPivot.setY(3);
        cameraPivot.setZ(0);
        cameraRotate = new Rotate(0, Rotate.Y_AXIS);
        camera.getTransforms().addAll(
                cameraPivot,
                cameraRotate,
                new Rotate(-45, Rotate.X_AXIS),
                new Translate(0, 0, CAMERA_RAY * (-1))
        );
    }

    private void initSubScene(Parent panel2d) {
        pane.setRight(panel2d);
        pane.setStyle("-fx-background-color: transparent");
        subScene3D = new SubScene(this.group, WIDTH, HEIGHT, true, SceneAntialiasing.BALANCED);
        subScene3D.setFill(Color.LIGHTSKYBLUE);
        subScene3D.setCamera(this.camera);
        pane.setCenter(subScene3D);

        scene.addEventHandler(KeyEvent.KEY_RELEASED, keyEvent -> {
            switch (keyEvent.getCode()) {
                case A -> {
                    System.out.println("Pressed A");
                    Timeline rotPosAnimation = new Timeline(
                            new KeyFrame(
                                    Duration.seconds(0),
                                    new KeyValue(cameraRotate.angleProperty(), cameraAngle)
                            ),
                            new KeyFrame(
                                    Duration.seconds(.5),
                                    new KeyValue(cameraRotate.angleProperty(), cameraAngle + 45)
                            )
                    );
                    cameraAngle += 45;
                    rotPosAnimation.play();
                }
                case D -> {
                    System.out.println("Pressed D");
                    Timeline rotNegAnimation = new Timeline(
                            new KeyFrame(
                                    Duration.seconds(0),
                                    new KeyValue(cameraRotate.angleProperty(), cameraAngle)
                            ),
                            new KeyFrame(
                                    Duration.seconds(.5),
                                    new KeyValue(cameraRotate.angleProperty(), cameraAngle - 45)
                            )
                    );
                    cameraAngle -= 45;
                    rotNegAnimation.play();
                }
                case ESCAPE -> displayPauseMenu();
            }
        });
        scene.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            System.out.println("mouse click " + mouseEvent.getTarget());
            if(mouseEvent.getTarget() instanceof BuildingCollider) {
                board3D.unselectBuildings();
                BuildingCollider target = ((BuildingCollider) mouseEvent.getTarget());
                target.select();
                board3D.getBuilding(target.getCoordX(), target.getCoordY()).increaseHeight();
            } else if(mouseEvent.getTarget() instanceof BuildingBlock) {
                board3D.unselectBuildings();
                BuildingBlock target = ((BuildingBlock) mouseEvent.getTarget());
                board3D.getBuilding(target.getCoordX(), target.getCoordY()).increaseHeight();
            }
            else {
                board3D.unselectBuildings();
            }
        });
    }

    private void displayPauseMenu() {
        // TODO
    }

    public Scene getScene() {
        return scene;
    }
}
