package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.server.ClientConnection;

public class RemoteView extends View {


    public RemoteView(ClientConnection clientConnection) {
        super(clientConnection);
    }

    @Override
    public void showModel(Model model) {

    }

    @Override
    public void onModelChange(Model model) {
        super.onModelChange(model);
    }
}
