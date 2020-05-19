package it.polimi.ingsw;

import it.polimi.ingsw.server.Server;

import java.io.IOException;

public class ServerApp {

    public static void main(String[] args){
        try {
            Server server = new Server();
            server.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
