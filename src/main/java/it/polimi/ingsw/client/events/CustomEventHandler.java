package it.polimi.ingsw.client.events;

import javafx.event.EventHandler;

public abstract class CustomEventHandler implements EventHandler<CustomEvent> {
    public abstract void onSceneChange(String sceneName);
    public abstract void onPlayerNameChange(String playerName);

    @Override
    public void handle(CustomEvent event) {
        event.invokeHandler(this);
    }
}
