package it.polimi.ingsw.util.listeners;

import it.polimi.ingsw.controller.Controller;
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
        God god = new God(model.getBoard());
        listener.onPlayerCreate(0, god);
        assertEquals(model.getNumberOfPlayers(), 1);
        assertEquals(model.getPlayer(0).getPlayerGod(), god);
    }

    @Test
    void testGodSelectionOutOfOrder(){
        God god = new God(model.getBoard());
        listener.onPlayerCreate(1, god);
        assertEquals(model.getNumberOfPlayers(), 0);
    }

    @Test
    void testPlayerAction(){
        listener.onPlayerCreate(0, new God(model.getBoard()));
        listener.onPlayerCreate(1, new God(model.getBoard()));
        Action place = new Action(null, new Vector2(0, 0), ActionType.PLACE_WORKER);
        listener.onPlayerAction(0, place);
        assertEquals(model.getPlayer(0).getNumWorkers(), 1);
        assertEquals(model.getBoard().getWorker(new Vector2(0, 0)), model.getPlayer(0).getWorker(0));
    }
}
