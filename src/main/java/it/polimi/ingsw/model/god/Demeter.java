package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.util.Vector2;

public class Demeter extends God {
    private int counterDemeterBuild=0;
    private Vector2 posFirstBuild;

    public Demeter(Board board, Player player) {
        super(board, player);
    }

    @Override
    public  boolean chooseAction (Action action){
        if(this.hasMoved ){
            if(counterDemeterBuild==0){
                if(action.getType()==Action.ActionType.BUILD){
                    if(build(action)) {

                        posFirstBuild=action.getTargetPos();
                        counterDemeterBuild++;
                        return true;
                    }
                }else if(action.getType()==Action.ActionType.BUILD_DOME){
                    if (buildDome(action)){
                        counterDemeterBuild++;
                        return true;
                    }
                }
            }
            if(counterDemeterBuild==1) {
                if (action.getType() == Action.ActionType.BUILD) {
                    if (build(action)) {
                        hasFinishedTurn = true;
                        return true;
                    }
                }else if (action.getType() == Action.ActionType.BUILD_DOME) {
                    if (buildDome(action)){
                        hasFinishedTurn = true;
                        return true;
                    }
                }
            }
        }else if (action.getType()==Action.ActionType.MOVE){
            if(move(action)) {
                chosenWorker=action.getWorker();
                this.hasMoved=true;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isBuildValid(Action action){
        Vector2 pos=action.getTargetPos();

        //check if pos is within board
        if(pos.getY()>=5 || pos.getX()>=5 || pos.getY()<0 || pos.getX()<0){
            return false;
        }
        //check if targeted pos doesn't have any other worker
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

        //check for Demeter power
        if(counterDemeterBuild==1 && action.getTargetPos().equals(posFirstBuild)){
            return false;
        }

        //check the worker is the same that moved
        if(!(chosenWorker.equals(action.getWorker()))){return false;}

        return true;
    }

    @Override
    public boolean isBuildDomeValid(Action action){

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
        //check for Demeter power
        if(counterDemeterBuild==1 && action.getTargetPos().equals(posFirstBuild)){
            return false;
        }
        //check the worker is the same that moved
        if(!(chosenWorker.equals(action.getWorker()))){return false;}

        return true;
    }
}
