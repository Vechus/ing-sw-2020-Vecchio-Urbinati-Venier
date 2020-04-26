package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.util.Vector2;
import org.testng.internal.collections.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Zeus extends God {
    public Zeus(Board board, Player player) {
        super(board, player);
    }

    List<Function<Pair<Action, Board>, Boolean>> buildBlockValidationFunctions = new ArrayList<>(
            Arrays.asList(GodValidationMethods::isTargetPosWithinBoard,
                    this::isCellOtherWorkersFree,
                    GodValidationMethods::isTargetPosDomesFree,
                    GodValidationMethods::isTargetPosAdjacent,
                    GodValidationMethods::isBuildingHeightLessThanThree
            ));



    boolean isCellOtherWorkersFree(Pair<Action, Board> actionBoardPair){
        Action action = actionBoardPair.first();
        if (this.board.getWorker(action.getTargetPos()) != null && !(action.getTargetPos().equals(action.getWorker().getPosition()))  ){
            return false;
        }
        return true;
    }

}
