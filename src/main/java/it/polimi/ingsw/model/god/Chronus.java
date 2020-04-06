package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.util.Vector2;

public class Chronus extends God {
    public Chronus(Board board, Player player) {
        super(board, player);
    }


    @Override
    public boolean checkWinCondition(Action action){
        int counterBuildings=0;
        int i,j;
        if (this.board.getHeight(action.getTargetPos()) - this.board.getHeight(action.getWorkerPos()) >0
                && this.board.getHeight(action.getTargetPos())==3
        ){
            return true;
        }


        for (i=0;i<5; i++ ){
            for(j=0; j<5;j++){
                Vector2 pos=new Vector2(i,j);
                if(this.board.getHeight(pos)==3 && this.board.isComplete(pos)){
                    return true;
                }
            }

        }
        if(counterBuildings==5){
            return true;
        }

        return false;
    }
}
