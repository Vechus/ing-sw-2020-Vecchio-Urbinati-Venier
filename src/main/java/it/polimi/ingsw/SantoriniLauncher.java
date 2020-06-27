package it.polimi.ingsw;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.server.Server;

import java.io.IOException;

/**
 * Launches either or one of the clients depending on the command line arguments.
 */
public class SantoriniLauncher {
    public static void main(String[] args) {
        if(args.length > 1){
            System.err.println("USAGE: java ClientApp.jar [--cli | --server]");
            return;
        }
        boolean useCli = false;
        boolean useServer = false;
        for(String arg : args)
            if(arg.equals("--cli")) useCli = true;
            else if(arg.equals("--server")) useServer = true;
            else System.err.println("Unrecognized arg: "+arg);

        if(useServer){
            try {
                Server server = new Server();
                server.run();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            Client remoteClient = new Client(useCli);
            remoteClient.run();
        }
    }
}
