package it.polimi.ingsw.model;

import it.polimi.ingsw.model.god.effect.OpponentEffect;
import it.polimi.ingsw.util.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private Building[][] state = new Building[5][5];
    private List<OpponentEffect> effects = new ArrayList<OpponentEffect>();

    public Board(){
        for(int i=0;i<5;i++)
            for(int j=0;j<5;j++)
                state[i][j] = new Building();
    }

    //TODO: possible bad practice to expose reference to internal state object
    public Building getCell(Vector2 pos){
        return state[pos.x][pos.y];
    }

    /*
    * TODO: Discuss about setCell
    */

    /*
     * TODO: discuss getWorker
     */

    public void addEffect(OpponentEffect e){
        effects.add(e);
    }

    public void setEffectActive(Player p, boolean active){
        for(OpponentEffect e : effects)
            if(e.checkOwner(p)) e.setActive(active);
    }
}
