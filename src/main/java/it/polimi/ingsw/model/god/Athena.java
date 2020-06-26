package it.polimi.ingsw.model.god;

import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.god.effect.AthenaEffect;
import it.polimi.ingsw.util.Vector2;

/**
 * Simple god: Opponent’s Turn: If one of your
 * Workers moved up on your last
 * turn, opponent Workers cannot
 * move up this turn.
 */
public class Athena extends God {

    public Athena(Board board, Player player){
        super(board, player);
        name = "Athena";

        this.board.addEffect(new AthenaEffect(this.board, this.player));
    }

    @Override
    public void beginNewTurn() {
        super.beginNewTurn();
        this.board.setEffectActive(this.player, false);
    }

    @Override
    public boolean move(Action action){
        Vector2 currPos= action.getWorker().getPosition();
        boolean res = super.move(action);
        int heightDiff = this.board.getHeight(action.getTargetPos())-this.board.getHeight(currPos);
        if(res && heightDiff > 0)
            this.board.setEffectActive(this.player, true);
        return res;
    }
}
