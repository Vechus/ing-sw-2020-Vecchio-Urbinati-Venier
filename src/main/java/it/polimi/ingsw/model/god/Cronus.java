package it.polimi.ingsw.model.god;

import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.util.Vector2;

public class Cronus extends God {
    public Cronus(Board board, Player player) {
        super(board, player);
        name = "Cronus";
    }

    @Override
    public boolean checkWinCondition(Action action){
        if(!super.checkWinCondition(action)){
            return false;
        }
        int counterBuildings=0;
        for (int i=0;i<5; i++ ){
            for(int j=0; j<5;j++){
                Vector2 pos=new Vector2(i,j);
                if(this.board.getHeight(pos)==3 && this.board.isComplete(pos))
                    counterBuildings++;
            }
        }
        return counterBuildings == 5;
    }
}
