package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.util.Vector2;

public class Hestia extends God {

    int counterHestiaBuilds=0;

    public Hestia(Board board, Player player) {
        super(board, player);
    }


    public  boolean chooseAction (Action action){
        if(this.hasMoved ){
           if(counterHestiaBuilds==0){
               if(action.getType()==Action.ActionType.BUILD){
                   if(build(action)) {
                       counterHestiaBuilds++;
                       return true;
                   }
               }else if(action.getType()==Action.ActionType.BUILD_DOME){
                   if(buildDome(action)) {
                       counterHestiaBuilds++;
                       return true;
                   }
               }


           } else if(counterHestiaBuilds==1){
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



           }



        }else if (action.getType()==Action.ActionType.MOVE) {
            if (move(action)) {
                chosenWorker=action.getWorker();
                this.hasMoved = true;
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean isBuildValid(Action action){
        Vector2 pos=action.getTargetPos();


        if(counterHestiaBuilds==1 && (pos.getY()==4 || pos.getX()==4 || pos.getY()==0 || pos.getX()==0)){
            return false;
        }
        return true;
    }

    @Override
    public boolean isBuildDomeValid(Action action){

        if(counterHestiaBuilds==1 && (action.getTargetPos().getY()==4 || action.getTargetPos().getX()==4 || action.getTargetPos().getY()==0 || action.getTargetPos().getX()==0)){
            return false;
        }

        return true;
    }



}
