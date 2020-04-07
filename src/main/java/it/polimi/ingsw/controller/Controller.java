package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.stage.GameStageController;
import it.polimi.ingsw.controller.stage.GodChoiceController;
import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.util.listeners.PlayerActionListener;

import java.util.Observable;
import java.util.Observer;

public class Controller implements PlayerActionListener {
    GameStageController currentStage;
    Model model;

    public Controller(Model model){
        this.model = model;
        this.currentStage = new GodChoiceController(model);
    }

    @Override
    public void onPlayerAction(int playerId, Action a) {
        currentStage.performAction(playerId, a);
    }
}
