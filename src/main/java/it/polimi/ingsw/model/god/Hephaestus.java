package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.util.Vector2;
import org.testng.internal.collections.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Hephaestus extends God {
    private Vector2 posFirstBuild;
    private int counterHephaestusBuilds = 0;

    public Hephaestus(Board board, Player player) {
        super(board, player);
    }

    List<Function<Pair<Action, Board>, Boolean>> buildBlockValidationFunctions = new ArrayList<>(
            Arrays.asList(GodValidationMethods::isTargetPosWithinBoard,
                    GodValidationMethods::isCellWorkersFree,
                    GodValidationMethods::isTargetPosOnDifferentCell,
                    GodValidationMethods::isTargetPosDomesFree,
                    GodValidationMethods::isTargetPosAdjacent,
                    GodValidationMethods::isBuildingHeightLessThanThree
            ));


    @Override
    public boolean chooseAction(Action action) {
        if (chosenWorker==null){ chosenWorker=action.getWorker(); }
        if (this.hasMoved) {
            if (counterHephaestusBuilds==0){
                if (action.getType() == Action.ActionType.BUILD) {
                    if (buildBlock(action)) {
                        posFirstBuild=action.getTargetPos();
                        counterHephaestusBuilds++;
                        return true;
                    }
                } else if (action.getType() == Action.ActionType.BUILD_DOME) {
                    if (buildDome(action)) {
                        return true;
                    }
                }
            }else if(counterHephaestusBuilds==1){
                if (action.getType() == Action.ActionType.BUILD) {
                    if (buildBlock(action)) {
                        return true;
                    }
                }
            }
        } else if (action.getType() == Action.ActionType.MOVE) {
            if (move(action)) {
                this.hasMoved = true;
                return true;
            }
        }if(action.getType()==Action.ActionType.END_TURN ) {
            if (endTurn()) {
                this.hasFinishedTurn = true;
                return true;
            }
        }
        return false;
    }


    public boolean isCellTheSame(Pair<Action, Board> actionBoardPair){
        Action action = actionBoardPair.first();
        if(counterHephaestusBuilds==1 && action.getTargetPos()!=posFirstBuild){
            return false;
        }
        return true;
    }







}