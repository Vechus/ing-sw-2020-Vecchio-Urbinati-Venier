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
        int pid = model.addNewPlayer(new God(board));
        model.placeWorker(pid, origin);
        model.beginNewTurn(0);
        board.setHeight(new Vector2(0,1), 2);
        board.setHeight(new Vector2(1,0), 2);
        model.checkPlayersLoseCondition();
        assertFalse(model.getPlayer(pid).isSpectator());
        board.setHeight(new Vector2(1,1), 3);
        model.checkPlayersLoseCondition();
        assertTrue(model.getPlayer(pid).isSpectator());
    }

    @Test
    void checkGameOver() {
        int pid1 = model.addNewPlayer(new God(model.getBoard()));
        Assert.assertTrue(model.checkGameOver());
        int pid2 = model.addNewPlayer(new God(model.getBoard()));
        Assert.assertFalse(model.checkGameOver());
    }

    @Test
    void executeAction() {
        int pid = model.addNewPlayer(new God(model.getBoard()));
        model.placeWorker(pid, new Vector2(0,0));
        assertTrue(model.executeAction(pid, new Action(model.getPlayer(pid).getWorker(0), new Vector2(0,1), Action.ActionType.MOVE)));
    }

    @Test
    void beginNewTurn() {
        model.addNewPlayer(new God(model.getBoard()));
        model.getPlayer(0).setFinished(true);
        model.beginNewTurn(0);
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