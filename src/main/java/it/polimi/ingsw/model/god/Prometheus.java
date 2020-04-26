package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import org.testng.internal.collections.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Prometheus extends God {

    public Prometheus(Board board, Player player) {
        super(board, player);
    }

    List<Function<Pair<Action, Board>, Boolean>> moveValidationFunctions = new ArrayList<>(
            Arrays.asList(GodValidationMethods::isTargetPosWithinBoard,
                    GodValidationMethods::isCellWorkersFree,
                    GodValidationMethods::isTargetPosOnDifferentCell,
                    GodValidationMethods::isTargetPosDomesFree,
                    GodValidationMethods::isTargetPosAdjacent,
                    GodValidationMethods::isMoveHeightLessThanOne,
                    this::hasBuiltBefore
            ));

    @Override
    public  boolean chooseAction (Action action){
        if (chosenWorker==null){ chosenWorker=action.getWorker(); }
        if(this.hasMoved ){
            if(action.getType()==Action.ActionType.BUILD&& chosenWorker==action.getWorker()){
                if(buildBlock(action)) {
                    return true;
                }
            }else if(action.getType()==Action.ActionType.BUILD_DOME&& chosenWorker==action.getWorker()){
                if(buildDome(action)) {
                    return true;
                }
            }



        }else if (action.getType()==Action.ActionType.MOVE&& chosenWorker==action.getWorker()) {
            if (move(action)) {
                this.hasMoved = true;
                return true;
            }
        } else if(hasBuilt==false && action.getType()==Action.ActionType.BUILD&& chosenWorker==action.getWorker()){
            if (buildBlock(action)){
                hasBuilt=true;
            }
        } else if(hasBuilt==false && action.getType()==Action.ActionType.BUILD_DOME&& chosenWorker==action.getWorker()){
            if (buildBlock(action)){
                hasBuilt=true;
            }
        }
        if(action.getType()==Action.ActionType.END_TURN ) {
            if (endTurn()) {
                this.hasFinishedTurn = true;
                return true;
            }
        }
        return false;
    }


    public boolean hasBuiltBefore (Pair<Action, Board> actionBoardPair){
        Action action = actionBoardPair.first();
        if(hasBuilt==true && this.board.getHeight(action.getTargetPos()) - this.board.getHeight(action.getWorkerPos())>0){
            return false;
        }
        return true;
    }


}
