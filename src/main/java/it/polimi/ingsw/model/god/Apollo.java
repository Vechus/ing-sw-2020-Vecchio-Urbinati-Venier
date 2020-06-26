package it.polimi.ingsw.model.god;

import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.util.Vector2;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *Simple god: Your Worker may
 * move into an opponent Worker’s
 * space by forcing their Worker to
 * the space yours just vacated.
 */
public class Apollo extends God {

    public Apollo(Board board, Player player) {
        super(board, player);
        name = "Apollo";

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
