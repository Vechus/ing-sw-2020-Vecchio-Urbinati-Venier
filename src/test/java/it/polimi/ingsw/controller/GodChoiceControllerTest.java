package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.stage.GodChoiceController;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Player;
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
        assertTrue(controller.createPlayer(0, "", ""));
        Player player = model.getPlayer(0);
        assertEquals(player.getPlayerGod().getPlayer(), player);
    }

    @Test
    void testOutOfOrder(){
        assertFalse(controller.createPlayer(1, "", ""));
        assertTrue(controller.createPlayer(0, "", ""));
        assertTrue(controller.createPlayer(1, "", ""));
    }
}
