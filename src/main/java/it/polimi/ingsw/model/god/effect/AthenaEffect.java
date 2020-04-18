package it.polimi.ingsw.model.god.effect;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.util.Vector2;

public class AthenaEffect extends OpponentEffect {

    public AthenaEffect(Board board, Player owner){
        super(board, owner, false);
    }

    @Override
    public boolean checkOpponentAction(Action a) {
        Vector2 currPos= a.getWorker().getPosition();
        int heightDiff = this.board.getHeight(currPos)-this.board.getHeight(a.getTargetPos());
        return heightDiff <= 0;
    }
}
