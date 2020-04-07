package it.polimi.ingsw.model;

import it.polimi.ingsw.model.god.effect.OpponentEffect;
import it.polimi.ingsw.util.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private Building[][] state = new Building[5][5];
    private Worker[][] workers = new Worker[5][5];
    private List<OpponentEffect> effects = new ArrayList<OpponentEffect>();

    public Board(){
        for(int i=0;i<5;i++)
            for(int j=0;j<5;j++)
                state[i][j] = new Building();
        for(int i=0;i<5;i++)
            for(int j=0;j<5;j++)
                workers[i][j] = null;
    }

    public int getHeight(Vector2 pos){
        return state[pos.getX()][pos.getY()].getHeight();
    }

    public void setHeight(Vector2 pos, int height){
        state[pos.getX()][pos.getY()].setHeight(height);
    }

    public boolean isComplete(Vector2 pos){
        return state[pos.getX()][pos.getY()].isComplete();
    }

    public void setComplete(Vector2 pos, boolean complete){
        state[pos.getX()][pos.getY()].setComplete(complete);
    }

    public void placeWorker(Worker w, Vector2 pos){
        workers[pos.getX()][pos.getY()] = w;
    }

    public Worker getWorker(Vector2 pos){
        return workers[pos.getX()][pos.getY()];
    }

    public void moveWorker(Action action){
        workers[action.getWorkerPos().getX()][action.getWorkerPos().getY()] = null;
        workers[action.getTargetPos().getX()][action.getTargetPos().getY()] = action.getWorker();
    }

    public void addEffect(OpponentEffect e){
        effects.add(e);
    }

    public void setEffectActive(Player p, boolean active){
        for(OpponentEffect e : this.effects)
            if(e.checkOwner(p)) e.setActive(active);
    }

    public boolean isActionPermittedByEffects(Action a){
        Player p = a.getWorker().getOwner();
        for(OpponentEffect e : this.effects)
            if(!e.checkOwner(p) && e.isActive() && !e.checkOpponentAction(a)) return false;
        return true;
    }

    public boolean isWinPermittedByEffects(Action a){
        Player p = a.getWorker().getOwner();
        for(OpponentEffect e : this.effects)
            if(!e.checkOwner(p) && e.isActive() && !e.checkOpponentWinCondition(a)) return false;
        return true;
    }
}
