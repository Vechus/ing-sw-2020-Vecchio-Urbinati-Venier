package it.polimi.ingsw.client.events;

import it.polimi.ingsw.util.ActionType;
import javafx.event.EventHandler;

import java.util.List;

public abstract class GameEventHandler implements EventHandler<GameEvent> {
    public abstract void onNewAllowedActions(List<ActionType> allowedActions);

    @Override
    public void handle(GameEvent gameEvent) {
        gameEvent.invokeHandler(this);
    }
}
