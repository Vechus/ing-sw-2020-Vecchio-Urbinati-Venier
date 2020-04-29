package it.polimi.ingsw.connection;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.util.Vector2;

/**
 * Use cases:
 * Normal action: serialize with parameters (client will verify posX & posY >0)
 * pass turn action: will serialize (-1,-1) vectors and PASS_TURN as actionType
 */
public class ActionMessage extends Message {

    private Vector2 start;
    private Vector2 target;
    private Action.ActionType actionType;

    /**
     * Gets start.
     *
     * @return the start
     */
    public Vector2 getStart() {
        return start;
    }

    /**
     * Sets start.
     *
     * @param start the start
     */
    public void setStart(Vector2 start) {
        this.start = start;
    }

    /**
     * Gets target.
     *
     * @return the target
     */
    public Vector2 getTarget() {
        return target;
    }

    /**
     * Sets target.
     *
     * @param target the target
     */
    public void setTarget(Vector2 target) {
        this.target = target;
    }

    /**
     * Gets action type.
     *
     * @return the action type
     */
    public Action.ActionType getActionType() {
        return actionType;
    }

    /**
     * Sets action type.
     *
     * @param actionType the action type
     */
    public void setActionType(Action.ActionType actionType) {
        this.actionType = actionType;
    }
}
