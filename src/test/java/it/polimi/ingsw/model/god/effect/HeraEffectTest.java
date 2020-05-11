package it.polimi.ingsw.model.god.effect;

import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.god.Hera;
import it.polimi.ingsw.util.ActionType;
import it.polimi.ingsw.util.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HeraEffectTest {
    Board board;
    Player player, opponent;
    Hera hera;
    Vector2 borderPos = new Vector2(1, 0);
    Vector2 centerPos = new Vector2(1, 2);
    Vector2 oppInitPos = new Vector2(1, 1);
    Worker oppWorker;


    @BeforeEach
    void setup(){
        board = new Board();
        player = new Player(board);
        hera = new Hera(board, player);
        player.setPlayerGod(hera);
        opponent = new Player(board);
        oppWorker = new Worker(opponent);
        board.placeWorker(oppWorker, oppInitPos);

        board.setHeight(borderPos, 3);
        board.setHeight(centerPos, 3);
        board.setHeight(oppInitPos, 2);
    }

    @Test
    void testEffectActiveValid(){
        board.setEffectActive(player, true);
        Action moveValid = new Action(oppWorker, centerPos, ActionType.MOVE);
        assertTrue(board.isWinPermittedByEffects(moveValid));
    }

    @Test
    void testEffectActiveInvalid(){
        board.setEffectActive(player, true);
        Action moveInvalid = new Action(oppWorker, borderPos, ActionType.MOVE);
        assertFalse(board.isWinPermittedByEffects(moveInvalid));
    }
}
