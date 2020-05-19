package it.polimi.ingsw.util.listeners;

import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.util.ActionType;

import java.util.List;

public interface ModelChangeListener {
    void onModelChange(Model model);
    void notifyActionRequired(List<ActionType> allowed);
    void notifyGameOver(String winner);
}
