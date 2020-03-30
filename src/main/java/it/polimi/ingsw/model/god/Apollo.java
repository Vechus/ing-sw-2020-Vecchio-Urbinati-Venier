package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Move;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.util.Vector2;

public class Apollo extends God {

    @Override
    public boolean move(Worker worker, Vector2 finPos) {
        Vector2 currPos= worker.getPosition();
        int heightDiff = this.board.getHeight(currPos)-this.board.getHeight(finPos);
        Move move= new Move(currPos, finPos, heightDiff);
        if(!this.isWorkersMoveValid(worker, move)) {
            return false;
        }

        if (this.board.getWorker(move.getFinPos()) != null){
            Worker otherWorker=this.board.getWorker(finPos);
            Move otherMove= new Move(finPos, currPos, -heightDiff);
            this.board.moveWorker(otherWorker, otherMove);
        }
        this.board.moveWorker(worker, move);
        worker.setPosition(finPos);
        return true;



    }

    @Override
    public boolean isWorkersMoveValid(Worker worker, Move move) {
        Vector2 currPos= move.getInitPos();
        Vector2 nextPos= move.getFinPos();

        int heightDiff = move.getHeightDiff();



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
