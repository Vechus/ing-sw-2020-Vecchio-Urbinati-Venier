package it.polimi.ingsw.model;

import it.polimi.ingsw.model.god.effect.OpponentEffect;
import it.polimi.ingsw.util.ConsoleColor;
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
     * Get height int.
     *
     * @param pos the pos
     * @return the int.
     */
    public int getHeight(Vector2 pos){
        if(pos.getX() < 0 || pos.getY() < 0) return -1;
        return state[pos.getX()][pos.getY()].getHeight();
    }

    /**
     * Set height.
     *
     * @param pos    the position
     * @param height the height.
     */
    public void setHeight(Vector2 pos, int height){
        state[pos.getX()][pos.getY()].setHeight(height);
    }

    /**
     * Is a complete building boolean. Returns true if the building is complete.
     *
     * @param pos the pos
     * @return the boolean.
     */
    public boolean isComplete(Vector2 pos){
        return state[pos.getX()][pos.getY()].isComplete();
    }

    /**
     * Set complete.
     *
     * @param pos      the pos
     * @param complete the complete.
     */
    public void setComplete(Vector2 pos, boolean complete){
        state[pos.getX()][pos.getY()].setComplete(complete);
    }

    /**
     * Place worker on a specified position.
     *
     * @param w   the w
     * @param pos the pos.
     */
    public boolean placeWorker(Worker w, Vector2 pos){
        if(pos.getX() < 0 || pos.getX() > 4 || pos.getY() < 0 || pos.getY() > 4) return false;
        workers[pos.getX()][pos.getY()] = w;
        w.setPosition(pos);
        return true;
    }

    /**
     * Get worker worker.
     *
     * @param pos the pos
     * @return the worker.
     */
    public Worker getWorker(Vector2 pos){
        return workers[pos.getX()][pos.getY()];
    }

    /**
     * Move worker.
     *
     * @param action the action.
     */
    public void moveWorker(Action action){
        workers[action.getWorkerPos().getX()][action.getWorkerPos().getY()] = null;
        workers[action.getTargetPos().getX()][action.getTargetPos().getY()] = action.getWorker();
        action.getWorker().setPosition(action.getTargetPos());
    }

    /**
     * Add effect to the game.
     *
     * @param e the effect to add.
     */
    public void addEffect(OpponentEffect e){
        effects.add(e);
    }

    /**
     * Set effect active.
     *
     * @param p      the p
     * @param active boolean to set an effect to.
     */
    public void setEffectActive(Player p, boolean active){
        for(OpponentEffect e : this.effects)
            if(e.checkOwner(p)) e.setActive(active);
    }

    /**
     * Is the player's effect active?
     * @param p player whose effect we're checking
     * @return if the effect is active
     */
    public boolean isEffectActive(Player p){
        for(OpponentEffect e : this.effects)
            if(e.checkOwner(p)) return e.isActive();
        return false;
    }

    /**
     * Is action permitted by effects boolean.
     *
     * @param a the a
     * @return the boolean.
     */
    public boolean isActionPermittedByEffects(Action a){
        Player p = a.getWorker().getOwner();
        for(OpponentEffect e : this.effects)
            if(!e.checkOwner(p) && e.isActive() && !e.checkOpponentAction(a)) return false;
        return true;
    }

    /**
     * Is win permitted by effects boolean.
     *
     * @param a the a
     * @return the boolean.
     */
    public boolean isWinPermittedByEffects(Action a){
        Player p = a.getWorker().getOwner();
        for(OpponentEffect e : this.effects)
            if(!e.checkOwner(p) && e.isActive() && !e.checkOpponentWinCondition(a)) return false;
        return true;
    }

    public void print() {
        String block = ConsoleColor.WHITE_BACKGROUND + ConsoleColor.WHITE_BOLD + "█" + ConsoleColor.RESET;
        System.out.println("  " + block.repeat(21));
        for(int i = 0; i < 5; i++) {
            StringBuilder topRow = new StringBuilder("  " + block);
            StringBuilder centralRow = new StringBuilder("  " + block);
            StringBuilder bottomRow = new StringBuilder("  " + block);
            for(int j = 0; j < 5; j++) {
                Vector2 pos = new Vector2(i,j);
                Worker w = getWorker(pos);
                if(w == null && !isComplete(pos)) {
                    topRow.append("   ").append(block);
                    centralRow.append(" ").append(getHeight(pos)).append(" ").append(block);
                    bottomRow.append("   ").append(block);
                } else if(w == null && isComplete(pos) && getHeight(pos) == 3) {
                    topRow.append(ConsoleColor.WHITE_BACKGROUND_BRIGHT).append(ConsoleColor.BLACK_BOLD_BRIGHT).append("╭─╮").append(ConsoleColor.RESET).append(block);
                    centralRow.append(ConsoleColor.WHITE_BACKGROUND_BRIGHT).append(ConsoleColor.BLACK_BOLD_BRIGHT).append("│").append(getHeight(pos)).append("│").append(ConsoleColor.RESET).append(block);
                    bottomRow.append("   ").append(block);
                } else if(w == null && isComplete(pos) && getHeight(pos) != 3){
                    topRow.append(ConsoleColor.WHITE_BACKGROUND_BRIGHT).append(ConsoleColor.BLACK_BOLD_BRIGHT).append("╭─╮").append(ConsoleColor.RESET).append(block);
                    centralRow.append(ConsoleColor.WHITE_BACKGROUND_BRIGHT).append(ConsoleColor.BLACK_BOLD_BRIGHT).append("│").append(getHeight(pos)).append("│").append(ConsoleColor.RESET).append(block);
                    bottomRow.append("   ").append(block);
                } else {
                    topRow.append("   ").append(block);
                    centralRow.append(" ").append(w.getOwner().getPlayerColor()).append(getHeight(pos)).append(ConsoleColor.RESET).append(" ").append(block);
                    bottomRow.append("   ").append(block);
                }
            }
            System.out.println(topRow);
            System.out.println(centralRow);
            System.out.println(bottomRow);
            System.out.println("  " + block.repeat(21));
        }
    }
}
