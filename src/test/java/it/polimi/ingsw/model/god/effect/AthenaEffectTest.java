package it.polimi.ingsw.model.god.effect;

import it.polimi.ingsw.model.god.God;
import it.polimi.ingsw.model.god.GodFactory;
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

public class AthenaEffectTest {
    Board board;
    Player player, opponent;
    God athena;
    Vector2 highPos = new Vector2(0, 0);
    Vector2 lowPos = new Vector2(1, 1);
    Vector2 oppInitPos = new Vector2(0, 1);
    Worker oppWorker;


    @BeforeEach
    void setup(){
        board = new Board();
        player = new Player(board);
        athena = GodFactory.createGod("Athena", board, player);
        player.setPlayerGod(athena);
        opponent = new Player(board);
        oppWorker = new Worker(opponent);
        board.placeWorker(oppWorker, oppInitPos);

        board.setHeight(highPos, 1);
    }

    @Test
    void testEffectActiveValid(){
        board.setEffectActive(player, true);
        Action moveValid = new Action(oppWorker, lowPos, ActionType.MOVE);
        assertTrue(board.isActionPermittedByEffects(moveValid));
    }

    @Test
    void testEffectActiveInvalid(){
        board.setEffectActive(player, true);
        Action moveInvalid = new Action(oppWorker, highPos, ActionType.MOVE);
        assertFalse(board.isActionPermittedByEffects(moveInvalid));
    }

    @Test
    void testEffectInactive(){
        Action moveInvalid = new Action(oppWorker, highPos, ActionType.MOVE);
        assertTrue(board.isActionPermittedByEffects(moveInvalid));
    }
}
