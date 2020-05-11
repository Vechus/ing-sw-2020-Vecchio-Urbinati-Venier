package it.polimi.ingsw.model.god.effect;

import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.util.Vector2;

public class HeraEffect extends OpponentEffect {

    public HeraEffect(Board board, Player owner) {
        super(board, owner, true);
    }

    @Override
    public boolean checkOpponentWinCondition(Action a) {
        Vector2 finPos = a.getTargetPos();
        return finPos.getX() != 0 && finPos.getX() != 4 && finPos.getY() != 0 && finPos.getY() != 4;
    }
}
