package it.polimi.ingsw.controller.stage;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Model;

public abstract class GameStageController {
    protected Model model;
    protected GameStage stage;
    protected boolean stageDone = false;

    public GameStageController(Model model){
        this.model = model;
    }

    public boolean isStageDone(){ return this.stageDone; }

    public GameStage getGameStage(){ return this.stage; }

    public abstract boolean performAction(int playerId, Action a);

    public abstract GameStageController advance();
}
