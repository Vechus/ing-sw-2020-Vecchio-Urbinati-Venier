package it.polimi.ingsw.model.god;

import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.util.ActionType;
import it.polimi.ingsw.util.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MinotaurTest {
    Board board;
    Player player;
    Minotaur minotaur;
    Vector2 pos1 = new Vector2(0, 0), pos2 = new Vector2(0, 1), pos3 = new Vector2(0, 2);
    Worker playerWorker, other1, other2;


    @BeforeEach
    void setup(){
        board = new Board();
        player = new Player(board);
        minotaur = new Minotaur(board, player);
        player.setPlayerGod(minotaur);
        playerWorker = new Worker(player);
        other1 = new Worker(player);
        other2 = new Worker(player);
        board.placeWorker(playerWorker, pos1);
        board.placeWorker(other1, pos2);
    }

    @Test
    void movePushValid(){
        Action move = new Action(playerWorker, pos2, ActionType.MOVE);
        assertTrue(minotaur.chooseAction(move));
        assertNull(board.getWorker(pos1));
        assertEquals(board.getWorker(pos2), playerWorker);
        assertEquals(board.getWorker(pos3), other1);
    }

    @Test
    void movePushOccupied(){
        board.placeWorker(other2, pos3);
        Action move = new Action(playerWorker, pos2, ActionType.MOVE);
        assertFalse(minotaur.chooseAction(move));
        assertEquals(board.getWorker(pos1), playerWorker);
        assertEquals(board.getWorker(pos2), other1);
        assertEquals(board.getWorker(pos3), other2);
    }

    @Test
    void movePushHigh(){
        board.setHeight(pos3, 3);
        Action move = new Action(playerWorker, pos2, ActionType.MOVE);
        assertTrue(minotaur.chooseAction(move));
        assertNull(board.getWorker(pos1));
        assertEquals(board.getWorker(pos2), playerWorker);
        assertEquals(board.getWorker(pos3), other1);
    }

    @Test
    void movePushDome(){
        board.setComplete(pos3, true);
        Action move = new Action(playerWorker, pos2, ActionType.MOVE);
        assertFalse(minotaur.chooseAction(move));
        assertEquals(board.getWorker(pos1), playerWorker);
        assertEquals(board.getWorker(pos2), other1);
        assertNull(board.getWorker(pos3));
    }

    @Test
    void movePushOutOfBounds(){
        Action move = new Action(other1, pos1, ActionType.MOVE);
        assertFalse(minotaur.chooseAction(move));
        assertEquals(board.getWorker(pos1), playerWorker);
        assertEquals(board.getWorker(pos2), other1);
    }
}
