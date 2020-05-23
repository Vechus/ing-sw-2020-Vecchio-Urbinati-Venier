package it.polimi.ingsw.client.events;

import it.polimi.ingsw.client.ClientAction;
import javafx.event.EventHandler;
import javafx.util.Pair;

public abstract class CustomEventHandler implements EventHandler<CustomEvent> {
    public abstract void onSceneChange(String sceneName);
    public abstract void onPlayerNameChange(String playerName);
    public abstract void onPlayerAction(ClientAction action);
    public abstract void onPlayerIpPortChange(Pair<String, Integer> input);
    public abstract void onPlayerNumberChange(int num);

    @Override
    public void handle(CustomEvent event) {
        event.invokeHandler(this);
    }
}
