package it.polimi.ingsw.client.interfaces;

import it.polimi.ingsw.connection.Message;

import java.io.IOException;

public interface ClientServerInterface {
    Message handshake(String playerName) throws IOException, ClassNotFoundException;
    Message receiveMessage() throws IOException, ClassNotFoundException;
    void sendMessage(Message message);
}
