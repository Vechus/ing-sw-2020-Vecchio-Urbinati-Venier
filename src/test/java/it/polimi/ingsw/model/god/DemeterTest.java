package it.polimi.ingsw.model.god;

import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.util.ActionType;
import it.polimi.ingsw.util.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DemeterTest {
    Board board;
    Player player;
    Vector2 highPos=new Vector2(1,0);
    Vector2 lowPos=new Vector2(1,1);
    Vector2 midPos=new Vector2(0,0);
    Worker worker;
    God god;

    @BeforeEach
    void setup() {
        board = new Board();
        player = new Player();
        god = GodFactory.createGod("Demeter", board, player);
        board.setHeight(highPos, 2);
        board.setHeight(lowPos, 0);
        board.setHeight(midPos, 1);
        worker = new Worker(player);
        board.placeWorker(worker, midPos);
    }
    @Test
    void validBuild(){
        Action move=new Action(worker, highPos, ActionType.MOVE);
        god.chooseAction(move);
        Action firstBuild=new Action(worker, midPos, ActionType.BUILD);
        assertTrue(god.chooseAction(firstBuild));
        Action secondBuild=new Action(worker, lowPos, ActionType.BUILD);
        assertTrue(god.chooseAction(secondBuild));
        assertTrue(board.getHeight(midPos)==2 && board.getHeight(lowPos)==1);
    }


    @Test
    void notValidBuild(){
        Action move=new Action(worker, highPos, ActionType.MOVE);
        god.chooseAction(move);
        Action firstBuild=new Action(worker, lowPos, ActionType.BUILD);
        assertTrue(god.chooseAction(firstBuild));
        Action secondBuild=new Action(worker, lowPos, ActionType.BUILD);
        assertFalse(god.chooseAction(secondBuild));
        assertNotEquals(2, board.getHeight(lowPos));
        assertEquals(1, board.getHeight(lowPos));
    }

    @Test
    void notValidBuildDome(){
        board.setHeight(lowPos, 2);
        Action move=new Action(worker, highPos, ActionType.MOVE);
        god.chooseAction(move);
        Action firstBuild=new Action(worker, lowPos, ActionType.BUILD);
        assertTrue(god.chooseAction(firstBuild));
        Action secondBuild=new Action(worker, lowPos, ActionType.BUILD_DOME);
        assertFalse(god.chooseAction(secondBuild));
        assertEquals(3, board.getHeight(lowPos));
    }
}
