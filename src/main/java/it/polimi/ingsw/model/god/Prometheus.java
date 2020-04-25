package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.util.Vector2;

public class Prometheus extends God {
    /*If your Worker does
    not move up, it may build both
    before and after moving*/

    //è giusto settare a flase??
    boolean hasBuilt=false;

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
        } else if(hasBuilt==false && action.getType()==Action.ActionType.BUILD){
            if (build(action)){
                hasBuilt=true;
            }
        } else if(hasBuilt==false && action.getType()==Action.ActionType.BUILD_DOME){
            if (build(action)){
                hasBuilt=true;
            }
        }

        return false;
    }


    @Override
    public boolean isMoveValid (Action action){


        //extra condition
        if(hasBuilt==true && this.board.getHeight(action.getTargetPos()) - this.board.getHeight(action.getWorkerPos())>0){
            return false;
        }

        return true;
    }


}
