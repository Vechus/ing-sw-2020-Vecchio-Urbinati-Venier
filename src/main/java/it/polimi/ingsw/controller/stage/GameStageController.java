package it.polimi.ingsw.controller.stage;

import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.god.God;

/**
 *Controller of the various stages of the game, which are: Setup, GodChoice, Match and Finished.
 */
public abstract class GameStageController {
    protected Model model;
    protected GameStage stage;
    protected boolean stageDone = false;

    public GameStageController(Model model){
        this.model = model;
    }


    public boolean isStageDone(){ return this.stageDone; }



    public GameStage getGameStage(){ return this.stage; }

    /**
     *Creates the player with his name, id and god.
     * @param playerId Id of the player
     * @param godName Name of the chosen god
     * @param name Name of the player
     * @return
     */
    public boolean createPlayer(int playerId, String godName, String name){ return false; }

    /**
     *Given the action from the client it handles what the model should do
     * @param playerId Id of the player that performs the action
     * @param a chosen action
     * @return true if the action is completed successfully
     */
    public abstract boolean performAction(int playerId, Action a);

    /**
     *Makes the game move to the next stage.
     * @return the next stage
     */
    public abstract GameStageController advance();

    /**
     * Sets the stage as done when the game must move to another stage
     * @param done
     */
    public void setStageDone(boolean done){ stageDone = done; }
}
