package it.polimi.ingsw.model.god;

import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.util.ActionType;
import org.testng.internal.collections.Pair;

import java.util.ArrayList;
import java.util.Arrays;

public class Triton extends God {
    public Triton(Board board, Player player) {
        super(board, player);
        this.moveValidationFunctions = new ArrayList<>(
                Arrays.asList(GodValidationMethods::isTargetPosWithinBoard,
                        GodValidationMethods::isCellWorkersFree,
                        GodValidationMethods::isTargetPosOnDifferentCell,
                        GodValidationMethods::isTargetPosDomesFree,
                        GodValidationMethods::isTargetPosAdjacent,
                        GodValidationMethods::isMoveHeightLessThanOne,
                        this::isMoveOnPerimeter
                ));
    }

    @Override
    public  boolean chooseAction (Action action){
        if (chosenWorker==null){ chosenWorker=action.getWorker(); }

        if(this.hasMoved){
            if(action.getType()== ActionType.MOVE&& chosenWorker==action.getWorker()&&hasBuilt==false){
                if(move(action)){
                    return true;
                }
            }
            if(action.getType()== ActionType.BUILD&& chosenWorker==action.getWorker()&&hasBuilt==false){
                if(buildBlock(action)) {
                    hasBuilt=true;
                    return true;
                }
            }else if(action.getType()== ActionType.BUILD_DOME&& chosenWorker==action.getWorker()&&hasBuilt==false){
                if(buildDome(action)) {
                    hasBuilt=true;
                    return true;
                }
            }
        }else if (action.getType()== ActionType.MOVE&& chosenWorker==action.getWorker()&&hasBuilt==false) {
            if (move(action)) {
                this.hasMoved = true;
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


    public boolean isMoveOnPerimeter(Pair<Action, Board> actionBoardPair) {
        Action action = actionBoardPair.first();
        if(hasMoved && !(action.getWorkerPos().getY()==4 || action.getWorkerPos().getX()==4|| action.getWorkerPos().getX()==0|| action.getWorkerPos().getY()==0))
            return false;
        return true;
    }
}
