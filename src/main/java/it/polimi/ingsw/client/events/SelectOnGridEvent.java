package it.polimi.ingsw.client.events;

import it.polimi.ingsw.util.Vector2;
import javafx.event.EventType;

public class SelectOnGridEvent extends GameEvent {
    public static final EventType<GameEvent> SELECT_ON_GRID_EVENT = new EventType<>(GAME_EVENT_TYPE, "SelectOnGridEvent");

    private final Vector2 pos;

    public SelectOnGridEvent(Vector2 pos) {
        super(SELECT_ON_GRID_EVENT);
        this.pos = pos;
    }

    @Override
    public void invokeHandler(GameEventHandler handler) {
        handler.onSelectGrid(pos);
    }
}
