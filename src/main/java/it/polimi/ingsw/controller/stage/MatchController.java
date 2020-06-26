package it.polimi.ingsw.controller.stage;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.util.ActionType;
import it.polimi.ingsw.util.Vector2;

/**
 *
 */
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
        if(model.checkGameOver()){
            System.out.println("Nobody won!");
            model.sendGameOver(-1);
            stageDone = true;
        }
        for(int pid = 0;pid<model.getNumberOfPlayers();pid++) {
            Player player = model.getPlayer(pid);
            if(player.isSpectator()) continue;
            if ((pid == model.getCurPlayer() && player.checkWinCondition(a))
                || player.checkWinCondition(new Action(player.getWorker(0), new Vector2(0, 0), ActionType.END_TURN))) {
                System.out.println("[CONTROLLER] Game over!");
                model.sendGameOver(pid);
                stageDone = true;
                break;
            }
        }
        if(!stageDone) {
            if (model.getPlayer(model.getCurPlayer()).isFinished()) {
                System.out.println("Passing turn...");
                model.incrementCurPlayer();
                model.beginNewTurn(model.getCurPlayer());
            }
        }
        model.updateClientModel();
        return true;
    }

    @Override
    public GameStageController advance() {
        return new FinishedController(model);
    }
}
