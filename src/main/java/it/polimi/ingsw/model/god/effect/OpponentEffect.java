package it.polimi.ingsw.model.god.effect;

import it.polimi.ingsw.model.Move;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;

public class OpponentEffect {
    protected Player owner;
    protected boolean active;

    public OpponentEffect(Player owner, boolean initActive){
        this.owner = owner;
        this.active = initActive;
    }

    public void setActive(boolean active){
        this.active = active;
    }

    public boolean checkOpponentMove(Worker w, Move m){
        return true;
    }

    public boolean checkOpponentWinCondition(Worker w, Move m){
        return true;
    }

    public boolean checkOwner(Player p) {
        return p == this.owner;
    }
}
