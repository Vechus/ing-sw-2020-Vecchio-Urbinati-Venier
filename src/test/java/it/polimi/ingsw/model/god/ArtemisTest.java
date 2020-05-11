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

public class ArtemisTest {
    Board board;
    Player player;
    Vector2 highPos=new Vector2(1,0);
    Vector2 lowPos=new Vector2(1,1);
    Vector2 midPos=new Vector2(0,0);
    Worker worker;
    God god;

    @BeforeEach
    void setup(){
        board= new Board();
        player=new Player();
        god= new Artemis(board, player);
        board.setHeight(highPos, 2);
        board.setHeight(lowPos,0);
        board.setHeight(midPos,1);
        worker=new Worker (player);
        board.placeWorker(worker, midPos);
    }

    @Test
    void validMove(){
        Action firstAction= new Action(worker, highPos, ActionType.MOVE);
        assertTrue(god.chooseAction(firstAction));
        Action secondAction= new Action(worker, lowPos, ActionType.MOVE);
        assertTrue(god.chooseAction(secondAction));
        assertNull(board.getWorker(midPos));
        assertNull(board.getWorker(highPos));
        assertSame(board.getWorker(lowPos), worker);
    }

    @Test
    void notValidMove(){
        Action firstAction= new Action(worker, highPos, ActionType.MOVE);
        assertTrue(god.chooseAction(firstAction));
        Action secondAction= new Action(worker, midPos, ActionType.MOVE);
        assertFalse(god.chooseAction(secondAction));
        assertFalse(board.getWorker(midPos)==worker&&
                board.getWorker(highPos)==null);
    }

    @Test
    void movingAfterBuildNotValid(){
        Action firstAction= new Action(worker, highPos, ActionType.MOVE);
        assertTrue(god.chooseAction(firstAction));
        Action secondAction= new Action(worker, midPos, ActionType.BUILD);
        assertTrue(god.chooseAction(secondAction));
        Action thirdAction= new Action(worker, lowPos, ActionType.MOVE);
        assertFalse(god.chooseAction(thirdAction));
        assertNotSame(board.getWorker(lowPos), worker);
        assertSame(board.getWorker(highPos), worker);
    }
}
