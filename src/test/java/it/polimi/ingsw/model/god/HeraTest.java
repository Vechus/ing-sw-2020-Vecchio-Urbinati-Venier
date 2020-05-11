package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HeraTest {
    Board board;
    Player player;
    Hera hera;

    @BeforeEach
    void setup(){
        board = new Board();
        player = new Player(board);
        hera = new Hera(board, player);
        player.setPlayerGod(hera);
    }

    @Test
    void testDefaultInactive(){
        assertTrue(board.isEffectActive(player));
    }
}
