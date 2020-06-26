package it.polimi.ingsw.model.god;

import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.util.ActionType;
import it.polimi.ingsw.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Advanced god: Your Build: Your Worker may
 * build one additional time, but this
 * cannot be on a perimeter space.
 */
public class Hestia extends God {
    public Hestia(Board board, Player player) {
        super(board, player);
        name = "Hestia";

        this.buildBlockValidationFunctions = new ArrayList<>(
                Arrays.asList(GodValidationMethods::isTargetPosWithinBoard,
                        GodValidationMethods::isCellWorkersFree,
                        GodValidationMethods::isTargetPosOnDifferentCell,
                        GodValidationMethods::isTargetPosDomesFree,
                        GodValidationMethods::isTargetPosAdjacent,
                        GodValidationMethods::isBuildingHeightLessThanThree,
                        this::isBuildOffThePerimeter
                ));

        this.buildDomeValidationFunctions = new ArrayList<>(
                Arrays.asList(GodValidationMethods::isTargetPosWithinBoard,
                        GodValidationMethods::isCellWorkersFree,
                        GodValidationMethods::isTargetPosOnDifferentCell,
                        GodValidationMethods::isTargetPosDomesFree,
                        GodValidationMethods::isTargetPosAdjacent,
                        GodValidationMethods::isBuildingHeightThree,
                        this::isBuildOffThePerimeter
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


    /**
     *
     * @param actionBoardPair board and action chosen
     * @return true if it is the second build and the chosen cell is not on the perimeter.
     */
    public boolean isBuildOffThePerimeter(Pair<Action, Board> actionBoardPair){
        Action action = actionBoardPair.first();
        return buildCtr != 1 || (action.getTargetPos().getY() != 4 && action.getTargetPos().getX() != 4 && action.getTargetPos().getY() != 0 && action.getTargetPos().getX() != 0);
    }



}
