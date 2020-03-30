package it.polimi.ingsw.model.god.effect;

import it.polimi.ingsw.model.Move;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;

public class AthenaEffect extends OpponentEffect {

    public AthenaEffect(Player owner){
        super(owner, false);
    }

    @Override
    public boolean checkOpponentMove(Worker w, Move m) {
        if(w.getOwner() == this.owner || !this.active) return true;
        return m.getHeightDiff() <= 0;
    }
}
