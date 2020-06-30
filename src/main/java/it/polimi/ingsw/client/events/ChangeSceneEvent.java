package it.polimi.ingsw.client.events;

import javafx.event.EventType;

/**
 * The Change scene event.
 */
public class ChangeSceneEvent extends CustomEvent {
    public static final EventType<CustomEvent> SCENE_CHANGE_EVENT_TYPE = new EventType<>(CUSTOM_EVENT_TYPE, "SceneChange");
    private final String param;

    /**
     * Instantiates a new Change scene event.
     *
     * @param param the scene name
     */
    public ChangeSceneEvent(String param) {
        super(SCENE_CHANGE_EVENT_TYPE);
        this.param = param;
    }

    @Override
    public void invokeHandler(CustomEventHandler handler) {
        handler.onSceneChange(param);
    }
}
