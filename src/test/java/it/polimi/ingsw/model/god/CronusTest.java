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

public class CronusTest {
    Board board;
    Player player;
    Cronus chronus;
    Worker playerWorker;

    @BeforeEach
    void setup(){
        board = new Board();
        player = new Player();
        chronus = new Cronus(board, player);
        playerWorker = new Worker(player);
        board.placeWorker(playerWorker, new Vector2(0, 0));

        for(int i=0;i<4;i++){
            board.setHeight(new Vector2(0, i), 3);
            board.setComplete(new Vector2(0, i), true);
        }
    }

    @Test
    void testWinFalse(){
        Action action = new Action(playerWorker, new Vector2(0, 1), ActionType.MOVE);
        assertFalse(chronus.checkWinCondition(action));
    }
}
