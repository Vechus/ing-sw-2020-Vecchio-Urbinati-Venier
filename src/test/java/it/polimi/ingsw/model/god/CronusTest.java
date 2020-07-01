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

public class CronusTest {
    Board board;
    Player player;
    God cronus;
    Worker playerWorker;

    @BeforeEach
    void setup(){
        board = new Board();
        player = new Player();
        cronus = GodFactory.createGod("Cronus", board, player);
        playerWorker = new Worker(player);
        board.placeWorker(playerWorker, new Vector2(0, 0));

        for(int i=0;i<4;i++){
            board.setHeight(new Vector2(0, i), 3);
            board.setComplete(new Vector2(0, i), true);
        }
        board.setHeight(new Vector2(1, 0), 3);
    }

    @Test
    void testWinTrue(){
        board.setComplete(new Vector2(1, 0), true);
        Action action = new Action(null, null, ActionType.END_TURN);
        assertTrue(cronus.checkWinCondition(action));
    }

    @Test
    void testWinFalse(){
        Action action = new Action(null, null, ActionType.END_TURN);
        assertFalse(cronus.checkWinCondition(action));
    }
}
