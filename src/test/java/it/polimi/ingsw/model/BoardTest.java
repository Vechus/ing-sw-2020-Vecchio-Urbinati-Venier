package it.polimi.ingsw.model;

import it.polimi.ingsw.model.god.God;
import it.polimi.ingsw.util.ConsoleColor;
import it.polimi.ingsw.util.Vector2;
import it.polimi.ingsw.model.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    Model model = new Model();
    Board board = model.getBoard();
    int p1 = model.addNewPlayer(new God(board), "");
    int p2 = model.addNewPlayer(new God(board), "");
    int p3 = model.addNewPlayer(new God(board), "");

    @Test
    void print() {
        model.getPlayer(p1).setPlayerColor(ConsoleColor.BLUE_UNDERLINED + ConsoleColor.BLUE_BOLD_BRIGHT);
        model.getPlayer(p2).setPlayerColor(ConsoleColor.GREEN_UNDERLINED + ConsoleColor.GREEN_BOLD_BRIGHT);
        model.getPlayer(p3).setPlayerColor(ConsoleColor.RED_UNDERLINED + ConsoleColor.RED_BOLD_BRIGHT);
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                board.setHeight(new Vector2(i,j), (int) ((Math.random()*10) % 4));
            }
        }
        board.setComplete(new Vector2(0,0), true);
        board.setComplete(new Vector2(0,4), true);
        board.setComplete(new Vector2(4,3), true);
        board.setHeight(new Vector2(4,3), 3);
        model.placeWorker(p1, new Vector2(0,1));
        model.placeWorker(p1, new Vector2(0,3));
        model.placeWorker(p2, new Vector2(3,4));
        model.placeWorker(p2, new Vector2(2,3));
        model.placeWorker(p3, new Vector2(1,2));
        model.placeWorker(p3, new Vector2(4,1));
        board.print();
    }

    @Test
    void isEffectActive() {
        assertFalse(board.isEffectActive(model.getPlayer(p1)));
    }
}