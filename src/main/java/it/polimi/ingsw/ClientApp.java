package it.polimi.ingsw;

import it.polimi.ingsw.client.Client;

import java.io.IOException;

public class ClientApp {
    public static void main(String[] args) {
        Client remoteClient = new Client();
        remoteClient.run();
    }
}
