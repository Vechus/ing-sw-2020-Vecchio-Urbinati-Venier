package it.polimi.ingsw.model.god;

import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.util.ActionType;

/**
 * Simple god: Win Condition: You also win if
 * your Worker moves down two or
 * more levels.
 */
public class Pan extends God {

    public Pan(Board board, Player player) {
        super(board, player);
        name = "Pan";
    }

    @Override
    public boolean checkWinCondition(Action action){
        if(super.checkWinCondition(action)){
            return true;
        }
        return action.getType() == ActionType.MOVE && this.board.getHeight(action.getWorkerPos()) - this.board.getHeight(action.getTargetPos()) >= 2;
    }
}
