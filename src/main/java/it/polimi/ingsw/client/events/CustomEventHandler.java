package it.polimi.ingsw.client.events;

import it.polimi.ingsw.client.ClientAction;
import javafx.event.EventHandler;
import javafx.util.Pair;

import java.util.List;

/**
 * The Custom Event Handler abstract definition.
 */
public abstract class CustomEventHandler implements EventHandler<CustomEvent> {
    /**
     * On scene change.
     *
     * @param sceneName the scene name
     */
    public abstract void onSceneChange(String sceneName);

    /**
     * On player name change.
     *
     * @param playerName the player name
     */
    public abstract void onPlayerNameChange(String playerName);

    /**
     * On player action.
     *
     * @param action the action
     */
    public abstract void onPlayerAction(ClientAction action);

    /**
     * On player ip and port change.
     *
     * @param input the Pair object containing IP address and port
     */
    public abstract void onPlayerIpPortChange(Pair<String, Integer> input);

    /**
     * On player number change.
     *
     * @param num the number of players in the game
     */
    public abstract void onPlayerNumberChange(int num);

    /**
     * On player god change.
     *
     * @param god the god name
     */
    public abstract void onPlayerGodChange(String god);

    /**
     * On host select gods.
     *
     * @param gods the selected gods
     */
    public abstract void onHostSelectGods(List<String> gods);

    @Override
    public void handle(CustomEvent event) {
        event.invokeHandler(this);
    }
}
