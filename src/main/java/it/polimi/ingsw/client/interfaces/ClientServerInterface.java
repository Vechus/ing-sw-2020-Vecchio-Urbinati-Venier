package it.polimi.ingsw.client.interfaces;

import it.polimi.ingsw.connection.Message;

import java.io.IOException;

/**
 * Class that defines which methods the server uses to send and receive from the clients.
 */
public interface ClientServerInterface {

    /**
     * Method that gets the messages from the clients.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    Message receiveMessage() throws IOException, ClassNotFoundException;

    /**
     *Methos that sends the messages to the clients.
     * @param message
     */
    void sendMessage(Message message);
}
