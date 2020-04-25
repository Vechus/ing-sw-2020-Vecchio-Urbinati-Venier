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


        //check for Demeter power
        if(counterDemeterBuild==1 && action.getTargetPos().equals(posFirstBuild)){
            return false;
        }

        return true;
    }

    @Override
    public boolean isBuildDomeValid(Action action){


        //check for Demeter power
        if(counterDemeterBuild==1 && action.getTargetPos().equals(posFirstBuild)){
            return false;
        }
        //check the worker is the same that moved
        if(!(chosenWorker.equals(action.getWorker()))){return false;}

        return true;
    }
}
