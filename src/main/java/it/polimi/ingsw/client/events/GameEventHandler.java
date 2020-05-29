package it.polimi.ingsw.client.events;

import it.polimi.ingsw.util.ActionType;
import it.polimi.ingsw.util.Vector2;
import javafx.event.EventHandler;

import java.util.List;

public abstract class GameEventHandler implements EventHandler<GameEvent> {
    public abstract void onNewAllowedActions(List<ActionType> allowedActions);
    public abstract void onSelectGrid(Vector2 pos);
    public abstract void onPlaceWorker(Vector2 pos);

    @Override
    public void handle(GameEvent gameEvent) {
        gameEvent.invokeHandler(this);
    }
}
