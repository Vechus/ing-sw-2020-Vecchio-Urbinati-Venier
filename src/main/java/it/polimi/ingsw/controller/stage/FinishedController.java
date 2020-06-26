package it.polimi.ingsw.controller.stage;

import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.model.Model;

/**
 *Specific controller of the part of the game where the game has ended.
 */
public class FinishedController extends GameStageController {
    public FinishedController(Model model) {
        super(model);
        this.stage = GameStage.ENDED;
    }

    @Override
    public boolean performAction(int playerId, Action a) {
        return false;
    }

    @Override
    public GameStageController advance() {
        return this;
    }
}
