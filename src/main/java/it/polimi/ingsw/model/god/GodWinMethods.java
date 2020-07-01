package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.util.ActionType;
import it.polimi.ingsw.util.Pair;
import it.polimi.ingsw.util.Vector2;

public class GodWinMethods {
    static boolean defaultWinCondition(Pair<Action, Board> actionBoardPair){
        Action action = actionBoardPair.first();
        Board board = actionBoardPair.second();
        return board.isWinPermittedByEffects(action)
                && action.getType() == ActionType.MOVE
                && board.getHeight(action.getTargetPos()) - board.getHeight(action.getWorkerPos()) > 0
                && board.getHeight(action.getTargetPos()) == 3;
    }

    /**
     * Checks, given the action, if the winning condition is possible in this turn.
     * @param actionBoardPair
     * @return true if the player can win
     */
    static boolean isWinPermittedByEffects(Pair<Action, Board> actionBoardPair){
        Action action = actionBoardPair.first();
        Board board = actionBoardPair.second();
        return board.isWinPermittedByEffects(action);
    }

    /**
     * Special win condition that tells if Cronus can win
     * @param actionBoardPair
     * @return return true if the there are 5 buildings completed
     */
    static boolean cronusWinCondition(Pair<Action, Board> actionBoardPair){
        if(defaultWinCondition(actionBoardPair))
            return true;
        Board board = actionBoardPair.second();
        int counterBuildings=0;
        for (int i=0;i<5; i++ ){
            for(int j=0; j<5;j++){
                Vector2 pos=new Vector2(i,j);
                if(board.getHeight(pos)==3 && board.isComplete(pos))
                    counterBuildings++;
            }
        }
        return counterBuildings >= 5;
    }

    /**
     * Special win condition that tells if Pan can win
     * @param actionBoardPair
     * @return true if pan gets down of two or more levels
     */
    static boolean panWinCondition(Pair<Action, Board> actionBoardPair){
        if(defaultWinCondition(actionBoardPair))
            return true;
        Action action = actionBoardPair.first();
        Board board = actionBoardPair.second();
        return action.getType() == ActionType.MOVE
                && board.getHeight(action.getWorkerPos()) - board.getHeight(action.getTargetPos()) >= 2;
    }
}
