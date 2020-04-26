package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.util.Vector2;
import org.testng.internal.collections.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Demeter extends God {
    private int counterDemeterBuild=0;
    private Vector2 posFirstBuild;

    public Demeter(Board board, Player player) {
        super(board, player);
    }

    List<Function<Pair<Action, Board>, Boolean>> buildBlockValidationFunctions = new ArrayList<>(
            Arrays.asList(GodValidationMethods::isTargetPosWithinBoard,
                    GodValidationMethods::isCellWorkersFree,
                    GodValidationMethods::isTargetPosOnDifferentCell,
                    GodValidationMethods::isTargetPosDomesFree,
                    GodValidationMethods::isTargetPosAdjacent,
                    GodValidationMethods::isBuildingHeightLessThanThree,
                    this::isCellDifferentWhenBuilding
            ));

    List<Function<Pair<Action, Board>, Boolean>> buildDomeValidationFunctions = new ArrayList<>(
            Arrays.asList(GodValidationMethods::isTargetPosWithinBoard,
                    GodValidationMethods::isCellWorkersFree,
                    GodValidationMethods::isTargetPosOnDifferentCell,
                    GodValidationMethods::isTargetPosDomesFree,
                    GodValidationMethods::isTargetPosAdjacent,
                    GodValidationMethods::isBuildingHeightThree,
                    this::isCellDifferentWhenBuilding
            ));

    @Override
    public  boolean chooseAction (Action action){
        if (chosenWorker==null){ chosenWorker=action.getWorker(); }
        if(this.hasMoved ){
            if(counterDemeterBuild==0){
                if(action.getType()==Action.ActionType.BUILD&& chosenWorker==action.getWorker()){
                    if(buildBlock(action)) {
                        posFirstBuild=action.getTargetPos();
                        counterDemeterBuild++;
                        return true;
                    }
                }else if(action.getType()==Action.ActionType.BUILD_DOME&& chosenWorker==action.getWorker()){
                    if (buildDome(action)){
                        counterDemeterBuild++;
                        return true;
                    }
                }
            }
            if(counterDemeterBuild==1) {
                if (action.getType() == Action.ActionType.BUILD&& chosenWorker==action.getWorker()) {
                    if (buildBlock(action)) {
                        return true;
                    }
                }else if (action.getType() == Action.ActionType.BUILD_DOME&& chosenWorker==action.getWorker()) {
                    if (buildDome(action)){
                        return true;
                    }
                }
            }
        }else if (action.getType()==Action.ActionType.MOVE&& chosenWorker==action.getWorker()){
            if(move(action)) {
                this.hasMoved=true;
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

    public boolean isCellDifferentWhenBuilding(Pair<Action, Board> actionBoardPair){
        Action action = actionBoardPair.first();
        if(counterDemeterBuild==1 && action.getTargetPos().equals(posFirstBuild)) { return false; }
        return true;
    }
}
