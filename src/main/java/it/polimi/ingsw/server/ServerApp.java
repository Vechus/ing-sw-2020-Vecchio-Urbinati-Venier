package it.polimi.ingsw.server;

import java.io.IOException;

public class ServerApp {
    Server server;

    {
        try {
            server = new Server();
            server.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
