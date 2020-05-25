package it.polimi.ingsw.client.events;

import javafx.event.EventType;

import java.util.List;

public class HostSelectGodsEvent extends CustomEvent {
    public static final EventType<CustomEvent> HOST_SELECT_EVENT_TYPE = new EventType<>(CUSTOM_EVENT_TYPE, "HostSelectGods");
    private final List<String> gods;

    public HostSelectGodsEvent(List<String> gods) {
        super(HOST_SELECT_EVENT_TYPE);
        this.gods = gods;
    }

    @Override
    public void invokeHandler(CustomEventHandler handler) {
        handler.onHostSelectGods(gods);
    }
}
