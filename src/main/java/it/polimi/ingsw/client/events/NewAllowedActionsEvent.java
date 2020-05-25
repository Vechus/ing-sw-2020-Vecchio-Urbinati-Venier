package it.polimi.ingsw.client.events;

import it.polimi.ingsw.util.ActionType;
import javafx.event.EventType;

import java.util.List;

public class NewAllowedActionsEvent extends GameEvent {
    public static final EventType<GameEvent> NEW_ALLOWED_ACTIONS_EVENT_TYPE = new EventType<>(GAME_EVENT_TYPE, "NewAllowedActionsEvent");

    private final List<ActionType> allowedActions;

    public NewAllowedActionsEvent(List<ActionType> allowedActions) {
        super(NEW_ALLOWED_ACTIONS_EVENT_TYPE);
        this.allowedActions = allowedActions;
    }

    @Override
    public void invokeHandler(GameEventHandler handler) {
        handler.onNewAllowedActions(allowedActions);
    }
}
