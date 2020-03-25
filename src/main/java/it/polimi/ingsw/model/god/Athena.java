package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.god.effect.AthenaEffect;

public class Athena extends God {

    public Athena(Player player, Board board){
        this.player = player;
        this.board = board;

        this.board.addEffect(new AthenaEffect(this.player, false));
    }

    @Override
    public void move(){
        // TODO: wait for implementation of superclass' move
    }
}
