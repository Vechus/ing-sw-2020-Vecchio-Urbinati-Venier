package it.polimi.ingsw.model.god.effect;

import it.polimi.ingsw.model.Move;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;

public class AthenaEffect extends OpponentEffect {

    public AthenaEffect(Player owner){
        super(owner);
    }

    @Override
    public boolean checkOpponentMove(Worker w, Move m) {
        if(w.owner == this.owner || !this.active) return true;
        return m.heightDiff <= 0;
    }
}
