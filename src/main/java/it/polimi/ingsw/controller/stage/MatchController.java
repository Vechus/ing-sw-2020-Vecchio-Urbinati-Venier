package it.polimi.ingsw.controller.stage;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.util.ActionType;
import it.polimi.ingsw.util.Vector2;

/**
 *Specific controller of the part of the game where the players face each other
 */
public class MatchController extends GameStageController {
    public MatchController(Model model) {
        super(model);
        stage = GameStage.PLAY;
    }

    @Override
    public boolean performAction(int playerId, Action a) {
        // validity check
        if(!model.isPlayersTurn(playerId)) return false;
        // perform action
        if(!model.executeAction(playerId, a)) return false;
        System.out.println("[CONTROLLER] Action "+a.getType()+" done successfully");
        // reset counter at the end of turn
        if (model.getPlayer(model.getCurPlayer()).isFinished())
            model.beginNewTurn(model.getCurPlayer());
        // is everyone immobilized?
        boolean tie = model.checkGameOver();
        // update current player
        if (model.getPlayer(model.getCurPlayer()).isFinished() || model.getPlayer(model.getCurPlayer()).isSpectator()) {
            System.out.println("Passing turn...");
            model.incrementCurPlayer();
            System.out.println("Current player is "+model.getPlayer(model.getCurPlayer()).getPlayerName());
        }
        // send model to clients
        model.updateClientModel();
        // notify if game is over
        if(tie){
            System.out.println("Nobody won!");
            model.sendGameOver(-1);
            stageDone = true;
        }
        else {
            for (int pid = 0; pid < model.getNumberOfPlayers(); pid++) {
                Player player = model.getPlayer(pid);
                if (player.isSpectator()) continue;
                if ((pid == model.getCurPlayer() && player.checkWinCondition(a))
                        || player.checkWinCondition(new Action(player.getWorker(0), new Vector2(0, 0), ActionType.END_TURN))) {
                    System.out.println("[CONTROLLER] Game over!");
                    model.sendGameOver(pid);
                    stageDone = true;
                    break;
                }
            }
        }
        return true;
    }

    @Override
    public GameStageController advance() {
        return new FinishedController(model);
    }
}
