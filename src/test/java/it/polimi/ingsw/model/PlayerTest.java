package it.polimi.ingsw.model;

import it.polimi.ingsw.model.god.God;
import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.util.ActionType;
import it.polimi.ingsw.util.ConsoleColor;
import it.polimi.ingsw.util.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    Model model;
    Board board;
    Player p1;
    Player p2;
    int pid1, pid2;
    Vector2 origin = new Vector2(0,0);
    
    @BeforeEach
    void setup(){
        model = new Model();
        board = model.getBoard();
        pid1 = model.addNewPlayer("", "");
        pid2 = model.addNewPlayer("", "");
        p1 = model.getPlayer(pid1);
        p2 = model.getPlayer(pid2);
        p1.setPlayerColor(ConsoleColor.GREEN_BOLD_BRIGHT);
        p2.setPlayerColor(ConsoleColor.BLUE_BOLD_BRIGHT);
    }

    @Test
    void beginNewTurn() {
        p1.setFinished(true);
        p1.setPlayerGod(new God(board, p1));
        p1.beginNewTurn();
        assertFalse(p1.isFinished());
        assertFalse(p1.getPlayerGod().getHasFinishedTurn());
    }

    @Test
    void checkWinCondition() {
        Vector2 winningPos = new Vector2(0,2);
        Vector2 fromPos = new Vector2(0, 1);
        board.setHeight(winningPos, 3);
        board.setHeight(fromPos, 2);
        model.placeWorker(pid1, fromPos);
        // let's win!
        Action winAction = new Action(p1.getWorker(0), winningPos, ActionType.MOVE);
        //p1.doAction(winAction);
        assertTrue(p1.checkWinCondition(winAction));
    }

    @Test
    void checkLoseCondition() {
        // this test places a P1 worker in (0,0,0), surrounded by two P2 workers and a lvl 2 tower in (1,1):
        /*
        P1|P2|..
        P2|b2|...
         */
        model.placeWorker(pid1, origin);
        // he should not lose right now, neither with the P2 only
        assertFalse(p1.checkLoseCondition());
        model.placeWorker(pid2, new Vector2(0,1));
        assertFalse(p1.checkLoseCondition());
        model.placeWorker(pid2, new Vector2(1,0));
        assertFalse(p1.checkLoseCondition());
        board.setHeight(new Vector2(1,1), 2);
        // now he should lose
        assertTrue(p1.checkLoseCondition());
    }

    @Test
    void doAction() {
        model.placeWorker(pid1, origin);
        Vector2 target = new Vector2(0,1);
        Action testActionMove = new Action(p1.getWorker(0), target, ActionType.MOVE);
        Action testActionBuild = new Action(p1.getWorker(0), target, ActionType.BUILD);
        Action testActionBuildDome = new Action(p1.getWorker(0), target, ActionType.BUILD_DOME);
        assertTrue(p1.doAction(testActionMove));
        assertFalse(p1.doAction(testActionBuild));
        assertFalse(p1.doAction(testActionBuildDome));
        p1.beginNewTurn();
        assertEquals(p1.getWorker(0).getPosition(), target);
        Action goBack = new Action(p1.getWorker(0), origin, ActionType.MOVE);
        assertTrue(p1.doAction(goBack));
        assertTrue(p1.doAction(testActionBuild));
        p1.beginNewTurn();
        assertFalse(p1.doAction(testActionBuildDome));
        board.setHeight(target, 3);
        assertTrue(p1.doAction(new Action(p1.getWorker(0), new Vector2(1,0), ActionType.MOVE)));
        assertTrue(p1.doAction(testActionBuildDome));
    }

    @Test
    void getWorker() {
        model.placeWorker(pid1, new Vector2(0, 0));
        model.placeWorker(pid1, new Vector2(1, 1));
        assertNotNull(p1.getWorker(0));
        assertNotNull(p1.getWorker(1));
    }

    @Test
    void getPlayerName() {
        Player p = new Player();
        p.setPlayerName("Vechus");
        assertEquals(p.getPlayerName(), "Vechus");
    }

    @Test
    void getPlayerColor() {
        Player p = new Player(board);
        p.setPlayerColor(ConsoleColor.GREEN);
        assertEquals(p.getPlayerColor(), ConsoleColor.GREEN);
    }

    @Test
    void isSpectator() {
        p1.setSpectator(true);
        assertTrue(p1.isSpectator());
    }
}