package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.util.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ArtemisTest {
    Board board;
    Player player;
    Vector2 highPos=new Vector2(1,0);;
    Vector2 lowPos=new Vector2(1,1);;
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
        Action firstAction= new Action(worker, highPos, Action.ActionType.MOVE);
        assertTrue(god.chooseAction(firstAction));
        Action secondAction= new Action(worker, lowPos, Action.ActionType.MOVE);
        assertTrue(god.chooseAction(secondAction));
        assertTrue(board.getWorker(midPos)==null);
        assertTrue(board.getWorker(highPos)==null);
        assertTrue(board.getWorker(lowPos)==worker);
    }

    @Test
    void notValidMove(){
        Action firstAction= new Action(worker, highPos, Action.ActionType.MOVE);
        god.move(firstAction);
        Action secondAction= new Action(worker, midPos, Action.ActionType.MOVE);
        god.move(secondAction);
        assertFalse(board.getWorker(midPos)==worker&&
                board.getWorker(highPos)==null);
    }

    @Test
    void movingAfterBuildNotValid(){
        Action firstAction= new Action(worker, highPos, Action.ActionType.MOVE);
        god.move(firstAction);
        Action secondAction= new Action(worker, midPos, Action.ActionType.BUILD);
        god.buildBlock(secondAction);
        Action thirdAction= new Action(worker, lowPos, Action.ActionType.MOVE);
        god.move(thirdAction);
        assertFalse(board.getWorker(lowPos)==worker);
        assertTrue(board.getWorker(highPos)==worker);
    }
}
