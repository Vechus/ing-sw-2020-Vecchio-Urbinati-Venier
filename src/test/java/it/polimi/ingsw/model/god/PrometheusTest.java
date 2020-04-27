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
public class PrometheusTest {
    Board board;
    Player player;
    Vector2 highPos=new Vector2(1,0);;
    Vector2 lowPos=new Vector2(1,1);;
    Vector2 midPos=new Vector2(0,0);
    Worker worker;
    God god;

    @BeforeEach
    void setup() {
        board = new Board();
        player = new Player();
        god= new Prometheus(board, player);
        board.setHeight(highPos, 2);
        board.setHeight(lowPos, 0);
        board.setHeight(midPos, 1);
        worker = new Worker(player);
        board.placeWorker(worker, midPos);
    }

    @Test
    void validTurn(){
        Action firstAction= new Action(worker, lowPos, Action.ActionType.BUILD);
        assertTrue(god.chooseAction(firstAction));
        Action secondAction= new Action(worker, lowPos, Action.ActionType.MOVE);
        assertTrue(god.chooseAction(secondAction));
        Action thirdAction= new Action(worker, midPos, Action.ActionType.BUILD);
        assertTrue(god.chooseAction(thirdAction));
        assertTrue(board.getWorker(lowPos)==worker&&
                board.getHeight(lowPos)==1&&
                board.getHeight(midPos)==2);
    }

    @Test
    void notValidTurn(){
        Action firstAction= new Action(worker, lowPos, Action.ActionType.BUILD);
        assertTrue(god.chooseAction(firstAction));
        Action secondAction= new Action(worker, highPos, Action.ActionType.MOVE);
        assertFalse(god.chooseAction(secondAction));
        Action thirdAction= new Action(worker, midPos, Action.ActionType.BUILD);
        assertFalse(god.chooseAction(thirdAction));
        assertFalse(board.getWorker(highPos)==worker ||
                board.getHeight(midPos)==2);
    }
}
