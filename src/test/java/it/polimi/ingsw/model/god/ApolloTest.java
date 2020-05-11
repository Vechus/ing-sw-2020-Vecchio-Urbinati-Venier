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

class ApolloTest {
    Board board;
    Player p1;
    Player p2;
    God god;
    Apollo apollo;
    Worker w1, w2;
    Vector2 p1Pos = new Vector2(2,2);
    Vector2 p2Pos = new Vector2(2,3);
    /**
     * Your Worker may
     * move into an opponent Worker’s
     * space by forcing their Worker to
     * the space yours just vacated.
     */

    @BeforeEach
    void setup() {
        board = new Board();
        p1 = new Player(board);
        p2 = new Player(board);
        god = new God(board, p2);
        apollo = new Apollo(board, p1);
        p1.setPlayerGod(apollo);
        p2.setPlayerGod(god);
        w1 = new Worker(p1);
        w2 = new Worker(p2);
        board.placeWorker(w1, p1Pos);
        board.placeWorker(w2, p2Pos);
    }

    @Test
    void move() {
        // action move
        assertTrue(p1.doAction(new Action(w1, p2Pos, ActionType.MOVE)));
        assertEquals(board.getWorker(p1Pos), w2);
        assertEquals(board.getWorker(p2Pos), w1);
    }

    @Test
    void isWorkersMoveValid() {
        assertTrue(apollo.isMoveValid(new Action(w1, p2Pos, ActionType.MOVE)));
    }
}