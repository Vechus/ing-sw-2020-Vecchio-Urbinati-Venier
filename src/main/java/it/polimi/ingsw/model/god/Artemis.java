package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.util.Vector2;

public class Artemis extends God {
    protected int counterArtemisMoves=0;

    public Artemis(Board board, Player player) {
        super(board, player);
    }


    @Override
    public boolean chooseAction (Action action){
        if(counterArtemisMoves==0 && action.getType()==Action.ActionType.MOVE){
            if (move(action)) {
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

}