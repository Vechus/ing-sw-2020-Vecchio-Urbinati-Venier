package it.polimi.ingsw.client;

import it.polimi.ingsw.util.ActionType;
import it.polimi.ingsw.util.Vector2;

public class ClientAction {
    private Vector2 from, to;
    private ActionType type;

    public ClientAction(Vector2 from, Vector2 to, ActionType type){
        this.from = from;
        this.to = to;
        this.type = type;
    }


    public Vector2 getFrom() {
        return from;
    }

    public void setFrom(Vector2 from) {
        this.from = from;
    }

    public Vector2 getTo() {
        return to;
    }

    public void setTo(Vector2 to) {
        this.to = to;
    }

    public ActionType getType() {
        return type;
    }

    public void setType(ActionType type) {
        this.type = type;
    }
}
