package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;

public class Pan extends God {

    public Pan(Board board, Player player) {
        super(board, player);
    }

    @Override
    boolean checkWinCondition(Action action){
        if (this.board.getHeight(action.getTargetPos()) - this.board.getHeight(action.getWorkerPos()) >0
                && this.board.getHeight(action.getTargetPos())==3
        ){
            return true;
        }

        if (this.board.getHeight(action.getWorkerPos())-  this.board.getHeight(action.getTargetPos())>=2){
            return true;
        }
        return false;
    }
}
