package it.polimi.ingsw.controller.stage;

import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.god.God;

/**
 *
 */
public abstract class GameStageController {
    protected Model model;
    protected GameStage stage;
    protected boolean stageDone = false;

    public GameStageController(Model model){
        this.model = model;
    }

    /**
     *
     * @return
     */
    public boolean isStageDone(){ return this.stageDone; }

    /**
     *
     * @return
     */
    public GameStage getGameStage(){ return this.stage; }

    /**
     *
     * @param playerId
     * @param godName
     * @param name
     * @return
     */
    public boolean createPlayer(int playerId, String godName, String name){ return false; }

    /**
     *
     * @param playerId
     * @param a
     * @return
     */
    public abstract boolean performAction(int playerId, Action a);

    /**
     *
     * @return
     */
    public abstract GameStageController advance();

    /**
     *
     * @param done
     */
    public void setStageDone(boolean done){ stageDone = done; }
}
