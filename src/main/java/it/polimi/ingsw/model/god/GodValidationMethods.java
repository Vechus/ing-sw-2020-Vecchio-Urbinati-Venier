package it.polimi.ingsw.model.god;
import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.model.Board;
import org.testng.internal.collections.Pair;


public class GodValidationMethods {


    //5 METHODS FOR ALL ACTIONS
    static boolean isTargetPosWithinBoard(Pair<Action, Board> actionBoardPair){
        Action action = actionBoardPair.first();
        return action.getTargetPos().getX() < 5 && action.getTargetPos().getY() < 5 && action.getTargetPos().getX() >= 0 && action.getTargetPos().getY() >= 0;
    }

    static boolean isCellWorkersFree(Pair<Action, Board> actionBoardPair){
        Action action = actionBoardPair.first();
        Board board = actionBoardPair.second();
        return board.getWorker(action.getTargetPos()) == null;
    }

    static boolean isTargetPosOnDifferentCell(Pair<Action, Board> actionBoardPair){
        Action action = actionBoardPair.first();
        return !action.getTargetPos().equals(action.getWorkerPos());
    }

    static boolean isTargetPosDomesFree(Pair<Action, Board> actionBoardPair){
        Action action = actionBoardPair.first();
        Board board = actionBoardPair.second();
        return !board.isComplete(action.getTargetPos());
    }

    static boolean isTargetPosAdjacent(Pair<Action, Board> actionBoardPair){
        Action action = actionBoardPair.first();
        return Math.abs(action.getTargetPos().getX() - action.getWorkerPos().getX()) <= 1 && Math.abs(action.getTargetPos().getY() - action.getWorkerPos().getY()) <= 1;
    }


    //ONE METHOD FOR MOVE
    static boolean isMoveHeightLessThanOne(Pair<Action, Board> actionBoardPair){
        Action action = actionBoardPair.first();
        Board board = actionBoardPair.second();
        return board.getHeight(action.getTargetPos()) - board.getHeight(action.getWorkerPos()) <= 1;
    }

    //ONE METHOD FOR BUILD_BLOCK
    static boolean isBuildingHeightLessThanThree(Pair<Action, Board> actionBoardPair){
        Action action = actionBoardPair.first();
        Board board = actionBoardPair.second();
        return board.getHeight(action.getTargetPos()) != 3;
    }

    //ONE METHOD FOR BUILD_DOME
    static boolean isBuildingHeightThree(Pair<Action, Board> actionBoardPair){
        Action action = actionBoardPair.first();
        Board board = actionBoardPair.second();
        return board.getHeight(action.getTargetPos()) == 3;
    }

}
