package it.polimi.ingsw.util.listeners;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.god.GodFactory;
import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.god.God;
import it.polimi.ingsw.util.ActionType;
import it.polimi.ingsw.util.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerActionListenerTest {
    Model model;
    PlayerActionListener listener;

    @BeforeEach
    void setup(){
        model = new Model();
        listener = new Controller(model, 2);
    }

    @Test
    void testGodSelection(){
        listener.onPlayerCreate(0, "", "");
        assertEquals(model.getNumberOfPlayers(), 1);
        God god = GodFactory.createGod("", model.getBoard(), model.getPlayer(0));
        assertEquals(model.getPlayer(0).getPlayerGod().getPlayer(), god.getPlayer());
    }

    @Test
    void testGodSelectionOutOfOrder(){
        listener.onPlayerCreate(1, "", "");
        assertEquals(model.getNumberOfPlayers(), 0);
    }

    @Test
    void testPlayerAction(){
        listener.onPlayerCreate(0, "", "");
        listener.onPlayerCreate(1, "", "");
        Action place = new Action(null, new Vector2(0, 0), ActionType.PLACE_WORKER);
        listener.onPlayerAction(0, place);
        assertEquals(model.getPlayer(0).getNumWorkers(), 1);
        assertEquals(model.getBoard().getWorker(new Vector2(0, 0)), model.getPlayer(0).getWorker(0));
    }
}
