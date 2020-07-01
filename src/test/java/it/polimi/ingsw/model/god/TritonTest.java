package it.polimi.ingsw.model.god;

import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.util.ActionType;
import it.polimi.ingsw.util.Vector2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class TritonTest {
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
        god = GodFactory.createGod("Triton", board, player);
        board.setHeight(highPos, 2);
        board.setHeight(lowPos, 0);
        board.setHeight(midPos, 1);
        worker = new Worker(player);
        board.placeWorker(worker, lowPos);
    }

    @Test
    void moveValid(){
        Action firstMove=new Action(worker, midPos, ActionType.MOVE);
        assertTrue(god.move(firstMove));
        Action secondMove=new Action(worker, highPos, ActionType.MOVE);
        assertTrue(god.move(secondMove));
        assertTrue(board.getWorker(midPos)==null && board.getWorker(highPos)==worker);
    }

    @Test
    void moveNotValid(){
        Action firstMove=new Action(worker, midPos, ActionType.MOVE);
        assertTrue(god.chooseAction(firstMove));
        Action secondMove=new Action(worker, lowPos, ActionType.MOVE);
        assertTrue(god.chooseAction(secondMove));
        Action thirdMove=new Action(worker, midPos, ActionType.MOVE);
        assertFalse(god.chooseAction(thirdMove));
        assertFalse(board.getWorker(midPos)==worker && board.getWorker(lowPos)==null);
    }
}
