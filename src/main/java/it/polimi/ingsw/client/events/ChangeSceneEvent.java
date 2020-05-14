package it.polimi.ingsw.client.events;

import javafx.event.Event;
import javafx.event.EventType;

public class ChangeSceneEvent extends SceneEvent {
    public static final EventType<SceneEvent> SCENE_CHANGE_EVENT_TYPE = new EventType<>(SCENE_EVENT_TYPE, "SceneChange");
    private final String param;

    public ChangeSceneEvent(String param) {
        super(SCENE_CHANGE_EVENT_TYPE);
        this.param = param;
    }

    @Override
    public void invokeHandler(SceneEventHandler handler) {
        handler.onSceneChange(param);
    }
}
