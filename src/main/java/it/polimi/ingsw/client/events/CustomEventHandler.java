package it.polimi.ingsw.client.events;

import it.polimi.ingsw.client.ClientAction;
import javafx.event.EventHandler;

public abstract class CustomEventHandler implements EventHandler<CustomEvent> {
    public abstract void onSceneChange(String sceneName);
    public abstract void onPlayerNameChange(String playerName);
    public abstract void onPlayerAction(ClientAction action);

    @Override
    public void handle(CustomEvent event) {
        event.invokeHandler(this);
    }
}
