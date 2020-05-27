package it.polimi.ingsw;

import it.polimi.ingsw.client.Client;

import java.io.IOException;

public class ClientApp {
    public static void main(String[] args) {
        if(args.length > 1){
            System.err.println("USAGE: java ClientApp.jar [-cli]");
            return;
        }
        boolean useCli = false;
        for(String arg : args)
            if(arg.equals("-cli")) useCli = true;

        Client remoteClient = new Client(useCli);
        remoteClient.run();
    }
}
