package it.polimi.ingsw.model.god;
import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.util.ActionType;
import it.polimi.ingsw.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;

public class Artemis extends God {
    public Artemis(Board board, Player player) {
        super(board, player);
        name = "Artemis";

        this.moveValidationFunctions = new ArrayList<>(
                Arrays.asList(GodValidationMethods::isTargetPosWithinBoard,
                        GodValidationMethods::isCellWorkersFree,
                        GodValidationMethods::isTargetPosOnDifferentCell,
                        GodValidationMethods::isTargetPosDomesFree,
                        GodValidationMethods::isTargetPosAdjacent,
                        GodValidationMethods::isMoveHeightLessThanOne,
                        this::isCellDifferentFromInitialSpace
                ));
    }

    @Override
    protected void createActionGraph() {
        super.createActionGraph();
        int secondMovedState = actionGraph.addState();
        int movedState = actionGraph.getNextState(actionGraph.INITIAL_STATE_IDX, ActionType.MOVE);
        int builtState = actionGraph.getNextState(movedState, ActionType.BUILD);
        actionGraph.addTransition(movedState, secondMovedState, ActionType.MOVE);
        actionGraph.addTransition(secondMovedState, builtState, ActionType.BUILD);
        actionGraph.addTransition(secondMovedState, builtState, ActionType.BUILD_DOME);
    }

    public boolean isCellDifferentFromInitialSpace(Pair<Action, Board> actionBoardPair){
        Action action = actionBoardPair.first();
        return moveCtr != 1 || !action.getTargetPos().equals(initPos);
    }

}