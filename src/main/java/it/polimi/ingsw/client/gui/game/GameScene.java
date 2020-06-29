package it.polimi.ingsw.client.gui.game;

import it.polimi.ingsw.client.ClientBoard;
import it.polimi.ingsw.client.events.GameEvent;
import it.polimi.ingsw.client.events.GameEventHandler;
import it.polimi.ingsw.client.events.SelectOnGridEvent;
import it.polimi.ingsw.client.gui.Panel2dController;
import it.polimi.ingsw.util.ActionType;
import it.polimi.ingsw.util.Vector2;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;

public class GameScene {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 700;
    private static final double CAMERA_RAY = 50;
    public enum ClientGameStage3D {PLACE_FIRST_WORKER, PLACE_SECOND_WORKER, ACTION, WAIT};
    private ClientGameStage3D clientGameStage3D = ClientGameStage3D.PLACE_FIRST_WORKER;
    private int cameraAngle = 0;
    private final Label bottomMessage = new Label();
    private final Board3D board3D;
    private final Group group;
    private Camera camera;
    private final BorderPane pane = new BorderPane();
    private final Scene scene;
    private Rotate cameraRotate;
    private Vector2 selected;
    private final Panel2dController panel2dController;
    private int playerID;


    public GameScene(Stage stage) {
        this.group = new Group();
        this.board3D = new Board3D(group);
        this.scene = new Scene(pane);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/Panel2d.fxml"));
        Parent panel2d = null;
        try{
            panel2d = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        panel2dController = loader.getController();
        panel2dController.init();

        scene.setFill(Color.STEELBLUE);
        initCamera();
        initSubScene(panel2d);
        bottomMessage.getStylesheets().add(getClass().getResource("/scenes/css/MainMenuStyle.css").toExternalForm());
        //bottomMessage.prefHeight(300);
        //bottomMessage.prefWidth(150);
        bottomMessage.setStyle("-fx-alignment: center");
        bottomMessage.setStyle("-fx-font-weight: bolder");
        //pane.setBottom(bottomMessage);
    }

    public Board3D getBoard3D() {
        return board3D;
    }

    public Group getGroup() {
        return group;
    }

    public void updateBoard(ClientBoard clientBoard) {
        board3D.fromClientBoard(clientBoard);
    }

    private void initCamera() {
        Group cameraGroup = new Group();
        camera = new PerspectiveCamera(true);
        camera.setNearClip(1);
        camera.setFarClip(200);
        cameraGroup.getChildren().add(camera);
        Translate cameraPivot = new Translate();
        cameraPivot.setX(0);
        cameraPivot.setY(3);
        cameraPivot.setZ(0);
        cameraRotate = new Rotate(0, Rotate.Y_AXIS);
        cameraGroup.getTransforms().addAll(
                cameraPivot,
                cameraRotate,
                new Rotate(-45, Rotate.X_AXIS),
                new Translate(0, 0, CAMERA_RAY * (-1))
        );
    }

    private void initSubScene(Parent panel2d) {
        pane.setRight(panel2d);

        pane.setStyle("-fx-background-color: transparent");
        SubScene subScene3D = new SubScene(this.group, WIDTH, HEIGHT, true, SceneAntialiasing.BALANCED);
        subScene3D.setFill(Color.LIGHTSKYBLUE);
        subScene3D.setCamera(this.camera);
        pane.setCenter(subScene3D);

        scene.addEventHandler(KeyEvent.KEY_RELEASED, keyEvent -> {
            switch (keyEvent.getCode()) {
                case A -> cameraRotationAnimation(45);
                case D -> cameraRotationAnimation(-45);
            }
        });
        scene.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (mouseEvent.getTarget() instanceof BuildingCollider) {
                BuildingCollider target = ((BuildingCollider) mouseEvent.getTarget());
                handleSelected(target);
                pane.fireEvent(new SelectOnGridEvent(target.getPos()));
            } else if (mouseEvent.getTarget() instanceof BuildingBlock) {
                handleSelected(board3D.getBuilding(((BuildingBlock) mouseEvent.getTarget()).getPos()).getCollider());
                pane.fireEvent(new SelectOnGridEvent(((BuildingBlock) mouseEvent.getTarget()).getPos()));
            } else if (mouseEvent.getTarget() instanceof Worker3D) {
                handleSelected(board3D.getBuilding(((Worker3D) (mouseEvent.getTarget())).getPosition()).getCollider());
            } else if (mouseEvent.getTarget() instanceof MeshView) {
                board3D.unselectBuildings();
                selected = null;
            }
        });
        pane.addEventHandler(GameEvent.GAME_EVENT_TYPE, new GameEventHandler() {
            @Override
            public void onSelectGrid(Vector2 pos) {}

            @Override
            public void onPlaceWorker(Vector2 pos) {
                if(clientGameStage3D == ClientGameStage3D.PLACE_FIRST_WORKER)
                    playerID = board3D.getPlayerWorkersSize() / 2;
                //board3D.addWorker(pos, playerID, clientGameStage3D == ClientGameStage3D.PLACE_SECOND_WORKER);
                if(clientGameStage3D == ClientGameStage3D.PLACE_FIRST_WORKER)
                    clientGameStage3D = ClientGameStage3D.PLACE_SECOND_WORKER;
                else
                    clientGameStage3D = ClientGameStage3D.WAIT;
            }
        });
    }

    private void cleanBottom() {
        pane.setBottom(null);
    }

    private void cameraRotationAnimation(int degrees) {
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

    public void getAllowedActions(List<ActionType> allowedActions) {
        panel2dController.addButtons(allowedActions);
        unselectAll();
        bottomMessage.setTextFill(Color.BLACK);
        allowedActions.forEach(a -> {
            switch (a) {
                case PLACE_WORKER -> bottomMessage.setText("Place a worker.");
                case END_TURN -> bottomMessage.setText("You can end your turn.");
                default -> bottomMessage.setText("Select a worker to move or build.");
            }
        });
        if(clientGameStage3D == ClientGameStage3D.WAIT)
            clientGameStage3D = ClientGameStage3D.ACTION;
    }

    public void setPlayerGod(String god) {
        panel2dController.setPlayerGodImage(god);
    }

    public void setPlayerName(String playerName) {
        panel2dController.setPlayerNameLabel(playerName);
    }

    public void setClientGameStage3D(ClientGameStage3D clientGameStage3D) {
        this.clientGameStage3D = clientGameStage3D;
    }

    public ClientGameStage3D getClientGameStage3D() {
        return clientGameStage3D;
    }

    public void displayBottomMessage(String message, Color color) {
        bottomMessage.setText(message);
        bottomMessage.setTextFill(color);
    }

    public void unselectAll() {
        selected = null;
        board3D.unselectBuildings();
        panel2dController.setPos(null);
        panel2dController.setSelected(null);
    }

    private void handleSelected(BuildingCollider target) {
        switch (clientGameStage3D) {
            case PLACE_FIRST_WORKER, PLACE_SECOND_WORKER -> {
                board3D.unselectBuildings();
                if(getBoard3D().getWorkerAt(target.getPos()) == null) {
                    selected = target.getPos();
                    panel2dController.setPos(selected);
                    panel2dController.setSelected(null);
                    board3D.getBuilding(selected).selectRed();
                } else {
                    selected = null;
                }
            }
            case WAIT -> {
                board3D.unselectBuildings();
                board3D.getBuilding(target.getPos()).select();
            }
            case ACTION -> {
                board3D.unselectNotRedBuildings();
                // if first select or the player **decides to reset**
                if(selected == null || (panel2dController.getPos() != null && panel2dController.getSelected() != null)) {
                    selected = target.getPos();
                    panel2dController.setPos(null);
                    panel2dController.setSelected(null);
                    if(getBoard3D().getWorkerAt(selected) != null) {
                        if (getBoard3D().getWorkerAt(selected).getWorkerId() == playerID) {
                            getBoard3D().getBuilding(selected).selectRed();
                            panel2dController.setPos(selected);
                        }
                    }
                } else if(panel2dController.getPos() != null) {
                    selected = target.getPos();
                    panel2dController.setSelected(selected);
                    board3D.getBuilding(selected).select();
                }
            }
        }
    }
}
