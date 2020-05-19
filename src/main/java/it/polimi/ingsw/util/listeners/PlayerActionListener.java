package it.polimi.ingsw.util.listeners;

import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.model.god.God;

public interface PlayerActionListener {
    boolean onPlayerAction(int playerId, Action a);
    void onPlayerCreate(int playerId, String godName, String name);
}
