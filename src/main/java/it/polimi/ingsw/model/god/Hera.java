package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.god.effect.HeraEffect;

public class Hera extends God {

    public Hera(Player player, Board board){
        this.player = player;
        this.board = board;

        this.board.addEffect(new HeraEffect(this.player, true));
    }
}
