package it.polimi.ingsw.client.events;

import javafx.event.Event;
import javafx.event.EventType;

public abstract class SceneEvent extends Event {
    public static final EventType<SceneEvent> SCENE_EVENT_TYPE = new EventType<>(ANY);

    public SceneEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }

    public abstract void invokeHandler(SceneEventHandler handler);
}
