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

public class HephaestusTest {
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
        god= new Hephaestus(board, player);
        board.setHeight(highPos, 2);
        board.setHeight(lowPos, 0);
        board.setHeight(midPos, 1);
        worker = new Worker(player);
        board.placeWorker(worker, midPos);
    }
    @Test
    void buildValid(){
        Action move= new Action(worker, highPos, ActionType.MOVE);
        assertTrue(god.chooseAction(move));
        Action firstAction= new Action(worker, lowPos, ActionType.BUILD);
        assertTrue(god.chooseAction(firstAction));
        Action secondAction=new Action(worker, lowPos, ActionType.BUILD);
        assertTrue(god.chooseAction(secondAction));
        assertEquals(2, board.getHeight(lowPos));
    }

    @Test
    void  buildNotValid(){
        Action move= new Action(worker, highPos, ActionType.MOVE);
        assertTrue(god.chooseAction(move));
        Action firstAction= new Action(worker, lowPos, ActionType.BUILD);
        assertTrue(god.chooseAction(firstAction));
        Action secondAction=new Action(worker, midPos, ActionType.BUILD);
        assertFalse(god.chooseAction(secondAction));
        assertNotEquals(3, board.getHeight(highPos));
    }
}
