package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.util.ActionType;
import it.polimi.ingsw.util.Pair;
import it.polimi.ingsw.util.Vector2;

/**
 * Class that defines all the possible types of moves which can differ depending on the special power of the god.
 */
public class GodMoveMethods {

    /**
     * Normal move where you go from one cell to another.
     * @param actionBoardPair
     * @return true if the move ends well
     */
    static boolean defaultMove(Pair<Action, Board> actionBoardPair) {
        Action action = actionBoardPair.first();
        Board board = actionBoardPair.second();
        board.moveWorker(action);
        return true;
    }

    /**
     * Move that changes the positions of two workers.
     * @param actionBoardPair
     * @return true if the swap ends well
     */
    static boolean swapMove(Pair<Action, Board> actionBoardPair) {
        Action action = actionBoardPair.first();
        Board board = actionBoardPair.second();

        if (board.getWorker(action.getTargetPos()) != null){
            Worker otherWorker = board.getWorker(action.getTargetPos());
            Vector2 initPos = action.getWorker().getPosition();
            defaultMove(actionBoardPair);
            board.placeWorker(otherWorker, initPos);
        }
        else defaultMove(actionBoardPair);
        return true;
    }

    /**
     * Move that pushes away from the target cell the worker that's there.
     * @param actionBoardPair
     * @return true if the action ends well
     */
    static boolean pushMove(Pair<Action, Board> actionBoardPair) {
        Action action = actionBoardPair.first();
        Board board = actionBoardPair.second();
        Worker otherWorker = board.getWorker(action.getTargetPos());

        if(otherWorker != null) {
            Vector2 delta = action.getTargetPos().sub(action.getWorkerPos());
            Vector2 movedPos = action.getTargetPos().add(delta);
            board.moveWorker(new Action(otherWorker, movedPos, ActionType.MOVE));
        }

        return defaultMove(actionBoardPair);
    }

    /**
     * Activates the power when a worker moves one lever above.
     * @param actionBoardPair
     * @return true if the action ends well
     */
    static boolean activateEffectOnRiseMove(Pair<Action, Board> actionBoardPair) {
        Action action = actionBoardPair.first();
        Board board = actionBoardPair.second();
        Player player = action.getWorker().getOwner();

        Vector2 currPos= action.getWorker().getPosition();
        defaultMove(actionBoardPair);
        int heightDiff = board.getHeight(action.getTargetPos())-board.getHeight(currPos);
        board.setEffectActive(player, heightDiff > 0);

        return true;
    }
}
