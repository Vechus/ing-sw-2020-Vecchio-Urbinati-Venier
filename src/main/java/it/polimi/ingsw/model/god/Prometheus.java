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
        name = "Prometheus";

        this.moveValidationFunctions = new ArrayList<>(
                Arrays.asList(GodValidationMethods::isTargetPosWithinBoard,
                        GodValidationMethods::isCellWorkersFree,
                        GodValidationMethods::isTargetPosOnDifferentCell,
                        GodValidationMethods::isTargetPosDomesFree,
                        GodValidationMethods::isTargetPosAdjacent,
                        GodValidationMethods::isMoveHeightLessThanOne,
                        this::noUpIfPrebuilt
                ));
    }

    @Override
    protected void createActionGraph() {
        super.createActionGraph();
        int preBuiltState = actionGraph.addState();
        actionGraph.addTransition(actionGraph.INITIAL_STATE_IDX, preBuiltState, ActionType.BUILD);
        actionGraph.addTransition(actionGraph.INITIAL_STATE_IDX, preBuiltState, ActionType.BUILD_DOME);
        int movedState = actionGraph.getNextState(actionGraph.INITIAL_STATE_IDX, ActionType.MOVE);
        actionGraph.addTransition(preBuiltState, movedState, ActionType.MOVE);
    }

    public boolean noUpIfPrebuilt(Pair<Action, Board> actionBoardPair){
        Action action = actionBoardPair.first();
        return buildCtr == 0 || this.board.getHeight(action.getTargetPos()) - this.board.getHeight(action.getWorkerPos()) <= 0;
    }
}
