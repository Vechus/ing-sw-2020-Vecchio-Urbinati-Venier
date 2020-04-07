package it.polimi.ingsw.view;

import it.polimi.ingsw.util.listeners.PlayerActionListener;

import java.util.List;

public class View {
    private List<PlayerActionListener> listeners;

    public View(){

    }

    public void addListener(PlayerActionListener listener){ listeners.add(listener); }

    /*
     * TODO: Add network code
     */
}
