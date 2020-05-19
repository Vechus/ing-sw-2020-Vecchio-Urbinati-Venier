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
    int p1 = model.addNewPlayer("", "");
    int p2 = model.addNewPlayer("", "");
    int p3 = model.addNewPlayer("",  "");

    @Test
    void isEffectActive() {
        assertFalse(board.isEffectActive(model.getPlayer(p1)));
    }
}