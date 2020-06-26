package it.polimi.ingsw.model.god;

import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.util.ActionType;
import it.polimi.ingsw.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Simple god: Your Build: Your Worker may
 * build one additional block (not
 * dome) on top of your first block.
 */
public class Hephaestus extends God {
    public Hephaestus(Board board, Player player) {
        super(board, player);
        name = "Hephaestus";

        this.buildBlockValidationFunctions = new ArrayList<>(
                Arrays.asList(GodValidationMethods::isTargetPosWithinBoard,
                        GodValidationMethods::isCellWorkersFree,
                        GodValidationMethods::isTargetPosOnDifferentCell,
                        GodValidationMethods::isTargetPosDomesFree,
                        GodValidationMethods::isTargetPosAdjacent,
                        GodValidationMethods::isBuildingHeightLessThanThree,
                        this::isCellTheSame
                ));

    }

    @Override
    protected void createActionGraph() {
        super.createActionGraph();
        int movedState = actionGraph.getNextState(actionGraph.INITIAL_STATE_IDX, ActionType.MOVE);
        int builtState = actionGraph.getNextState(movedState, ActionType.BUILD);
        int secondBuildState = actionGraph.addState();
        actionGraph.addTransition(builtState, secondBuildState, ActionType.BUILD);
        actionGraph.addTransition(builtState, secondBuildState, ActionType.BUILD_DOME);
        actionGraph.addTransition(secondBuildState, actionGraph.FINAL_STATE_IDX, ActionType.END_TURN);
    }

    public boolean isCellTheSame(Pair<Action, Board> actionBoardPair){
        Action action = actionBoardPair.first();
        return buildCtr != 1 || action.getTargetPos().equals(buildPos);
    }







}