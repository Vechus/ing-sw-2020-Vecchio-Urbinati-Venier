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
        if(!model.isPlayersTurn(playerId)) return false;
        if(a.getType() != Action.ActionType.PLACE_WORKER) return false;
        if(!model.placeWorker(playerId, a.getTargetPos())) return false;
        model.incrementCurPlayer();
        boolean full = true;
        for(int i=0;i<model.getNumberOfPlayers();i++)
            full &= model.getPlayer(i).getNumWorkers() == 2;
        if(full) stageDone = true;
        return true;
    }

    @Override
    public GameStageController advance() {
        return new MatchController(this.model);
    }
}
