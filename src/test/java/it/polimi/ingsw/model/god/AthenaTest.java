package it.polimi.ingsw.model.god;

import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.util.ActionType;
import it.polimi.ingsw.util.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AthenaTest {
    Board board;
    Player player, opponent;
    Athena athena;
    God oppGod;
    Vector2 highPos = new Vector2(0, 0);
    Vector2 lowPos = new Vector2(1, 1);
    Vector2 pInitPos = new Vector2(1, 0);
    Worker playerWorker, other;


    @BeforeEach
    void setup(){
        board = new Board();
        player = new Player(board);
        opponent = new Player(board);
        athena = new Athena(board, player);
        oppGod = new God(board, opponent);
        player.setPlayerGod(athena);
        opponent.setPlayerGod(oppGod);
        playerWorker = new Worker(player);
        other = new Worker(opponent);

        board.placeWorker(playerWorker, pInitPos);
        board.placeWorker(other, lowPos);

        board.setHeight(pInitPos, 1);
        board.setHeight(highPos, 2);
    }

    @Test
    void testActivationTrue(){
        Action playerMove = new Action(playerWorker, highPos, ActionType.MOVE);
        assertTrue(athena.move(playerMove));
        assertTrue(board.isEffectActive(player));
        Action oppMove = new Action(other, pInitPos, ActionType.MOVE);
        assertFalse(oppGod.move(oppMove));
        oppMove = new Action(other, new Vector2(0, 1), ActionType.MOVE);
        assertTrue(oppGod.move(oppMove));
    }

    @Test
    void testActivationFalse(){
        Action playerMove = new Action(playerWorker, new Vector2(2, 0), ActionType.MOVE);
        athena.move(playerMove);
        assertFalse(board.isEffectActive(player));
        Action oppMove = new Action(other, pInitPos, ActionType.MOVE);
        assertTrue(oppGod.move(oppMove));
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
