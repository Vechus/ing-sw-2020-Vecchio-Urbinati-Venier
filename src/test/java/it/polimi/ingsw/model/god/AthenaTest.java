package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.util.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AthenaTest {
    Board board;
    Player player;
    Athena athena;
    Vector2 highPos = new Vector2(0, 0);
    Vector2 lowPos = new Vector2(1, 1);
    Vector2 pInitPos = new Vector2(1, 0);
    Worker playerWorker;


    @BeforeEach
    void setup(){
        board = new Board();
        player = new Player(board);
        athena = new Athena(board, player);
        player.setPlayerGod(athena);
        playerWorker = new Worker(player);
        board.placeWorker(playerWorker, pInitPos);

        board.setHeight(highPos, 1);
    }

    @Test
    void testActivationTrue(){
        Action playerMove = new Action(playerWorker, highPos, Action.ActionType.MOVE);
        athena.move(playerMove);
        assertTrue(board.isEffectActive(player));
    }

    @Test
    void testActivationFalse(){
        Action playerMove = new Action(playerWorker, lowPos, Action.ActionType.MOVE);
        athena.move(playerMove);
        assertFalse(board.isEffectActive(player));
    }

    @Test
    void testNewTurn(){
        athena.beginNewTurn();
        assertFalse(board.isEffectActive(player));
    }

    @Test
    void testDefaultInactive(){
        assertFalse(board.isEffectActive(player));
    }
}
