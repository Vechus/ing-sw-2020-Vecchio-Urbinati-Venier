package it.polimi.ingsw.client.events;

import it.polimi.ingsw.util.Vector2;
import javafx.event.EventHandler;

/**
 *  The abstract definition of a Game Event Handler
 */
public abstract class GameEventHandler implements EventHandler<GameEvent> {

    /**
     * A cell has been selected on the grid.
     * @param pos the position on the grid
     */
    public abstract void onSelectGrid(Vector2 pos);

    /**
     * A worker has been placed on the grid
     * @param pos the position on the grid
     */
    public abstract void onPlaceWorker(Vector2 pos);

    @Override
    public void handle(GameEvent gameEvent) {
        gameEvent.invokeHandler(this);
    }
}
