package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.stage.GameStage;
import it.polimi.ingsw.controller.stage.GameStageController;
import it.polimi.ingsw.controller.stage.GodChoiceController;
import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.god.God;
import it.polimi.ingsw.util.listeners.PlayerActionListener;

/**
 *Controller in the MVC model. Listens to players action and processes them.
 */
public class Controller implements PlayerActionListener {
    GameStageController currentStage;
    Model model;
    int totalPlayers;

    public Controller(Model model, int totalPlayers){
        this.model = model;
        this.totalPlayers = totalPlayers;
        this.currentStage = new GodChoiceController(model);
    }

    @Override
    public void onPlayerCreate(int playerId, String godName, String name) {
        if(currentStage.getGameStage() != GameStage.GOD_CHOICE) return;
        currentStage.createPlayer(playerId, godName, name);
        if(model.getNumberOfPlayers() == totalPlayers)
            currentStage.setStageDone(true);
        if(currentStage.isStageDone())
            currentStage = currentStage.advance();
    }

    @Override
    public boolean onPlayerAction(int playerId, Action a) {
        boolean res = currentStage.performAction(playerId, a);
        if(currentStage.isStageDone())
            currentStage = currentStage.advance();
        return res;
    }
}
