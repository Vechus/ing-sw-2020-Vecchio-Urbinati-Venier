package it.polimi.ingsw.util.listeners;

import it.polimi.ingsw.model.Model;

public interface ModelChangeListener {
    void onModelChange(Model model);
    void notifyGameOver(String winner);
}
