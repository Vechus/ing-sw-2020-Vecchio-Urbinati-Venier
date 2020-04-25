package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.util.ConsoleColor;
import it.polimi.ingsw.util.Vector2;
import org.junit.jupiter.api.Test;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class ApolloTest {
    /**
     * Your Worker may
     * move into an opponent Worker’s
     * space by forcing their Worker to
     * the space yours just vacated.
     */

    @Test
    void move() {
        Board board = new Board();
        Player p1 = new Player(board);
        Player p2 = new Player(board);
        God god = new God(board, p2);
        Apollo apollo = new Apollo(board, p1);
        Vector2 p1Pos = new Vector2(2,2);
        Vector2 p2Pos = new Vector2(2,3);
        p1.setPlayerColor(ConsoleColor.GREEN_BOLD_BRIGHT);
        p2.setPlayerColor(ConsoleColor.RED_BOLD_BRIGHT);
        p1.setPlayerGod(apollo);
        p2.setPlayerGod(god);
        board.placeWorker(p1.getWorker(0), p1Pos);
        assertEquals(p1.getWorker(0), board.getWorker(p1Pos));
        board.placeWorker(p2.getWorker(0), p2Pos);
        assertEquals(p2.getWorker(0), board.getWorker(p2Pos));
        // action move
        board.print();
        assertTrue(p1.doAction(new Action(p1.getWorker(0), p2Pos, Action.ActionType.MOVE)));
        assertEquals(board.getWorker(p1Pos), p2.getWorker(0));
        assertEquals(board.getWorker(p2Pos), p1.getWorker(0));
    }

    @Test
    void isWorkersMoveValid() {
        Board board = new Board();
        Player p1 = new Player(board);
        Player p2 = new Player(board);
        God god = new God(board, p2);
        Apollo apollo = new Apollo(board, p1);
        Vector2 p1Pos = new Vector2(2,2);
        Vector2 p2Pos = new Vector2(2,3);
        p1.setPlayerColor(ConsoleColor.GREEN_BOLD_BRIGHT);
        p2.setPlayerColor(ConsoleColor.RED_BOLD_BRIGHT);
        p1.setPlayerGod(apollo);
        p2.setPlayerGod(god);
        board.placeWorker(p1.getWorker(0), p1Pos);
        assertEquals(p1.getWorker(0), board.getWorker(p1Pos));
        board.placeWorker(p2.getWorker(0), p2Pos);
        assertEquals(p2.getWorker(0), board.getWorker(p2Pos));
        assertTrue(apollo.isMoveValid(new Action(p1.getWorker(0), p2Pos, Action.ActionType.MOVE)));
    }
}