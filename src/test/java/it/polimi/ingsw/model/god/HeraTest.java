package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.util.ActionType;
import it.polimi.ingsw.util.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HeraTest {
    Board board;
    Player player, opponent;
    Hera hera;
    God oppGod;
    Vector2 pos1 = new Vector2(0, 0), pos2 = new Vector2(0, 1), pos3 = new Vector2(1, 1);
    Worker work;

    @BeforeEach
    void setup(){
        board = new Board();
        player = new Player(board);
        hera = new Hera(board, player);
        player.setPlayerGod(hera);

        opponent = new Player(board);
        oppGod = new God(board, opponent);
        opponent.setPlayerGod(oppGod);
        work = new Worker(opponent);
        board.placeWorker(work, pos1);

        board.setHeight(pos1, 2);
        board.setHeight(pos2, 3);
        board.setHeight(pos3, 3);
    }

    @Test
    void testDefaultActive(){
        assertTrue(board.isEffectActive(player));
    }

    @Test
    void testEffectActive(){
        Action a = new Action(work, pos2, ActionType.MOVE);
        assertFalse(oppGod.checkWinCondition(a));
        a = new Action(work, pos3, ActionType.MOVE);
        assertTrue(oppGod.checkWinCondition(a));
    }
}
