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


public class TritonClass {
    Board board;
    Player player;
    Vector2 highPos=new Vector2(1,0);;
    Vector2 lowPos=new Vector2(1,1);;
    Vector2 midPos=new Vector2(0,0);
    Worker worker;
    God god= new God(board, player);

    @BeforeEach
    void setup() {
        board = new Board();
        player = new Player();
        board.setHeight(highPos, 2);
        board.setHeight(lowPos, 0);
        board.setHeight(midPos, 1);
        board.placeWorker(worker, lowPos);
        worker = new Worker(lowPos, player);
    }

    @Test
    void moveValid(){
        Action firstMove=new Action(worker, midPos, Action.ActionType.MOVE);
        Action secondMove=new Action(worker, highPos, Action.ActionType.MOVE);
        god.move(firstMove);
        god.move(secondMove);
        assertTrue(board.getWorker(midPos)==null && board.getWorker(highPos)==worker);
    }

    @Test
    void moveNotValid(){
        Action firstMove=new Action(worker, midPos, Action.ActionType.MOVE);
        Action secondMove=new Action(worker, lowPos, Action.ActionType.MOVE);
        Action thirdMove=new Action(worker, midPos, Action.ActionType.MOVE);
        god.move(firstMove);
        god.move(secondMove);
        god.move(thirdMove);
        assertFalse(board.getWorker(midPos)==worker && board.getWorker(lowPos)==null);
    }
}
