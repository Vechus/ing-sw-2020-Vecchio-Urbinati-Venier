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
    Player player, opponent;
    Athena athena;
    Vector2 highPos = new Vector2(0, 0);
    Vector2 lowPos = new Vector2(1, 1);
    Vector2 oppInitPos = new Vector2(0, 1);
    Vector2 pInitPos = new Vector2(1, 0);
    Worker playerWorker, oppWorker;


    @BeforeEach
    void setup(){
        board = new Board();
        player = new Player(board);
        athena = new Athena(player, board);
        player.setPlayerGod(athena);
        playerWorker = new Worker(pInitPos, player);
        board.setWorker(pInitPos, playerWorker);
        opponent = new Player(board);
        oppWorker = new Worker(oppInitPos, opponent);
        board.setWorker(oppInitPos, oppWorker);

        board.setHeight(highPos, 1);
    }

    @Test
    void testEffectActiveValid(){
        board.setEffectActive(player, true);
        Action moveValid = new Action(oppWorker, lowPos, Action.ActionType.MOVE);
        assertTrue(board.isActionPermittedByEffects(moveValid));
    }

    @Test
    void testEffectActiveInvalid(){
        board.setEffectActive(player, true);
        Action moveInvalid = new Action(oppWorker, highPos, Action.ActionType.MOVE);
        assertFalse(board.isActionPermittedByEffects(moveInvalid));
    }

    @Test
    void testEffectInactive(){
        Action moveInvalid = new Action(oppWorker, highPos, Action.ActionType.MOVE);
        assertTrue(board.isActionPermittedByEffects(moveInvalid));
    }

    @Test
    void testDefaultInactive(){
        assertFalse(board.isEffectActive(player));
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
}
