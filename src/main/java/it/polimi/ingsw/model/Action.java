package it.polimi.ingsw.model;

import it.polimi.ingsw.util.Vector2;

public class Action {
    private Worker worker;
    private Vector2 workerPos;
    private Vector2 targetPos;
    private ActionType type;



    /**
     * Defines the type of the action.
     * How to use it somewhere else:
         import it.polimi.ingsw.model.Action
         Action.ActionType foo = Action.ActionType.BUILD;
     */
    public enum ActionType {PLACE_WORKER, BUILD, MOVE, BUILD_DOME, END_TURN};


    /**
     * Generate an Action object
     * @param Worker Worker target
     * @param targetPos Vector2 target position of the action
     * @param type ActionType [enum] type of action
     */
    public Action(Worker worker, Vector2 targetPos, ActionType type) {
        this.worker = worker;
        this.targetPos = targetPos;
        if(worker != null) this.workerPos = worker.getPosition();
        this.type = type;
    }

    /**
     * Getter for Worker
     * @return Worker Worker target
     */
    public Worker getWorker() {
        return worker;
    }

    /**
     * Getter for workerPos
     * @return workerPos Vector2 worker position
     */
    public Vector2 getWorkerPos() {
        return workerPos;
    }

    /**
     * Getter for targetPos
     * @return targetPos Vector2 target position
     */
    public Vector2 getTargetPos() {
        return targetPos;
    }

    /**
     * Getter for ActionType
     * @return type enum ActionType: type of the action
     */
    public ActionType getType() {
        return type;
    }
}
