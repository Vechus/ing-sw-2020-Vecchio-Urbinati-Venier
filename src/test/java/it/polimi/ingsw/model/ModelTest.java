package it.polimi.ingsw.model;

import it.polimi.ingsw.model.god.God;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    @Test
    void addNewPlayer() {
        final Model model = new Model();
        model.addNewPlayer(new God());
        assertEquals(1, model.getNumberOfPlayers());
    }

    @Test
    void checkPlayersLoseCondition() {
        assertTrue(true);
    }

    @Test
    void checkGameOver() {
        final Model model = new Model();
        model.addNewPlayer(new God());
        assertTrue(model.checkGameOver());
        model.addNewPlayer(new God());
        assertFalse(model.checkGameOver());
    }

    @Test
    void executeAction() {
        assertTrue(true);
    }

    @Test
    void beginNewTurn() {
        assertTrue(true);
    }

    @Test
    void getNumberOfPlayers() {
        final Model model = new Model();
        model.addNewPlayer(new God());
        assertEquals(model.getNumberOfPlayers(), 1);
        model.addNewPlayer(new God());
        assertEquals(model.getNumberOfPlayers(), 2);
    }
}