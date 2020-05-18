package it.polimi.ingsw.client.gui.game;

import it.polimi.ingsw.client.events.ChangeSceneEvent;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class GameScene {
    private static final int WIDTH = 1100;
    private static final int HEIGHT = 750;
    private static final double CAMERA_RAY = 50;
    public enum ClientGameStage3D {CHOOSE_GOD, PLACE_WORKER, WAIT, TURN};
    private ClientGameStage3D clientGameStage3D = ClientGameStage3D.CHOOSE_GOD;
    private int cameraAngle = 0;
    private final Board3D board3D;
    private final Group group;
    private Camera camera;
    private SubScene subScene3D;
    private final BorderPane pane = new BorderPane();
    private Scene scene;
    private Rotate cameraRotate;
    private Stage primaryStage;

    public GameScene(Stage stage, Parent panel2d) {
        this.group = new Group();
        this.board3D = new Board3D(group);
        this.scene = new Scene(pane);
        this.primaryStage = stage;
        scene.setFill(Color.STEELBLUE);
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
                case A -> cameraRotationAnimation(45);
                case D -> cameraRotationAnimation(-45);
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
        pane.setEffect(new GaussianBlur());

        VBox pauseRoot = new VBox(5);
        pauseRoot.setAlignment(Pos.CENTER);
        pauseRoot.setStyle("-fx-background-color: rgba(46, 62, 180, 0.4);");
        pauseRoot.setPadding(new Insets(20));
        Label pauseLabel = new Label("Paused");
        pauseLabel.getStylesheets().add(getClass().getResource("/scenes/css/MainMenuStyle.css").toExternalForm());
        pauseRoot.getChildren().add(pauseLabel);
        Button resumeButton = new Button("Resume");
        Button quitButton = new Button("Quit Game");
        resumeButton.getStylesheets().add(getClass().getResource("/scenes/css/Buttons.css").toExternalForm());
        quitButton.getStylesheets().add(getClass().getResource("/scenes/css/Buttons.css").toExternalForm());
        resumeButton.getStyleClass().add("greenButton");
        quitButton.getStyleClass().add("redButton");
        pauseRoot.getChildren().add(resumeButton);
        pauseRoot.getChildren().add(quitButton);

        Stage popupStage = new Stage(StageStyle.TRANSPARENT);
        popupStage.initOwner(primaryStage);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setScene(new Scene(pauseRoot, Color.TRANSPARENT));

        resumeButton.setOnAction(actionEvent -> {
            pane.setEffect(null);
            popupStage.hide();
        });

        quitButton.setOnAction(actionEvent -> {
            pane.setEffect(null);
            popupStage.hide();
            pane.fireEvent(new ChangeSceneEvent("gameToMenu"));
        });
        popupStage.show();
    }

    private void cameraRotationAnimation(int degrees) {
        if(clientGameStage3D == ClientGameStage3D.CHOOSE_GOD) return;
        Timeline rotPosAnimation = new Timeline(
                new KeyFrame(
                        Duration.seconds(0),
                        new KeyValue(cameraRotate.angleProperty(), cameraAngle)
                ),
                new KeyFrame(
                        Duration.seconds(.5),
                        new KeyValue(cameraRotate.angleProperty(), cameraAngle + degrees)
                )
        );
        cameraAngle += degrees;
        rotPosAnimation.play();
    }
    public Scene getScene() {
        return scene;
    }
}
