package it.polimi.ingsw.model.god.effect;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.util.Vector2;

public class HeraEffect extends OpponentEffect {

    public HeraEffect(Player owner) {
        super(owner, true);
    }

    /* TODO: fix this
    @Override
    public boolean checkOpponentWinCondition(Worker w, Move m) {
        if(w.getOwner() == this.owner || !this.active) return true;
        Vector2 finPos = m.getFinPos();
        return finPos.getX() != 0 && finPos.getX() != 4 && finPos.getY() != 0 && finPos.getY() != 4;
    }

     */
}
