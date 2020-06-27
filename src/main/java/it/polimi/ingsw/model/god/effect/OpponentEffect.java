package it.polimi.ingsw.model.god.effect;

import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;

public class OpponentEffect {
    protected Player owner;
    protected boolean active;
    protected Board board;

    public OpponentEffect(Board board, Player owner, boolean initActive){
        this.board = board;
        this.owner = owner;
        this.active = initActive;
    }

    public void setActive(boolean active){
        this.active = active;
    }

    public boolean isActive(){ return this.active; }

    public boolean checkOpponentAction(Action a){
        return true;
    }

    public boolean checkOpponentWinCondition(Action a){
        return true;
    }

    public boolean checkOwner(Player p) {
        return p.equals(this.owner);
    }
}
