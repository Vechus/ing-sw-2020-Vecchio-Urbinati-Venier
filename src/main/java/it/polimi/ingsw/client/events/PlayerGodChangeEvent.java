package it.polimi.ingsw.client.events;

import javafx.event.EventType;

public class PlayerGodChangeEvent extends CustomEvent {
    public static final EventType<CustomEvent> GOD_CHANGE_EVENT_TYPE = new EventType<>(CUSTOM_EVENT_TYPE, "PlayerGodChange");
    private final String param;

    public PlayerGodChangeEvent(String param) {
        super(GOD_CHANGE_EVENT_TYPE);
        this.param = param;
    }

    @Override
    public void invokeHandler(CustomEventHandler handler) {
        handler.onPlayerGodChange(param);
    }
}
