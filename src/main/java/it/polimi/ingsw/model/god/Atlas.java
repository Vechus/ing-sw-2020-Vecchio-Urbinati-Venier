package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Simple god: Your Build: Your Worker may
 * build a dome at any level.
 */
public class Atlas extends God {
    public Atlas(Board board, Player player) {
        super(board, player);
        name = "Atlas";

        this.buildDomeValidationFunctions = new ArrayList<>(
                Arrays.asList(GodValidationMethods::isTargetPosWithinBoard,
                        GodValidationMethods::isCellWorkersFree,
                        GodValidationMethods::isTargetPosOnDifferentCell,
                        GodValidationMethods::isTargetPosDomesFree,
                        GodValidationMethods::isTargetPosAdjacent
                ));
    }
}