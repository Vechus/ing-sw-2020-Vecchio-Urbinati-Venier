package it.polimi.ingsw.model.god;

import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import org.testng.internal.collections.Pair;

import java.util.ArrayList;
import java.util.Arrays;

public class Zeus extends God {
    public Zeus(Board board, Player player) {
        super(board, player);
        this.buildBlockValidationFunctions = new ArrayList<>(
                Arrays.asList(GodValidationMethods::isTargetPosWithinBoard,
                        this::isCellOtherWorkersFree,
                        GodValidationMethods::isTargetPosDomesFree,
                        GodValidationMethods::isTargetPosAdjacent,
                        GodValidationMethods::isBuildingHeightLessThanThree
                ));

    }


    boolean isCellOtherWorkersFree(Pair<Action, Board> actionBoardPair){
        Action action = actionBoardPair.first();
        return this.board.getWorker(action.getTargetPos()) == null || action.getTargetPos().equals(action.getWorker().getPosition());
    }

}
