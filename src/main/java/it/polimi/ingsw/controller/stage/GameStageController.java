package it.polimi.ingsw.controller.stage;

import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.god.God;

public abstract class GameStageController {
    protected Model model;
    protected GameStage stage;
    protected boolean stageDone = false;

    public GameStageController(Model model){
        this.model = model;
    }

    public boolean isStageDone(){ return this.stageDone; }

    public GameStage getGameStage(){ return this.stage; }

    public boolean createPlayer(int playerId, God god, String name){ return false; }

    public abstract boolean performAction(int playerId, Action a);

    public abstract GameStageController advance();

    public void setStageDone(boolean done){ stageDone = done; }
}
