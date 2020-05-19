package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.stage.MatchController;
import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.god.God;
import it.polimi.ingsw.util.ActionType;
import it.polimi.ingsw.util.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MatchControllerTest {
    Model model;
    int pid1, pid2;
    MatchController controller;
    Vector2 initPos = new Vector2(0, 0), oppPos = new Vector2(3, 3);
    Worker worker1, worker2;

    @BeforeEach
    void setup(){
        model = new Model();

        pid1 = model.addNewPlayer("", "");
        model.placeWorker(pid1, initPos);
        worker1 = model.getBoard().getWorker(initPos);

        pid2 = model.addNewPlayer("", "");
        model.placeWorker(pid2, oppPos);
        worker2 = model.getBoard().getWorker(oppPos);

        controller = new MatchController(model);
    }

    @Test
    void testNotPlayersTurn(){
        Action action = new Action(worker2, new Vector2(2, 2), ActionType.MOVE);
        assertFalse(controller.performAction(pid2, action));
    }

    @Test
    void testActionInvalid(){
        Action action = new Action(worker1, new Vector2(2, 2), ActionType.MOVE);
        assertFalse(controller.performAction(pid1, action));
    }

    @Test
    void testTurnFinished(){
        Action move = new Action(worker1, new Vector2(1, 1), ActionType.MOVE);
        assertTrue(controller.performAction(pid1, move));
        Action build = new Action(worker1, new Vector2(0, 0), ActionType.BUILD);
        assertTrue(controller.performAction(pid1, build));
        Action finish = new Action(worker1, new Vector2(0, 0), ActionType.END_TURN);
        assertTrue(controller.performAction(pid1, finish));
        assertEquals(model.getCurPlayer(), pid2);
    }

    @Test
    void testWin(){
        model.getBoard().setHeight(initPos, 2);
        model.getBoard().setHeight(new Vector2(1, 1), 3);
        Action action = new Action(worker1, new Vector2(1, 1), ActionType.MOVE);
        assertTrue(controller.performAction(pid1, action));
        assertTrue(controller.isStageDone());
    }
}
