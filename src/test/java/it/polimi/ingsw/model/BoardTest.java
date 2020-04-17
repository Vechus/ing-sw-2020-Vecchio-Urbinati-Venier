package it.polimi.ingsw.model;

import it.polimi.ingsw.util.ConsoleColor;
import it.polimi.ingsw.util.Vector2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void print() {
        Board board = new Board();
        Player p1 = new Player(board);
        Player p2 = new Player(board);
        Player p3 = new Player(board);
        p1.setPlayerColor(ConsoleColor.BLUE_UNDERLINED + ConsoleColor.BLUE_BOLD_BRIGHT);
        p2.setPlayerColor(ConsoleColor.GREEN_UNDERLINED + ConsoleColor.GREEN_BOLD_BRIGHT);
        p3.setPlayerColor(ConsoleColor.RED_UNDERLINED + ConsoleColor.RED_BOLD_BRIGHT);
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                board.setHeight(new Vector2(i,j), (int) ((Math.random()*10) % 4));
            }
        }
        board.setComplete(new Vector2(0,0), true);
        board.setComplete(new Vector2(0,4), true);
        board.setComplete(new Vector2(4,3), true);
        board.setHeight(new Vector2(4,3), 3);
        board.placeWorker(p1.getWorker(1), new Vector2(0,1));
        board.placeWorker(p1.getWorker(0), new Vector2(0,3));
        board.placeWorker(p2.getWorker(1), new Vector2(3,4));
        board.placeWorker(p2.getWorker(0), new Vector2(2,3));
        board.placeWorker(p3.getWorker(1), new Vector2(1,2));
        board.placeWorker(p3.getWorker(0), new Vector2(4,1));
        board.print();
    }
}