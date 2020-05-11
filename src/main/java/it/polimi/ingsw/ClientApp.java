package it.polimi.ingsw;

import it.polimi.ingsw.client.RemoteClient;

import java.io.IOException;

public class ClientApp {
    private static final String ip = "127.0.0.1";
    private static final int port = 27000;
    public static void main(String[] args) {
        RemoteClient remoteClient = new RemoteClient(ip, port);

        try {
            remoteClient.run();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
