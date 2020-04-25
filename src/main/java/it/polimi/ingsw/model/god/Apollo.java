package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.util.Vector2;

import static it.polimi.ingsw.model.Action.ActionType.MOVE;

public class Apollo extends God {

    public Apollo(Board board, Player player) {
        super(board, player);
    }

    @Override
    public boolean move(Action action) {
        int heightDiff = this.board.getHeight(action.getWorkerPos())-this.board.getHeight(action.getTargetPos());
        //action will be built probably in Model
        if(!this.isMoveValid(action)) {
            return false;
        }

        if (this.board.getWorker(action.getTargetPos()) != null){
            Worker otherWorker=this.board.getWorker(action.getTargetPos());
            Action otherAction= new Action(otherWorker,action.getWorkerPos(), MOVE);
            this.board.moveWorker(otherAction);
        }
        this.board.moveWorker(action);
        action.getWorker().setPosition(action.getTargetPos());
        return true;



    }


    //removing the constraint on the target pos to enable apollo to go on occupied cells
    @Override
    public boolean isMoveValid(Action action) {
        Vector2 currPos= action.getWorkerPos();
        Vector2 nextPos= action.getTargetPos();

        int heightDiff = this.board.getHeight(currPos)-this.board.getHeight(action.getTargetPos());



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

        return true;
    }
}
