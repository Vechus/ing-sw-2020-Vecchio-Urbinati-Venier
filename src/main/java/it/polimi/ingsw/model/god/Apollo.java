package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.util.Vector2;
import org.testng.internal.collections.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static it.polimi.ingsw.model.Action.ActionType.MOVE;

public class Apollo extends God {

    public Apollo(Board board, Player player) {
        super(board, player);
        this.moveValidationFunctions = new ArrayList<>(
                Arrays.asList(GodValidationMethods::isTargetPosWithinBoard,
                        GodValidationMethods::isTargetPosOnDifferentCell,
                        GodValidationMethods::isTargetPosDomesFree,
                        GodValidationMethods::isTargetPosAdjacent,
                        GodValidationMethods::isMoveHeightLessThanOne
                ));
    }


    @Override
    public boolean move(Action action) {
        if(!this.isMoveValid(action)) {
            return false;
        }

        if (this.board.getWorker(action.getTargetPos()) != null){
            Worker otherWorker=this.board.getWorker(action.getTargetPos());
            Vector2 initPos = action.getWorker().getPosition();
            this.board.moveWorker(action);
            this.board.placeWorker(otherWorker, initPos);
        }
        else this.board.moveWorker(action);

        return true;
    }

}
