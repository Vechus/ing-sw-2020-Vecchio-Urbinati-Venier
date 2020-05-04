package it.polimi.ingsw.controller.stage;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.god.God;

public class GodChoiceController extends GameStageController {
    public GodChoiceController(Model model) {
        super(model);
        this.stage = GameStage.GOD_CHOICE;
    }

    @Override
    public boolean createPlayer(int playerId, God god) {
        if(model.getNumberOfPlayers() != playerId) return false;
        model.addNewPlayer(god);
        return true;
    }

    @Override
    public boolean performAction(int playerId, Action a) {
        return false;
    }

    @Override
    public GameStageController advance() {
        return new SetupController(this.model);
    }
}
