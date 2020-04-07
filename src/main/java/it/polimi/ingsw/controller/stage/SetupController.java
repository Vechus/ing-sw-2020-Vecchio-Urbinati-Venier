package it.polimi.ingsw.controller.stage;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Model;

public class SetupController extends GameStageController {
    public SetupController(Model model) {
        super(model);
        this.stage = GameStage.SETUP;
    }

    @Override
    public boolean performAction(int playerId, Action a) {
        return false;
    }

    @Override
    public GameStageController advance() {
        return new MatchController(this.model);
    }
}
