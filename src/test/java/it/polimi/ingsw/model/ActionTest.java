package it.polimi.ingsw.model;

import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.util.ActionType;
import it.polimi.ingsw.util.Vector2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActionTest {

    @Test
    void getTargetWorker() {
        final Board board = new Board();
        final Player p = new Player(board);
        final Worker worker = new Worker(p);
        final Action action = new Action(worker, new Vector2(0,4), ActionType.MOVE);
        assertEquals(worker, action.getWorker());
    }

    @Test
    void getWorkerPos() {
        final Board board = new Board();
        final Player p = new Player(board);
        final Worker worker = new Worker(p);
        final Action action = new Action(worker, new Vector2(0,4), ActionType.MOVE);
        assertEquals(worker.getPosition(), action.getWorkerPos());
    }

    @Test
    void getTargetPos() {
        final Board board = new Board();
        final Player p = new Player(board);
        final Worker worker = new Worker(p);
        final Action action = new Action(worker, new Vector2(0,4), ActionType.MOVE);
        assertEquals(new Vector2(0, 4), action.getTargetPos());
    }

    @Test
    void getType() {
        final Board board = new Board();
        final Player p = new Player(board);
        final Worker worker = new Worker(p);
        final Action action = new Action(worker, new Vector2(0,3), ActionType.MOVE);
        assertSame(ActionType.MOVE, action.getType());
        assertNotSame(ActionType.BUILD, action.getType());
    }
}