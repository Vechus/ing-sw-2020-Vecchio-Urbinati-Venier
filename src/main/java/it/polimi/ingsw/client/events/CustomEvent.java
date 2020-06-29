package it.polimi.ingsw.client.events;

import javafx.event.Event;
import javafx.event.EventType;

/**
 *
 */
public abstract class CustomEvent extends Event {
    public static final EventType<CustomEvent> CUSTOM_EVENT_TYPE = new EventType<>(ANY);

    public CustomEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }

    /**
     *
     * @param handler
     */
    public abstract void invokeHandler(CustomEventHandler handler);
}
