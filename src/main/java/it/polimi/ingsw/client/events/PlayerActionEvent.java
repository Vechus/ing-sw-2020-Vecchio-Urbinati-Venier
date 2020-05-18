package it.polimi.ingsw.client.events;

import it.polimi.ingsw.client.ClientAction;
import javafx.event.EventType;

public class PlayerActionEvent extends CustomEvent{
    public static final EventType<CustomEvent> PLAYER_ACTION_EVENT_TYPE = new EventType<>(CUSTOM_EVENT_TYPE, "PlayerAction");
    private final ClientAction param;

    public PlayerActionEvent(ClientAction param) {
        super(PLAYER_ACTION_EVENT_TYPE);
        this.param = param;
    }

    @Override
    public void invokeHandler(CustomEventHandler handler) {
        handler.onPlayerAction(param);
    }
}
