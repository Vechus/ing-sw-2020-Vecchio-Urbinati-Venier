package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.util.Vector2;

public class God {
    protected Board board;
    protected  Player player;
    protected boolean hasMoved= false;
    protected boolean hasFinishedTurn=false;


    public God(Board board, Player player){
        this.board=board;
        this.player=player;
    }

    public boolean getHasFinishedTurn(){return hasFinishedTurn;}


    public boolean hasPlayerWon(Action action){
        if (this.board.getHeight (action.getWorkerPos()) == 3){
            return true;
        }else{
            return false;
        }
    }


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


    public boolean move(Action action) {

        Vector2 currPos= action.getWorker().getPosition();
        int heightDiff = this.board.getHeight(currPos)-this.board.getHeight(action.getTargetPos());
        //action will be built probably in model

        if(!this.isWorkersMoveValid(action)) {
            return false;
        }

        this.board.moveWorker(action);
        action.getWorker().setPosition(action.getTargetPos());


        return true;
    }

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

        return true;
    }

    public boolean build (Action action){

        if(isBuildValid(action)) {
            this.board.setHeight(action.getTargetPos(), board.getHeight(action.getTargetPos()) + 1);
            return true;
        }else{
            return false;
        }

    }

    public boolean isBuildValid(Action action){
        Vector2 pos=action.getTargetPos();

        //check if pos is within board
        if(pos.getY()>=5 || pos.getX()>=5 || pos.getY()<0 || pos.getX()<0){
            return false;
        }
        //check if targeted pos doesn't have any other worker. It also check that you don't build where your worker is.
       if (this.board.getWorker(pos) != null){
           return false;
       }

       if(this.board.isComplete(pos)){
           return false;
       }
       if (this.board.getHeight(pos)>=3){
           return false;
       }

       //check worker is building within their range
       if(action.getWorker().getPosition().getX() - pos.getX()>1 || action.getWorker().getPosition().getY()-pos.getY()>1){
           return false;
       }

       return true;
    }

    public boolean buildDome(Action action){
        if (!isBuildDomeValide(action)){
            return false;
        }else{
            this.board.setComplete(action.getTargetPos(), true);
            return true;
        }
    }

    public boolean isBuildDomeValide(Action action){

        //check if pos is within board
        if(action.getTargetPos().getY()>=5 || action.getTargetPos().getX()>=5 || action.getTargetPos().getY()<0 || action.getTargetPos().getX()<0){
            return false;
        }
        //check if targeted pos doesn't have any other worker
        if (this.board.getWorker(action.getTargetPos()) != null){
            return false;
        }


        if(this.board.isComplete(action.getTargetPos())){
            return false;
        }

        //check worker is building within their range
        if(action.getWorker().getPosition().getX() - action.getTargetPos().getX()>1 || action.getWorker().getPosition().getY()-action.getTargetPos().getY()>1){
            return false;
        }

        if(this.board.getHeight(action.getTargetPos())<3){
            return false;
        }
        return true;
    }


    void beginNewTurn(){

    }

}
