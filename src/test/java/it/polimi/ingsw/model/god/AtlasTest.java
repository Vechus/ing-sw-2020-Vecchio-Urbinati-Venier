package it.polimi.ingsw.model.god;

import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.util.ActionType;
import it.polimi.ingsw.util.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.testng.Assert.assertTrue;

public class AtlasTest {
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
        god= new Atlas(board, player);
        board.setHeight(highPos, 2);
        board.setHeight(lowPos,0);
        board.setHeight(midPos,1);
        worker=new Worker (player);
        board.placeWorker(worker, midPos);
    }

    @Test
    void validBuild(){
        Action move =new Action(worker, highPos, ActionType.MOVE);
        assertTrue(god.chooseAction(move));
        Action action =new Action(worker, lowPos, ActionType.BUILD_DOME);
        assertTrue(god.chooseAction(action));
    }

}
