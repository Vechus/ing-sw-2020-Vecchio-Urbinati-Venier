package it.polimi.ingsw.view;

import it.polimi.ingsw.server.ClientConnection;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.god.God;
import it.polimi.ingsw.util.listeners.ConnectionListener;
import it.polimi.ingsw.util.listeners.ModelChangeListener;
import it.polimi.ingsw.util.listeners.PlayerActionListener;

import java.util.ArrayList;
import java.util.List;

/**
 *Represents the model and allows the client to interact with the MVC.
 */
public abstract class View implements ModelChangeListener, ConnectionListener {
    private PlayerActionListener listener;
    int playerId;
    protected ClientConnection clientConnection;
    Model model;
    Server server;

    public View(Server server, Model model, ClientConnection clientConnection, int playerId){
        this.server = server;
        this.model = model;
        this.clientConnection=clientConnection;
        this.playerId = playerId;
    }


    public void addListener(PlayerActionListener listener){ this.listener = listener; }

    /**
     *Notifies the controller that a new player is created
     * @param godName
     * @param name name of the player
     */
    public void processPlayerCreation(String godName, String name){
        listener.onPlayerCreate(playerId, godName, name);
    }

    /**
     *Notifies the controller that an action has been made.
     * @param action
     * @return
     */
    public boolean processAction(Action action){
        return listener.onPlayerAction(playerId, action);
    }

    /**
     *Shows a representation of the model to the client.
     * @param model
     */
    public abstract void showModel(Model model);

    @Override
    public void onModelChange(Model model) {
        showModel(model);
    }
}
