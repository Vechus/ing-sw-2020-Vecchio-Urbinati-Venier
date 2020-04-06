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
    public boolean isWorkersMoveValid (Action action){
        Vector2 currPos= action.getWorkerPos();
        Vector2 nextPos= action.getTargetPos();

        int heightDiff = this.board.getHeight(currPos)-this.board.getHeight(action.getTargetPos());

        if (this.board.getWorker(action.getTargetPos()) != null){
            return false;
        }

        if(heightDiff>1){
            return false;
        }

        if(this.board.isComplete(nextPos)){
            return false;
        }

        if (nextPos.getX()>=5 || nextPos.getY()>=5 || nextPos.getX()<0 || nextPos.getY()<0){
            return false;
        }

        if(nextPos.equals(currPos) ){
            return false;
        }

        if(Math.abs(nextPos.getX()-currPos.getX()) >1  || Math.abs(nextPos.getY()-currPos.getY()) >1){
            return false;
        }

        //extra condition
        if(hasBuilt==true && this.board.getHeight(action.getTargetPos()) - this.board.getHeight(action.getWorkerPos())>0){
            return false;
        }

        return true;
    }


}
