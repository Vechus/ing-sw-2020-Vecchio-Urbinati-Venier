package it.polimi.ingsw.client.interfaces;

import it.polimi.ingsw.connection.Message;

import java.io.IOException;

public interface ClientServerInterface {
    Message receiveMessage() throws IOException, ClassNotFoundException;
    void sendMessage(Message message);
}
