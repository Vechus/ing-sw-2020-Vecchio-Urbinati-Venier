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

public class ZeusTest {
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
        board.placeWorker(worker, midPos);
        worker = new Worker(midPos, player);
    }

    @Test
    void buildValid(){
        Action move = new Action(worker, lowPos, Action.ActionType.MOVE );
        Action build = new Action(worker, lowPos, Action.ActionType.BUILD );
        god.move(move);
        god.buildBlock(build);
        assertTrue(board.getHeight(lowPos)==1);
    }
    @Test
    void buildNotValid(){
        Worker otherWorker=new Worker (highPos,player);
        board.placeWorker(otherWorker,highPos);
        Action move = new Action(worker, lowPos, Action.ActionType.MOVE );
        Action build = new Action(worker, highPos, Action.ActionType.BUILD );
        god.move(move);
        god.buildBlock(build);
        assertFalse(board.getHeight(highPos)==3);
    }

}
