package it.polimi.ingsw.connection;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.util.Vector2;

public class ActionMessage extends Message{
    /**
     * Use cases:
     *      Normal action: serialize with parameters (client will verify posX & posY >0)
     *      pass turn action: will serialize (-1,-1) vectors and PASS_TURN as actionType
     */
    private Vector2 start;
    private Vector2 target;
    private Action.ActionType actionType;
}
