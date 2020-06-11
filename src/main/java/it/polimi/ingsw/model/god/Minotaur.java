package it.polimi.ingsw.model.god;

import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.util.ActionType;
import it.polimi.ingsw.util.Vector2;
import it.polimi.ingsw.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Minotaur extends God {

    List<Function<Pair<Action, Board>, Boolean>> pushValidationFunctions;

    public Minotaur(Board board, Player player) {
        super(board, player);
        name = "Minotaur";

        this.pushValidationFunctions = new ArrayList<>(
                Arrays.asList(GodValidationMethods::isTargetPosWithinBoard,
                        GodValidationMethods::isCellWorkersFree,
                        GodValidationMethods::isTargetPosOnDifferentCell,
                        GodValidationMethods::isTargetPosDomesFree,
                        GodValidationMethods::isTargetPosAdjacent
                ));

        this.moveValidationFunctions = new ArrayList<>(
                Arrays.asList(GodValidationMethods::isTargetPosWithinBoard,
                        GodValidationMethods::isTargetPosOnDifferentCell,
                        GodValidationMethods::isTargetPosDomesFree,
                        GodValidationMethods::isTargetPosAdjacent,
                        GodValidationMethods::isMoveHeightLessThanOne,
                        this::isPushPossible
                ));
    }

    /**
     * Checks if it's possible to push the worker occupying the target cell in the same direction as the move.
     * If the target cell is free, then true by default.
     * @param actionBoardPair bundled action and board
     * @return true if the target cell is empty or its worker can be pushed away in the same direction
     */
    private boolean isPushPossible(Pair<Action, Board> actionBoardPair){
        if(GodValidationMethods.isCellWorkersFree(actionBoardPair)) return true;
        Action action = actionBoardPair.first();
        Board board = actionBoardPair.second();
        Worker otherWorker = board.getWorker(action.getTargetPos());
        Vector2 delta = action.getTargetPos().sub(action.getWorkerPos());
        Vector2 movedPos = action.getTargetPos().add(delta);
        Action movedAction = new Action(otherWorker, movedPos, ActionType.MOVE);
        return checkConditions(this.pushValidationFunctions, movedAction);
    }

    @Override
    public boolean move(Action action){
        if(!this.isMoveValid(action)) {
            return false;
        }
        Worker otherWorker = board.getWorker(action.getTargetPos());
        if(otherWorker != null) {
            Vector2 delta = action.getTargetPos().sub(action.getWorkerPos());
            Vector2 movedPos = action.getTargetPos().add(delta);
            board.moveWorker(new Action(otherWorker, movedPos, ActionType.MOVE));
        }
        board.moveWorker(action);
        return true;
    }
}
