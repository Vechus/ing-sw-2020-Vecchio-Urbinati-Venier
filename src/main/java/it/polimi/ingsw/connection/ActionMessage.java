package it.polimi.ingsw.connection;

import it.polimi.ingsw.client.ClientAction;
import it.polimi.ingsw.util.ActionType;
import it.polimi.ingsw.util.Vector2;

/**
 * Use cases:
 * Normal action: serialize with parameters (client will verify posX & posY >0)
 * pass turn action: will serialize (-1,-1) vectors and PASS_TURN as actionType
 */
public class ActionMessage extends Message {
    private ClientAction action;

    public ActionMessage(){
        setMessageType(MessageType.ACTION);
    }

    public ClientAction getAction() {
        return action;
    }

    public void setAction(ClientAction action) {
        this.action = action;
    }
}
