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
        name = "Triton";

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
    protected void createActionGraph() {
        super.createActionGraph();
        int movedState = actionGraph.getNextState(actionGraph.INITIAL_STATE_IDX, ActionType.MOVE);
        actionGraph.addTransition(movedState, movedState, ActionType.MOVE);
    }

    public boolean isMoveOnPerimeter(Pair<Action, Board> actionBoardPair) {
        Action action = actionBoardPair.first();
        return moveCtr == 0 || (action.getWorkerPos().getY() == 4 || action.getWorkerPos().getX() == 4 || action.getWorkerPos().getX() == 0 || action.getWorkerPos().getY() == 0);
    }
}
