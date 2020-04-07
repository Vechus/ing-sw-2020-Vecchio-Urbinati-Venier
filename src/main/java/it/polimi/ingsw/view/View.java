package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.util.listeners.ModelChangeListener;
import it.polimi.ingsw.util.listeners.PlayerActionListener;

import java.util.List;

public abstract class View implements ModelChangeListener {
    private List<PlayerActionListener> listeners;
    int playerId;

    public View(int playerId){
        this.playerId = playerId;
    }

    public void addListener(PlayerActionListener listener){ this.listeners.add(listener); }

    public void processAction(Action action){
        for(PlayerActionListener listener : this.listeners){
            listener.onPlayerAction(this.playerId, action);
        }
    }

    public abstract void showModel(Model model);

    @Override
    public void onModelChange(Model model) {
        showModel(model);
    }
}
