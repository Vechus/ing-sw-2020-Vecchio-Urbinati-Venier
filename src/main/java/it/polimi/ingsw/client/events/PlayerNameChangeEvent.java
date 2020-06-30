package it.polimi.ingsw.client.events;

import javafx.event.EventType;

/**
 * The Player name change event concrete definition.
 */
public class PlayerNameChangeEvent extends CustomEvent{
    public static final EventType<CustomEvent> NAME_CHANGE_EVENT_TYPE = new EventType<>(CUSTOM_EVENT_TYPE, "PlayerNameChange");
    private final String param;

    public PlayerNameChangeEvent(String param) {
        super(NAME_CHANGE_EVENT_TYPE);
        this.param = param;
    }

    @Override
    public void invokeHandler(CustomEventHandler handler) {
        handler.onPlayerNameChange(param);
    }
}
