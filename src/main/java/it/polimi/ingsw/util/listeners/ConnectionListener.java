package it.polimi.ingsw.util.listeners;

import it.polimi.ingsw.connection.Message;

public interface ConnectionListener {
    void onMessageReceived(Message message);
}
