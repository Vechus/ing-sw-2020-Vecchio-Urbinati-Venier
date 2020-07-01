package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.util.ActionType;
import it.polimi.ingsw.util.Pair;
import it.polimi.ingsw.util.Vector2;

public class GodMoveMethods {

    static boolean defaultMove(Pair<Action, Board> actionBoardPair) {
        Action action = actionBoardPair.first();
        Board board = actionBoardPair.second();
        board.moveWorker(action);
        return true;
    }

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
