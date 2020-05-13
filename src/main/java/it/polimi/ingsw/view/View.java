package it.polimi.ingsw.view;

import it.polimi.ingsw.server.ClientConnection;
import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.god.God;
import it.polimi.ingsw.util.listeners.ModelChangeListener;
import it.polimi.ingsw.util.listeners.PlayerActionListener;

import java.util.List;

public abstract class View implements ModelChangeListener {
    private List<PlayerActionListener> listeners;
    int playerId;
    private ClientConnection clientConnection;

    public View(ClientConnection clientConnection){
        this.clientConnection=clientConnection;
    }

    public void addListener(PlayerActionListener listener){ this.listeners.add(listener); }

    public void processPlayerCreation(God god, String name){
        for(PlayerActionListener listener : this.listeners){
            listener.onPlayerCreate(this.playerId, god, name);
        }
    }

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
