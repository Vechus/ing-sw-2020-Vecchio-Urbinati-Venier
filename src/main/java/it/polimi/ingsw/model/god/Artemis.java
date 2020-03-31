package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.util.Vector2;

public class Artemis extends God {
    protected int counterArtemisMoves=0;


    @Override
    public boolean callMoveOrBuild (Worker worker, Vector2 pos){
        if(counterArtemisMoves==0){
            if (move(worker, pos)) {
                counterArtemisMoves++;
                return true;
            }
        }
        if(counterArtemisMoves==1){
            if(build(worker, pos)){
                hasFinishedTurn=true;
                return true;
            }
            if(move(worker, pos)) {
                counterArtemisMoves++;
                return true;
            }
        }
        if(counterArtemisMoves==2){
            if(build(worker, pos)) {
                hasFinishedTurn=true;
                return true;
            }
        }

        return false;
    }



}