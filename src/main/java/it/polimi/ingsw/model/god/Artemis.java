package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.util.Vector2;

public class Artemis extends God {
    protected int counterArtemisMoves=0;


    @Override
    public boolean chooseAction (Action action){
        if(counterArtemisMoves==0){
            if (move(action)) {
                counterArtemisMoves++;
                return true;
            }
        }
        if(counterArtemisMoves==1){
            if(build(action)){
                hasFinishedTurn=true;
                return true;
            }
            if(move(action)) {
                counterArtemisMoves++;
                return true;
            }
        }
        if(counterArtemisMoves==2){
            if(build(action)) {
                hasFinishedTurn=true;
                return true;
            }
        }

        return false;
    }



}