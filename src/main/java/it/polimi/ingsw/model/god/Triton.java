package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.util.Vector2;

public class Triton extends God {
    public Triton(Board board, Player player) {
        super(board, player);
    }

    boolean isTritonInPerimeter;

    @Override
    public  boolean chooseAction (Action action){
        if(this.hasMoved ){
            if(action.getType()==Action.ActionType.MOVE && isTritonInPerimeter==true){
                if(move(action)){
                    return true;
                }
            }
            if(action.getType()==Action.ActionType.BUILD){
                if(buildBlock(action)) {
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
                chosenWorker=action.getWorker();
                this.hasMoved = true;
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean move(Action action) {


        //special setting for triton
        if(action.getWorker().getPosition().getY()==4 || action.getWorker().getPosition().getX()==4){
            isTritonInPerimeter=true;
        }
        return true;
    }
}
