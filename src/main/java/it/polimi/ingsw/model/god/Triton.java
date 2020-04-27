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

public class Triton extends God {
    public Triton(Board board, Player player) {
        super(board, player);
    }
    boolean isTritonInPerimeter;
    List<Function<Pair<Action, Board>, Boolean>> moveValidationFunctions = new ArrayList<>(
            Arrays.asList(GodValidationMethods::isTargetPosWithinBoard,
                    GodValidationMethods::isCellWorkersFree,
                    GodValidationMethods::isTargetPosOnDifferentCell,
                    GodValidationMethods::isTargetPosDomesFree,
                    GodValidationMethods::isTargetPosAdjacent,
                    GodValidationMethods::isMoveHeightLessThanOne,
                    this::isMoveOnPerimenter
            ));

    @Override
    public  boolean chooseAction (Action action){
        if (chosenWorker==null){ chosenWorker=action.getWorker(); }

        if(this.hasMoved ){
            if(action.getType()==Action.ActionType.MOVE && isTritonInPerimeter==true){
                if(move(action)){
                    return true;
                }
            }
            if(action.getType()==Action.ActionType.BUILD){
                if(buildBlock(action)) {
                    return true;
                }
            }else if(action.getType()==Action.ActionType.BUILD_DOME){
                if(buildDome(action)) {
                    return true;
                }
            }
        }else if (action.getType()==Action.ActionType.MOVE) {
            if (move(action)) {
                this.hasMoved = true;
                return true;
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


    public boolean isMoveOnPerimenter(Pair<Action, Board> actionBoardPair) {
        Action action = actionBoardPair.first();
        if(action.getWorker().getPosition().getY()==4 || action.getWorker().getPosition().getX()==4){
            isTritonInPerimeter=true;
        }
        return true;
    }
}
