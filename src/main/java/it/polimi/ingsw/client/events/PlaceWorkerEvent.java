package it.polimi.ingsw.client.events;

import it.polimi.ingsw.util.Vector2;
import javafx.event.EventType;

/**
 * The Place worker event concrete definition.
 */
public class PlaceWorkerEvent extends GameEvent {
    public static final EventType<GameEvent> PLACE_WORKER_EVENT = new EventType<>(GAME_EVENT_TYPE, "PlaceWorkerEvent");

    private final Vector2 pos;

    public PlaceWorkerEvent(Vector2 pos) {
        super(PLACE_WORKER_EVENT);
        this.pos = pos;
    }

    @Override
    public void invokeHandler(GameEventHandler handler) {
        handler.onPlaceWorker(pos);
    }
}
