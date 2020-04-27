package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.util.Vector2;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ArtemisTest {
    Board board;
    Player player;
    Vector2 highPos=new Vector2(1,0);;
    Vector2 lowPos=new Vector2(1,1);;
    Vector2 midPos=new Vector2(0,0);
    Worker worker;
    God god= new God(board, player);

    @BeforeEach
    void setup(){
        board= new Board();
        player=new Player();
        board.setHeight(highPos, 2);
        board.setHeight(lowPos,0);
        board.setHeight(midPos,1);
        board.placeWorker(worker, midPos);
        worker=new Worker (midPos, player);
    }

    @Test
    void validMove(){
        Action firstAction= new Action(worker, highPos, Action.ActionType.MOVE);
        Action secondAction= new Action(worker, lowPos, Action.ActionType.MOVE);
        god.move(firstAction);
        god.move(secondAction);
        assertTrue(board.getWorker(midPos)==null&&
                board.getWorker(highPos)==null&&
                board.getWorker(lowPos)==worker);
    }

    @Test
    void notValidMove(){
        Action firstAction= new Action(worker, highPos, Action.ActionType.MOVE);
        Action secondAction= new Action(worker, midPos, Action.ActionType.MOVE);
        god.move(firstAction);
        god.move(secondAction);
        assertFalse(board.getWorker(midPos)==worker&&
                board.getWorker(highPos)==null);
    }
}
