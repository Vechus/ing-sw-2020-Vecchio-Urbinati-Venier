package it.polimi.ingsw.model.god;
import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.util.Pair;

/**
 * Contains all the checks necessary on the actions performed
 */
public class GodValidationMethods {


    //5 METHODS FOR ALL ACTIONS

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


    //ONE METHOD FOR MOVE

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

    //ONE METHOD FOR BUILD_BLOCK

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

    //ONE METHOD FOR BUILD_DOME

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

}
