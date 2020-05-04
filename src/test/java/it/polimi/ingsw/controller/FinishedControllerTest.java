package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.stage.FinishedController;
import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.god.God;
import it.polimi.ingsw.util.Vector2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class FinishedControllerTest {
    Model model = new Model();
    FinishedController controller = new FinishedController(model);
    int pid = model.addNewPlayer(new God(model.getBoard()));

    @Test
    void testAction(){
        model.placeWorker(pid, new Vector2(0, 0));
        Action a = new Action(model.getPlayer(pid).getWorker(0), new Vector2(0, 1), Action.ActionType.MOVE);
        assertFalse(controller.performAction(pid, a));
    }
}
