package it.polimi.ingsw.controller.stage;

import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.util.ActionType;

/**
 *Specific controller of the part of the game where the players set up the their workers before the beginning of the match.
 */
public class SetupController extends GameStageController {
    public SetupController(Model model) {
        super(model);
        this.stage = GameStage.SETUP;
    }

    @Override
    public boolean performAction(int playerId, Action a) {
        if(!model.isPlayersTurn(playerId)) return false;
        if(a.getType() != ActionType.PLACE_WORKER) return false;
        if(!model.placeWorker(playerId, a.getTargetPos())) return false;
        if(model.getPlayer(model.getCurPlayer()).getNumWorkers() == 2)
            model.incrementCurPlayer();
        model.updateClientModel();
        if(model.getPlayer(model.getCurPlayer()).getNumWorkers() == 2)
            stageDone = true;
        return true;
    }

    @Override
    public GameStageController advance() {
        return new MatchController(this.model);
    }
}
