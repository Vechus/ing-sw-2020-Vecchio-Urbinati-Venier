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
            client.run(menu());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private static boolean menu() {
        Scanner input = new Scanner(System.in);

        System.out.println("SANTORINI OFFICIAL GAME SPONSORED BY Java 13©");
        String ans = input.next();
        System.out.println("Vuoi aprire una nuova partita?[si/no] ");
        while(!ans.equals("si") && !ans.equals("no")) {
            ans = input.next();
            System.out.println("Errore input! Vuoi aprire una nuova partita?[si/no] ");
        }
        input.close();
        return ans.equals("si");
    }
}
