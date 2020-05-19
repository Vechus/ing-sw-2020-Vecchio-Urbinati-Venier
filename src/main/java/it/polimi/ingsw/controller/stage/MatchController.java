package it.polimi.ingsw.controller.stage;

import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.model.Model;

public class MatchController extends GameStageController {
    public MatchController(Model model) {
        super(model);
        stage = GameStage.PLAY;
    }

    @Override
    public boolean performAction(int playerId, Action a) {
        if(!model.isPlayersTurn(playerId)) return false;
        if(!model.executeAction(playerId, a)) return false;
        if(model.getPlayer(model.getCurPlayer()).checkWinCondition(a) || model.checkGameOver())
            stageDone = true;
        else {
            if (model.getPlayer(model.getCurPlayer()).isFinished()) {
                model.incrementCurPlayer();
                model.beginNewTurn(model.getCurPlayer());
            }
            model.sendActionRequest(model.getCurPlayer());
        }
        return true;
    }

    @Override
    public GameStageController advance() {
        return new FinishedController(model);
    }
}
