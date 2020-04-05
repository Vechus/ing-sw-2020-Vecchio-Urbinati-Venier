package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.util.Vector2;

public class Artemis extends God {
    private int counterArtemisMoves=0;
    private Vector2 initialPos;
    private  Worker firstWorker;
    public Artemis(Board board, Player player) {
        super(board, player);
    }


    @Override
    public boolean chooseAction (Action action){
        if(counterArtemisMoves==0 && action.getType()==Action.ActionType.MOVE){
            initialPos=action.getWorkerPos();
            if (move(action)) {
                firstWorker= action.getWorker();
                counterArtemisMoves++;
                return true;
            }
        }
        if(counterArtemisMoves==1){
            if(action.getType()==Action.ActionType.MOVE){
                if(move(action)) {
                    counterArtemisMoves++;
                    return true;
                }
            }
            if(action.getType()==Action.ActionType.BUILD){
                if(build(action)) {
                    hasFinishedTurn = true;
                    return true;
                }
            }
            if(action.getType()==Action.ActionType.BUILD_DOME){
                if(buildDome(action)){
                    hasFinishedTurn=true;
                    return true;
                }
            }
        }
        if(counterArtemisMoves==2){
            if(action.getType()==Action.ActionType.BUILD){
                if(build(action)) {
                    hasFinishedTurn=true;
                    return true;
                }
            }
            if(action.getType()==Action.ActionType.BUILD_DOME) {
                if (buildDome(action)) {
                    hasFinishedTurn = true;
                    return true;
                }
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

        //check added for Artemis power
        if(counterArtemisMoves==1 && nextPos.equals(initialPos)){
            return false;
        }
        if(counterArtemisMoves==1&& !(firstWorker.equals(action.getWorker()))){
            return false;
        }

        return true;
    }

}