package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.god.effect.AthenaEffect;
import it.polimi.ingsw.util.Vector2;

public class Athena extends God {

    public Athena(Player player, Board board){
        super(board, player);

        this.board.addEffect(new AthenaEffect(this.board, this.player));
    }

    @Override
    public void beginNewTurn() {
        super.beginNewTurn();
        this.board.setEffectActive(this.player, false);
    }

    @Override
    public boolean move(Action action){
        boolean res = super.move(action);
        Vector2 currPos= action.getWorker().getPosition();
        int heightDiff = this.board.getHeight(currPos)-this.board.getHeight(action.getTargetPos());
        if(res && heightDiff > 0)
            this.board.setEffectActive(this.player, true);
        return res;
    }
}
