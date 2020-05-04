package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.stage.SetupController;
import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.god.God;
import it.polimi.ingsw.util.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SetupControllerTest {
    Model model;
    int pid1, pid2;
    Worker w1, w2;
    SetupController controller;

    @BeforeEach
    void setup(){
        model = new Model();
        pid1 = model.addNewPlayer(new God(model.getBoard()));
        pid2 = model.addNewPlayer(new God(model.getBoard()));
        controller = new SetupController(model);
    }

    @Test
    void testNotPlayersTurn(){
        w2 = new Worker(null, model.getPlayer(pid2));
        Action action = new Action(w2, new Vector2(2, 2), Action.ActionType.MOVE);
        assertFalse(controller.performAction(pid2, action));
    }

    @Test
    void testWrongActionType(){
        Action a = new Action(null, new Vector2(0, 0), Action.ActionType.MOVE);
        assertFalse(controller.performAction(pid1, a));
    }

    @Test
    void testPlaceValid(){
        Action a = new Action(null, new Vector2(0, 0), Action.ActionType.PLACE_WORKER);
        assertTrue(controller.performAction(pid1, a));
        w1 = model.getBoard().getWorker(new Vector2(0, 0));
        assertEquals(w1, model.getPlayer(pid1).getWorker(0));
        assertEquals(w1.getOwner(), model.getPlayer(pid1));
        assertEquals(w1.getPosition(), new Vector2(0, 0));
    }
}
