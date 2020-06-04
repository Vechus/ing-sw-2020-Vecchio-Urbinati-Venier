package it.polimi.ingsw.client.events;

import javafx.event.Event;
import javafx.event.EventType;

public abstract class GameEvent extends Event {
    public static final EventType<GameEvent> GAME_EVENT_TYPE = new EventType<>(ANY, "GAME_EVENT_TYPE");

    public GameEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }

    public abstract void invokeHandler(GameEventHandler handler);
}
