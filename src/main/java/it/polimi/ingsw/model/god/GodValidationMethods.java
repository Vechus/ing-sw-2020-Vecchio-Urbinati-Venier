package it.polimi.ingsw.model.god;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.util.ActionType;
import it.polimi.ingsw.util.Pair;
import it.polimi.ingsw.util.Vector2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * Contains all the checks necessary on the actions performed
 */
public class GodValidationMethods {

    static boolean isActionPermittedByEffects(Pair<Action, Board> actionBoardPair){
        Action action = actionBoardPair.first();
        Board board = actionBoardPair.second();
        return board.isActionPermittedByEffects(action);
    }


    // METHODS FOR ALL ACTIONS

    /**
     * @param actionBoardPair the action chosen by the player
     * @return true if the position where the player wants to move to is within the board
     */
    static boolean isTargetPosWithinBoard(Pair<Action, Board> actionBoardPair){
        Action action = actionBoardPair.first();
        return action.getTargetPos().getX() < 5 && action.getTargetPos().getY() < 5 && action.getTargetPos().getX() >= 0 && action.getTargetPos().getY() >= 0;
    }

    /**
     *
     * @param actionBoardPair the action chosen by the player
     * @return true if the position where the player wants to move to doesn't have any worker in it
     */
    static boolean isCellWorkersFree(Pair<Action, Board> actionBoardPair){
        Action action = actionBoardPair.first();
        Board board = actionBoardPair.second();
        return board.getWorker(action.getTargetPos()) == null;
    }

    /**
     *
     * @param actionBoardPair the action chosen by the player
     * @return true if the position where the player wants to move to is different form the one they are in
     */
    static boolean isTargetPosOnDifferentCell(Pair<Action, Board> actionBoardPair){
        Action action = actionBoardPair.first();
        return !action.getTargetPos().equals(action.getWorkerPos());
    }

    /**
     *
     * @param actionBoardPair the action chosen by the player
     * @return true if the position where the player wants to move to doesn't have any dome.
     */
    static boolean isTargetPosDomesFree(Pair<Action, Board> actionBoardPair){
        Action action = actionBoardPair.first();
        Board board = actionBoardPair.second();
        return !board.isComplete(action.getTargetPos());
    }

    /**
     *
     * @param actionBoardPair the action chosen by the player
     * @return true if the position where the player wants to move to is within range.
     */
    static boolean isTargetPosAdjacent(Pair<Action, Board> actionBoardPair){
        Action action = actionBoardPair.first();
        return Math.abs(action.getTargetPos().getX() - action.getWorkerPos().getX()) <= 1 && Math.abs(action.getTargetPos().getY() - action.getWorkerPos().getY()) <= 1;
    }

    /**
     *
     * @param actionBoardPair
     * @return
     */
    public static boolean isCellDifferentFromInitialSpace(Pair<Action, Board> actionBoardPair){
        Action action = actionBoardPair.first();
        God god = action.getWorker().getOwner().getPlayerGod();
        return god.getMoveCount() != 1 || !action.getTargetPos().equals(god.getInitPos());
    }


    // METHODS FOR MOVE

    /**
     *
     * @param actionBoardPair the action chosen by the player
     * @return true if the position where the player wants to move to is at a height difference from the current position less than one
     */
    static boolean isMoveHeightLessThanOne(Pair<Action, Board> actionBoardPair){
        Action action = actionBoardPair.first();
        Board board = actionBoardPair.second();
        return board.getHeight(action.getTargetPos()) - board.getHeight(action.getWorkerPos()) <= 1;
    }

    static boolean noUpIfPrebuilt(Pair<Action, Board> actionBoardPair){
        Action action = actionBoardPair.first();
        Board board = actionBoardPair.second();
        God god = action.getWorker().getOwner().getPlayerGod();
        return god.getBuildCount() == 0 || board.getHeight(action.getTargetPos()) - board.getHeight(action.getWorkerPos()) <= 0;
    }

    /**
     *
     * @param actionBoardPair board and action chosen
     * @return true if it's not the first time he moves and the cell he wants to go to is on the perimeter
     */
    static boolean isMoveOnPerimeter(Pair<Action, Board> actionBoardPair) {
        Action action = actionBoardPair.first();
        God god = action.getWorker().getOwner().getPlayerGod();
        return god.getMoveCount() == 0 || (action.getWorkerPos().getY() == 4 || action.getWorkerPos().getX() == 4 || action.getWorkerPos().getX() == 0 || action.getWorkerPos().getY() == 0);
    }

    /**
     * Checks if it's possible to push the worker occupying the target cell in the same direction as the move.
     * If the target cell is free, then true by default.
     * @param actionBoardPair bundled action and board
     * @return true if the target cell is empty or its worker can be pushed away in the same direction
     */
    static boolean isPushPossible(Pair<Action, Board> actionBoardPair){
        if(GodValidationMethods.isCellWorkersFree(actionBoardPair)) return true;
        Action action = actionBoardPair.first();
        Board board = actionBoardPair.second();
        Worker otherWorker = board.getWorker(action.getTargetPos());
        Vector2 delta = action.getTargetPos().sub(action.getWorkerPos());
        Vector2 movedPos = action.getTargetPos().add(delta);
        Action movedAction = new Action(otherWorker, movedPos, ActionType.MOVE);
        List<Function<Pair<Action, Board>, Boolean>> pushValidationFunctions = new ArrayList<>(
                Arrays.asList(GodValidationMethods::isActionPermittedByEffects,
                        GodValidationMethods::isTargetPosWithinBoard,
                        GodValidationMethods::isCellWorkersFree,
                        GodValidationMethods::isTargetPosOnDifferentCell,
                        GodValidationMethods::isTargetPosDomesFree,
                        GodValidationMethods::isTargetPosAdjacent
                ));
        return God.checkConditions(pushValidationFunctions, movedAction, board);
    }

    // METHODS FOR BUILD_BLOCK

    /**
     *
     * @param actionBoardPair the action chosen by the player
     * @return true if the position where the player wants to build has less than three block
     */
    static boolean isBuildingHeightLessThanThree(Pair<Action, Board> actionBoardPair){
        Action action = actionBoardPair.first();
        Board board = actionBoardPair.second();
        return board.getHeight(action.getTargetPos()) != 3;
    }

    /**
     * checks if the cells different from the one he is in, are without workers
     * @param actionBoardPair board and action chosen
     * @return true if the are no workers if the other cells
     */
    static boolean isDifferentCellFree(Pair<Action, Board> actionBoardPair){
        Action action = actionBoardPair.first();
        Board board = actionBoardPair.second();
        return board.getWorker(action.getTargetPos()) == null || action.getTargetPos().equals(action.getWorker().getPosition());
    }

    // METHODS FOR BUILD_DOME

    /**
     *
     * @param actionBoardPair the action chosen by the player
     * @return true if the position where the player wants to build has 3 blocks.
     */
    static boolean isBuildingHeightThree(Pair<Action, Board> actionBoardPair){
        Action action = actionBoardPair.first();
        Board board = actionBoardPair.second();
        return board.getHeight(action.getTargetPos()) == 3;
    }

    // COMMON METHODS FOR BUILD BLOCK AND DOME


    /**
     * Checks, given the action, if the building is on a different cell.
     * @param actionBoardPair
     * @return
     */
    static boolean isCellDifferentWhenBuilding(Pair<Action, Board> actionBoardPair){
        Action action = actionBoardPair.first();
        God god = action.getWorker().getOwner().getPlayerGod();
        return god.getBuildCount() != 1 || !action.getTargetPos().equals(god.getBuildPos());
    }

    /**
     * Checks, given the action, if the building is on the same cell.
     * @param actionBoardPair
     * @return
     */
    static boolean isCellSameWhenBuilding(Pair<Action, Board> actionBoardPair){
        Action action = actionBoardPair.first();
        God god = action.getWorker().getOwner().getPlayerGod();
        return god.getBuildCount() != 1 || action.getTargetPos().equals(god.getBuildPos());
    }

    /**
     * @param actionBoardPair board and action chosen
     * @return true if it is the second build and the chosen cell is not on the perimeter.
     */
    static boolean isBuildOffThePerimeter(Pair<Action, Board> actionBoardPair){
        Action action = actionBoardPair.first();
        God god = action.getWorker().getOwner().getPlayerGod();
        return god.getBuildCount() != 1 || (action.getTargetPos().getY() != 4 && action.getTargetPos().getX() != 4 && action.getTargetPos().getY() != 0 && action.getTargetPos().getX() != 0);
    }
}
