package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.god.effect.HeraEffect;

public class Hera extends God {

    public Hera(Board board, Player player){
        super(board, player);

        this.board.addEffect(new HeraEffect(this.board, this.player));
    }
}
