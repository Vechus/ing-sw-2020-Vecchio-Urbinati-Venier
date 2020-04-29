package it.polimi.ingsw;

import it.polimi.ingsw.client.Client;

import java.io.IOException;
import java.util.Scanner;

public class ClientApp {
    private static final String ip = "127.0.0.1";
    private static final int port = 27000;
    public static void main(String[] args) {
        Client client = new Client(ip, port);

        try {
            client.run();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
