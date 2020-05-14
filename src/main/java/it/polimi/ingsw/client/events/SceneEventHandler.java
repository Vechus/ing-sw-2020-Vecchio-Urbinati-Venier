package it.polimi.ingsw.client.events;

import javafx.event.EventHandler;

public abstract class SceneEventHandler implements EventHandler<SceneEvent> {
    public abstract void onSceneChange(String sceneName);

    @Override
    public void handle(SceneEvent event) {
        event.invokeHandler(this);
    }
}
