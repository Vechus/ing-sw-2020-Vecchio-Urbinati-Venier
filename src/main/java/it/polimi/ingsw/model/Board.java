package it.polimi.ingsw.model;

import it.polimi.ingsw.model.god.effect.OpponentEffect;
import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.util.ActionType;
import it.polimi.ingsw.util.Vector2;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Board. It is by game rule a 5x5 grid.
 */
public class Board {
    private Building[][] state = new Building[5][5];
    private Worker[][] workers = new Worker[5][5];
    private List<OpponentEffect> effects = new ArrayList<>();

    /**
     * Instantiates a new Board. (5x5 empty building grid)
     */
    public Board(){
        for(int i=0;i<5;i++)
            for(int j=0;j<5;j++)
                state[i][j] = new Building();
        for(int i=0;i<5;i++)
            for(int j=0;j<5;j++)
                workers[i][j] = null;
    }

    /**
     * Getter of height.
     *
     * @param pos : the position of building
     * @return int : the height of the building
     */
    public int getHeight(Vector2 pos){
        if(pos.getX() < 0 || pos.getY() < 0) return -1;
        return state[pos.getX()][pos.getY()].getHeight();
    }

    /**
     * Setter of height.
     *
     * @param pos : the position of the building
     * @param height : the height you wanna give to the building
     */
    public void setHeight(Vector2 pos, int height){
        state[pos.getX()][pos.getY()].setHeight(height);
    }

    /**
     * Returns true if the building is complete.
     *
     * @param pos: the position of the building
     * @return boolean
     */
    public boolean isComplete(Vector2 pos){
        return state[pos.getX()][pos.getY()].isComplete();
    }

    /**
     * Setter of complete.
     *
     * @param pos : the position of the building
     * @param complete : complete is true if the building has a dome.
     */
    public void setComplete(Vector2 pos, boolean complete){
        state[pos.getX()][pos.getY()].setComplete(complete);
    }

    /**
     * Place worker on a specified position.
     *
     * @param w  : worker to be placed
     * @param pos : the position where the the worker is placed
     */
    public boolean placeWorker(Worker w, Vector2 pos){
        if(pos.getX() < 0 || pos.getX() > 4 || pos.getY() < 0 || pos.getY() > 4) return false;
        workers[pos.getX()][pos.getY()] = w;
        w.setPosition(pos);
        return true;
    }

    /**
     * Getter of worker.
     *
     * @param pos : the position from which you wanna take the worker from
     * @return Worker.
     */
    public Worker getWorker(Vector2 pos){
        return workers[pos.getX()][pos.getY()];
    }

    /**
     * Moves the worker.
     *
     * @param action : the action containing the move
     */
    public void moveWorker(Action action){
        workers[action.getWorkerPos().getX()][action.getWorkerPos().getY()] = null;
        workers[action.getTargetPos().getX()][action.getTargetPos().getY()] = action.getWorker();
        action.getWorker().setPosition(action.getTargetPos());
    }

    /**
     * Adds effect to the game.
     *
     * @param e : the effect to add.
     */
    public void addEffect(OpponentEffect e){
        effects.add(e);
    }

    /**
     * Sets effect active.
     *
     * @param p : the player to which the effect belong
     * @param active boolean to set an effect to.
     */
    public void setEffectActive(Player p, boolean active){
        for(OpponentEffect e : this.effects)
            if(e.checkOwner(p)) e.setActive(active);
    }

    /**
     * Checks if the player's effect is active
     * @param p : player whose effect we're checking
     * @return true if the effect is active
     */
    public boolean isEffectActive(Player p){
        for(OpponentEffect e : this.effects)
            if(e.checkOwner(p)) return e.isActive();
        return false;
    }

    /**
     * Checks if the action is permitted by effects.
     *
     * @param a : the action checked
     * @return boolean
     */
    public boolean isActionPermittedByEffects(Action a){
        Player p = a.getWorker().getOwner();
        for(OpponentEffect e : this.effects)
            if(!e.checkOwner(p) && e.isActive() && !e.checkOpponentAction(a)) return false;
        return true;
    }

    /**
     * Checks if win is permitted by effects.
     *
     * @param a the action checked.
     * @return boolean
     */
    public boolean isWinPermittedByEffects(Action a){
        if(a.getType() == ActionType.END_TURN) return true;
        Player p = a.getWorker().getOwner();
        for(OpponentEffect e : this.effects)
            if(!e.checkOwner(p) && e.isActive() && !e.checkOpponentWinCondition(a)) return false;
        return true;
    }
}
