package it.polimi.ingsw.connection;

import it.polimi.ingsw.util.ActionType;

import java.util.List;

public class ActionRequestMessage extends Message {
    private List<ActionType> allowedActions;

    public ActionRequestMessage(){
        setMessageType(MessageType.ACTION_REQUEST);
    }

    public List<ActionType> getAllowedActions() {
        return allowedActions;
    }

    public void setAllowedActions(List<ActionType> allowedActions) {
        this.allowedActions = allowedActions;
    }
}
