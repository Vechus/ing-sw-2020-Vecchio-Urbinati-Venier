package it.polimi.ingsw.model;

import it.polimi.ingsw.model.god.God;
import org.junit.jupiter.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    @Test
    void addNewPlayer() {
        final Model model = new Model();
        model.addNewPlayer(new God(model.getBoard()));
        Assert.assertEquals(1, model.getNumberOfPlayers());
    }

    @Test
    void checkPlayersLoseCondition() {
        Assert.assertTrue(true);
    }

    @Test
    void checkGameOver() {
        final Model model = new Model();
        model.addNewPlayer(new God(model.getBoard()));
        Assert.assertTrue(model.checkGameOver());
        model.addNewPlayer(new God(model.getBoard()));
        Assert.assertFalse(model.checkGameOver());
    }

    @Test
    void executeAction() {
        Assert.assertTrue(true);
    }

    @Test
    void beginNewTurn() {
        Assert.assertTrue(true);
    }

    @Test
    void getNumberOfPlayers() {
        final Model model = new Model();
        model.addNewPlayer(new God(model.getBoard()));
        Assert.assertEquals(model.getNumberOfPlayers(), 1);
        model.addNewPlayer(new God(model.getBoard()));
        Assert.assertEquals(model.getNumberOfPlayers(), 2);
    }
}