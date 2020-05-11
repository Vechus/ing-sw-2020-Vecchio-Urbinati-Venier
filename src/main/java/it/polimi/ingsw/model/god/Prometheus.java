package it.polimi.ingsw.model.god;

import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.util.ActionType;
import org.testng.internal.collections.Pair;

import java.util.ArrayList;
import java.util.Arrays;

public class Prometheus extends God {

    public Prometheus(Board board, Player player) {
        super(board, player);
        this.moveValidationFunctions = new ArrayList<>(
                Arrays.asList(GodValidationMethods::isTargetPosWithinBoard,
                        GodValidationMethods::isCellWorkersFree,
                        GodValidationMethods::isTargetPosOnDifferentCell,
                        GodValidationMethods::isTargetPosDomesFree,
                        GodValidationMethods::isTargetPosAdjacent,
                        GodValidationMethods::isMoveHeightLessThanOne,
                        this::hasBuiltBefore
                ));
    }

    @Override
    public  boolean chooseAction (Action action){
        if (chosenWorker==null){ chosenWorker=action.getWorker(); }
        if(this.hasMoved ) {
            if (action.getType() == ActionType.BUILD && chosenWorker == action.getWorker()) {
                if (buildBlock(action)) {
                    this.hasBuilt=true;
                    return true;
                }
            } else if (action.getType() == ActionType.BUILD_DOME && chosenWorker == action.getWorker()) {
                if (buildDome(action)) {
                    this.hasBuilt=true;
                    return true;
                }
            }


        }else if (action.getType()== ActionType.MOVE&& chosenWorker==action.getWorker()) {
            if (move(action)) {
                this.hasMoved = true;
                return true;
            }
        } else if(!hasBuilt && action.getType()== ActionType.BUILD&& chosenWorker==action.getWorker()){
            if (buildBlock(action)){
                this.hasBuilt=true;
                return true;
            }
        } else if(!hasBuilt && action.getType()== ActionType.BUILD_DOME&& chosenWorker==action.getWorker()){
            if (buildBlock(action)){
                this.hasBuilt=true;
                return true;
            }
        }
        if(action.getType()== ActionType.END_TURN ) {
            if (endTurn()) {
                this.hasFinishedTurn = true;
                return true;
            }
        }
        return false;
    }

    public boolean hasBuiltBefore (Pair<Action, Board> actionBoardPair){
        Action action = actionBoardPair.first();
        return !hasBuilt || this.board.getHeight(action.getTargetPos()) - this.board.getHeight(action.getWorkerPos()) <= 0;
    }
}
