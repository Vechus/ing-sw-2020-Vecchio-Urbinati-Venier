package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.util.Vector2;

public class Artemis extends God {
    private int counterArtemisMoves=0;
    private Vector2 initialPos;
    public Artemis(Board board, Player player) {
        super(board, player);
    }


    @Override
    public boolean chooseAction (Action action){
        if(counterArtemisMoves==0 && action.getType()==Action.ActionType.MOVE){
            initialPos=action.getWorkerPos();
            if (move(action)) {

                chosenWorker=action.getWorker();
                counterArtemisMoves++;
                return true;
            }
        }
        if(counterArtemisMoves>=1){
            if(action.getType()==Action.ActionType.MOVE && counterArtemisMoves == 1){
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
    public boolean isMoveValid (Action action){
        Vector2 nextPos= action.getTargetPos();

        boolean res = super.isMoveValid(action);

        //check added for Artemis power
        if(counterArtemisMoves==1 && nextPos.equals(initialPos)){
            return false;
        }
        if(counterArtemisMoves==1 && !(chosenWorker.equals(action.getWorker()))){
            return false;
        }

        return true;
    }

}