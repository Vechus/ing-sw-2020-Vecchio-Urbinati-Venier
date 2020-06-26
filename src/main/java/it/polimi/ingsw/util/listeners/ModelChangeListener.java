package it.polimi.ingsw.util.listeners;

import it.polimi.ingsw.model.Model;

/**
 * Listener server side that reacts to the changes to the model. Specifically, the board state.
 */
public interface ModelChangeListener {
    void onModelChange(Model model);
    void notifyGameOver(String winner);
}
