package it.polimi.ingsw.client.events;

import javafx.event.EventType;

/**
 * The Player number change event concrete definition.
 */
public class PlayerNumberChangeEvent extends CustomEvent {
    public static final EventType<CustomEvent> PLAYER_NUMBER_CHANGE_EVENT_TYPE = new EventType<>(CUSTOM_EVENT_TYPE, "PlayerNumberChange");

    private final int param;

    public PlayerNumberChangeEvent(int param) {
        super(PLAYER_NUMBER_CHANGE_EVENT_TYPE);
        this.param = param;
    }

    public int getParam() {
        return param;
    }

    @Override
    public void invokeHandler(CustomEventHandler handler) {
        handler.onPlayerNumberChange(param);
    }
}
