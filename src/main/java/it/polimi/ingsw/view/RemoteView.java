package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Model;

public class RemoteView extends View {
    public RemoteView(int playerId) {
        super(playerId);
    }

    @Override
    public void showModel(Model model) {

    }

    /*
     * TODO: Add network code
     */

    @Override
    public void onModelChange(Model model) {
        super.onModelChange(model);
    }
}
