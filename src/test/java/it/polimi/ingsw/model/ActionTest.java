package it.polimi.ingsw.model;

import it.polimi.ingsw.model.god.God;
import it.polimi.ingsw.util.Vector2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActionTest {

    @Test
    void getTargetWorker() {
        final Worker worker = new Worker(new Player(new God(), new Board()));
        final Action action = new Action(worker, new Vector2(0,4), Action.ActionType.MOVE);
        assertTrue(worker.equals(action.getTargetWorker()));
    }

    @Test
    void getWorkerPos() {
        final Worker worker = new Worker(new Player(new God(), new Board()));
        final Action action = new Action(worker, new Vector2(0,4), Action.ActionType.MOVE);
        assertTrue(worker.getPosition().equals(action.getWorkerPos()));
    }

    @Test
    void getTargetPos() {
        final Worker worker = new Worker(new Player(new God(), new Board()));
        final Action action = new Action(worker, new Vector2(0,4), Action.ActionType.MOVE);
        assertEquals(new Vector2(0, 4), action.getTargetPos());
    }

    @Test
    void getType() {
        final Worker worker = new Worker(new Player(new God(), new Board()));
        final Action action = new Action(worker, new Vector2(0,3), Action.ActionType.MOVE);
        assertSame(Action.ActionType.MOVE, action.getType());
        assertNotSame(Action.ActionType.BUILD, action.getType());
    }
}