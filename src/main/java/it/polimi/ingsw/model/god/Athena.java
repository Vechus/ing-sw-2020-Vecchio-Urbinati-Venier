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
        Vector2 currPos= action.getWorker().getPosition();
        boolean res = super.move(action);
        int heightDiff = this.board.getHeight(action.getTargetPos())-this.board.getHeight(currPos);
        System.out.println("Athena moves: "+res+" height diff: "+heightDiff);
        if(res && heightDiff > 0)
            this.board.setEffectActive(this.player, true);
        return res;
    }
}
