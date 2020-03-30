package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Move;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.util.Vector2;

public class God {
    protected Board board;
    protected  Player player;
    protected boolean hasMoved;

    public boolean hasPlayerWon(Move move){
        if (this.board.getHeight (move.getFinPos()) == 3){
            return true;
        }else{
            return false;
        }
    }


    public  boolean callMoveOrBuild (Worker worker, Vector2 pos){
        if(this.hasMoved){
            if(!build(worker, pos)) return false;
        }else{
            if(!move(worker, pos)) return false;
        }
        return true;
    }


    public boolean move(Worker worker, Vector2 finPos){

        Vector2 currPos= worker.getPosition();
        int heightDiff = this.board.getHeight(currPos)-this.board.getHeight(finPos);
        Move move= new Move(currPos, finPos, heightDiff);

        if(!this.isWorkersMoveValid(worker, move)) {
            return false;
        }

        this.board.moveWorker(worker, move);
        worker.setPosition(finPos);
        return true;
    }

    public boolean isWorkersMoveValid (Worker worker, Move move){
        Vector2 currPos= move.getInitPos();
        Vector2 nextPos= move.getFinPos();

        int heightDiff = move.getHeightDiff();

        if (this.board.getWorker(move.getFinPos()) != null){
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

    public boolean build (Worker worker, Vector2 pos){
        if(isBuildValid(worker, pos)) {
            this.board.setHeight(pos, board.getHeight(pos) + 1);
            return true;
        }else{
            return false;
        }

    }

    public boolean isBuildValid(Worker worker, Vector2 pos){
        if(pos.getY()>=5 || pos.getX()>=5 || pos.getY()<0 || pos.getX()<0){
            return false;
        }

       if (this.board.getWorker(pos) != null){
           return false;
       }

       if(this.board.getHeight(pos)==3){
           return false;
       }

       if(this.board.isComplete(pos)){
           return false;
       }

       if(worker.getPosition().getX() - pos.getX()>1 || worker.getPosition().getY()-pos.getY()>1){
           return false;
       }

       return true;
    }


}
