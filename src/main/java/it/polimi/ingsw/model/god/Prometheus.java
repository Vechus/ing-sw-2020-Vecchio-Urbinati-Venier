package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;

public class Prometheus extends God {


    public Prometheus(Board board, Player player) {
        super(board, player);
    }

    @Override
    public  boolean chooseAction (Action action){
        if(this.hasMoved ){
            if(action.getType()==Action.ActionType.BUILD){
                if(build(action)) {
                    hasFinishedTurn=true;
                    return true;
                }
            }else if(action.getType()==Action.ActionType.BUILD_DOME){
                if(buildDome(action)) {
                    hasFinishedTurn = true;
                    return true;
                }
            }
        }else if (action.getType()==Action.ActionType.MOVE) {
            if (move(action)) {
                this.hasMoved = true;
                return true;
            }
        }
        return false;
    }


}
