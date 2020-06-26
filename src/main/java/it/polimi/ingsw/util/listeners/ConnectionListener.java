package it.polimi.ingsw.util.listeners;

import it.polimi.ingsw.connection.Message;

/**
 * Listener server side that reacts to the messages from the client.
 */
public interface ConnectionListener {
    void onMessageReceived(Message message);
}
