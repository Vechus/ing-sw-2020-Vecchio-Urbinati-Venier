package it.polimi.ingsw.controller.stage;

import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.god.God;

/**
 *
 */
public class GodChoiceController extends GameStageController {
    public GodChoiceController(Model model) {
        super(model);
        this.stage = GameStage.GOD_CHOICE;
    }

    @Override
    public boolean createPlayer(int playerId, String godName, String name) {
        if(model.getNumberOfPlayers() != playerId) return false;
        model.addNewPlayer(godName, name);
        return true;
    }

    @Override
    public boolean performAction(int playerId, Action a) {
        return false;
    }

    @Override
    public GameStageController advance() {
        model.updateClientModel();
        return new SetupController(model);
    }
}
