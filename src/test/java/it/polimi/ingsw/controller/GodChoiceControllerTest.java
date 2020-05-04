package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.stage.GodChoiceController;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.god.God;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GodChoiceControllerTest {
    Model model;
    GodChoiceController controller;

    @BeforeEach
    void setup(){
        model = new Model();
        controller = new GodChoiceController(model);
    }

    @Test
    void testValid(){
        God god = new God(model.getBoard());
        assertTrue(controller.createPlayer(0, god));
        assertEquals(god.getPlayer(), model.getPlayer(0));
        assertEquals(model.getPlayer(0).getPlayerGod(), god);
    }

    @Test
    void testOutOfOrder(){
        God god = new God(model.getBoard());
        God god2 = new God(model.getBoard());
        assertFalse(controller.createPlayer(1, god2));
        assertTrue(controller.createPlayer(0, god));
        assertTrue(controller.createPlayer(1, god2));
    }
}
