package it.polimi.ingsw.controller.stage;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Model;

public class MatchController extends GameStageController {
    public MatchController(Model model) {
        super(model);
        this.stage = GameStage.PLAY;
    }

    @Override
    public boolean performAction(int playerId, Action a) {
        return this.model.executeAction(playerId, a);
    }

    @Override
    public GameStageController advance() {
        return new FinishedController(this.model);
    }
}
