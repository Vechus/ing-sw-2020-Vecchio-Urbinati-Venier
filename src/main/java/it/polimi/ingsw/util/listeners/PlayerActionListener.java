package it.polimi.ingsw.util.listeners;

import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.model.god.God;

public interface PlayerActionListener {
    void onPlayerAction(int playerId, Action a);
    void onPlayerCreate(int playerId, God god);
}
