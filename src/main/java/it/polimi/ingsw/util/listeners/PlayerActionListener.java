package it.polimi.ingsw.util.listeners;

import it.polimi.ingsw.model.Action;

public interface PlayerActionListener {
    void onPlayerAction(int playerId, Action a);
}
