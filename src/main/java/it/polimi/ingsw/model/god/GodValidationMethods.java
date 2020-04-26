package it.polimi.ingsw.model.god;
import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Board;
import org.testng.internal.collections.Pair;


public class GodValidationMethods {


    //5 METHODS FOR ALL ACTIONS
    static boolean isTargetPosWithinBoard(Pair<Action, Board> actionBoardPair){
        Action action = actionBoardPair.first();
        if (action.getTargetPos().getX()>=5 || action.getTargetPos().getY()>=5 || action.getTargetPos().getX()<0 || action.getTargetPos().getY()<0) {
            return false;
        }
        return true;
    }

    static boolean isCellWorkersFree(Pair<Action, Board> actionBoardPair){
        Action action = actionBoardPair.first();
        Board board = actionBoardPair.second();
        if (board.getWorker(action.getTargetPos()) != null){
            return false;
        }
        return true;
    }

    static boolean isTargetPosOnDifferentCell(Pair<Action, Board> actionBoardPair){
        Action action = actionBoardPair.first();
        if(action.getTargetPos().equals(action.getWorkerPos()) ){
            return false;
        }
        return true;
    }

    static boolean isTargetPosDomesFree(Pair<Action, Board> actionBoardPair){
        Action action = actionBoardPair.first();
        Board board = actionBoardPair.second();
        if(board.isComplete(action.getTargetPos())){
            return false;
        }
        return true;
    }

    static boolean isTargetPosAdjacent(Pair<Action, Board> actionBoardPair){
        Action action = actionBoardPair.first();
        if(!(Math.abs(action.getTargetPos().getX() - action.getWorkerPos().getX()) <= 1 && Math.abs(action.getTargetPos().getY() - action.getWorkerPos().getY())<= 1 )){
            return false;
        }
        return true;
    }


    //ONE METHOD FOR MOVE
    static boolean isMoveHeightLessThanOne(Pair<Action, Board> actionBoardPair){
        Action action = actionBoardPair.first();
        Board board = actionBoardPair.second();
        if(board.getHeight(action.getTargetPos())-board.getHeight(action.getWorkerPos())>1){
            return false;
        }
        return true;
    }

    //ONE METHOD FOR BUILD_BLOCK
    static boolean isBuildingHeightLessThanThree(Pair<Action, Board> actionBoardPair){
        Action action = actionBoardPair.first();
        Board board = actionBoardPair.second();
        if (board.getHeight(action.getTargetPos())>=3){
            return false;
        }
        return true;
    }

    //ONE METHOD FOR BUILD_DOME
    static boolean isBuildingHeightThree(Pair<Action, Board> actionBoardPair){
        Action action = actionBoardPair.first();
        Board board = actionBoardPair.second();
        if (board.getHeight(action.getTargetPos())==3){
            return false;
        }
        return true;
    }

}
