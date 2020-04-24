package it.polimi.ingsw.model;

import it.polimi.ingsw.model.god.God;
import it.polimi.ingsw.util.Vector2;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {
    final Model model = new Model();

    @Test
    void addNewPlayer() {
        model.addNewPlayer(new God(model.getBoard()));
        Assert.assertEquals(1, model.getNumberOfPlayers());
    }

    @Test
    void checkPlayersLoseCondition() {
        Vector2 origin = new Vector2(0,0);
        Board board = model.getBoard();
        model.addNewPlayer(new God(board));
        board.placeWorker(model.getPlayer(0).getWorker(0), origin);
        model.beginNewTurn(model.getPlayer(0));
        board.setHeight(new Vector2(0,1), 2);
        board.setHeight(new Vector2(1,0), 2);
        model.checkPlayersLoseCondition();
        assertEquals(1, model.getNumberOfPlayers());
        //board.setHeight(new Vector2(1,1), 3);
        //model.checkPlayersLoseCondition();
        //assertEquals(0, model.getNumberOfPlayers());
    }

    @Test
    void checkGameOver() {
        model.addNewPlayer(new God(model.getBoard()));
        Assert.assertTrue(model.checkGameOver());
        model.addNewPlayer(new God(model.getBoard()));
        Assert.assertFalse(model.checkGameOver());
    }

    @Test
    void executeAction() {
        model.addNewPlayer(new God(model.getBoard()));
        model.getBoard().placeWorker(model.getPlayer(0).getWorker(0), new Vector2(0,0));
        assertTrue(model.executeAction(0, new Action(model.getPlayer(0).getWorker(0), new Vector2(0,1), Action.ActionType.MOVE)));
    }

    @Test
    void beginNewTurn() {
        model.addNewPlayer(new God(model.getBoard()));
        model.getPlayer(0).setFinished(true);
        model.beginNewTurn(model.getPlayer(0));
        assertFalse(model.getPlayer(0).isFinished());
    }

    @Test
    void getNumberOfPlayers() {
        model.addNewPlayer(new God(model.getBoard()));
        Assert.assertEquals(model.getNumberOfPlayers(), 1);
        model.addNewPlayer(new God(model.getBoard()));
        Assert.assertEquals(model.getNumberOfPlayers(), 2);
    }
}