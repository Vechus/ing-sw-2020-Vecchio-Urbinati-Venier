package it.polimi.ingsw.server;

import it.polimi.ingsw.util.listeners.ConnectionListener;

public interface ClientConnection{

    void closeConnection();

    void addListener(ConnectionListener listener);

    void asyncSend(Object message);
}