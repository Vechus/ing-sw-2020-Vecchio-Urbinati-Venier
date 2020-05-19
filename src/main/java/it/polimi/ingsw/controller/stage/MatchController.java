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
        System.out.println("[CONTROLLER] Action "+a.getType()+" done successfully");
        System.out.print("Has "+model.getPlayer(playerId).getPlayerName()+" won?");
        System.out.println(model.getPlayer(model.getCurPlayer()).checkWinCondition(a));
        if(model.getPlayer(model.getCurPlayer()).checkWinCondition(a) || model.checkGameOver()) {
            System.out.println("[CONTROLLER] It's over");
            model.sendGameOver(playerId);
            stageDone = true;
        }
        else {
            System.out.println("[CONTROLLER] The match goes on");
            if (model.getPlayer(model.getCurPlayer()).isFinished()) {
                System.out.println("[CONTROLLER] Incrementing player");
                model.incrementCurPlayer();
                model.beginNewTurn(model.getCurPlayer());
                System.out.println("[CONTROLLER] New turn has begun!");
            }
            model.sendActionRequest(model.getCurPlayer());
            System.out.println("[CONTROLLER] Sent out the request");
        }
        return true;
    }

    @Override
    public GameStageController advance() {
        return new FinishedController(model);
    }
}
