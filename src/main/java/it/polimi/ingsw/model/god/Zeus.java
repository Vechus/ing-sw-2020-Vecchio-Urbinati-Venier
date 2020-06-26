package it.polimi.ingsw.model.god;

import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Advanced god: Your Build: Your Worker may
 * build a block under itself.
 */
public class Zeus extends God {
    public Zeus(Board board, Player player) {
        super(board, player);
        name = "Zeus";

        this.buildBlockValidationFunctions = new ArrayList<>(
                Arrays.asList(GodValidationMethods::isTargetPosWithinBoard,
                        this::isCellOtherWorkersFree,
                        GodValidationMethods::isTargetPosDomesFree,
                        GodValidationMethods::isTargetPosAdjacent,
                        GodValidationMethods::isBuildingHeightLessThanThree
                ));

    }


    /**
     * checks if the cells different from the one he is in, are without workers
     * @param actionBoardPair board and action chosen
     * @return true if the are no workers if the other cells
     */
    boolean isCellOtherWorkersFree(Pair<Action, Board> actionBoardPair){
        Action action = actionBoardPair.first();
        return this.board.getWorker(action.getTargetPos()) == null || action.getTargetPos().equals(action.getWorker().getPosition());
    }

}
