package it.polimi.ingsw.util.listeners;

import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.model.god.God;

/**
 *Listener server side that reacts to the actions made by the players.
 */
public interface PlayerActionListener {
    boolean onPlayerAction(int playerId, Action a);
    void onPlayerCreate(int playerId, String godName, String name);
}
