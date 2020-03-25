package it.polimi.ingsw.model.god.effect;

import it.polimi.ingsw.model.Move;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.util.Vector2;

public class HeraEffect extends OpponentEffect {

    public HeraEffect(Player owner) {
        super(owner);
    }

    @Override
    public boolean checkOpponentWinCondition(Worker w, Move m) {
        if(w.owner == this.owner || !this.active) return true;
        Vector2 finPos = m.finPos;
        return finPos.x != 0 && finPos.x != 4 && finPos.y != 0 && finPos.y != 4;
    }
}
