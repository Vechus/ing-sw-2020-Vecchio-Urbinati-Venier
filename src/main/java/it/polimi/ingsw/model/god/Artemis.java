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

public class Artemis extends God {
    private int counterArtemisMoves=0;
    private Vector2 initialPos;
    public Artemis(Board board, Player player) {
        super(board, player);
    }


    List<Function<Pair<Action, Board>, Boolean>> moveValidationFunctions = new ArrayList<>(
            Arrays.asList(GodValidationMethods::isTargetPosWithinBoard,
                    GodValidationMethods::isCellWorkersFree,
                    GodValidationMethods::isTargetPosOnDifferentCell,
                    GodValidationMethods::isTargetPosDomesFree,
                    GodValidationMethods::isTargetPosAdjacent,
                    GodValidationMethods::isMoveHeightLessThanOne,
                    this::isCellDifferentFromInitialSpace
            ));


    @Override
    public boolean chooseAction (Action action){
        if (chosenWorker==null){ chosenWorker=action.getWorker(); }

        if(counterArtemisMoves==0 && action.getType()==Action.ActionType.MOVE){
            initialPos=action.getWorkerPos();
            if (move(action)) {
                counterArtemisMoves++;
                return true;
            }
        }
        if(counterArtemisMoves>=1){
            if(action.getType()==Action.ActionType.MOVE && counterArtemisMoves == 1&& chosenWorker==action.getWorker()){
                if(move(action)) {
                    counterArtemisMoves++;
                    return true;
                }
            }
            if(action.getType()==Action.ActionType.BUILD&& chosenWorker==action.getWorker()){
                if(buildBlock(action)) {
                    return true;
                }
            }
            if(action.getType()==Action.ActionType.BUILD_DOME&& chosenWorker==action.getWorker()) {
                if (buildDome(action)) {
                    return true;
                }
            }
        }if(action.getType()==Action.ActionType.END_TURN ) {
            if (endTurn()) {
                this.hasFinishedTurn = true;
                return true;
            }
        }
        return false;
    }



    public boolean isCellDifferentFromInitialSpace(Pair<Action, Board> actionBoardPair){
        Action action = actionBoardPair.first();
        if(counterArtemisMoves==1 && action.getTargetPos().equals(initialPos)){
            return false;
        }
        return true;
    }

}