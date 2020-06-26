package it.polimi.ingsw.server;

import it.polimi.ingsw.util.listeners.ConnectionListener;


/**
 *  Handles the connection between the client and the server
 */
public interface ClientConnection{

    /**
     * Closes the connection between the server and the client.
     */
    void closeConnection();

    void addListener(ConnectionListener listener);

    /**
     * Sends the message to the client asynchronously
     *
     * @param message the object to send
     */
    void asyncSend(Object message);

    /**
     * Sends the message to the client.
     * @param message
     */
    void send(Object message);
}