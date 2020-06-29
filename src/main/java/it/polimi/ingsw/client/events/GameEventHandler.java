package it.polimi.ingsw.client.events;

import it.polimi.ingsw.util.Vector2;
import javafx.event.EventHandler;

/**
 *
 */
public abstract class GameEventHandler implements EventHandler<GameEvent> {

    /**
     *
     * @param pos
     */
    public abstract void onSelectGrid(Vector2 pos);

    /**
     *
     * @param pos
     */
    public abstract void onPlaceWorker(Vector2 pos);

    @Override
    public void handle(GameEvent gameEvent) {
        gameEvent.invokeHandler(this);
    }
}
