package it.polimi.ingsw.model.god;

import it.polimi.ingsw.util.ActionType;
import org.testng.internal.collections.Pair;

import java.util.ArrayList;
import java.util.List;

public class TurnActionGraph {
    public final int INITIAL_STATE_IDX = 0;
    public final int FINAL_STATE_IDX = 1;
    private int numStates;
    private List<List<Pair<Integer, ActionType>>> graph;

    public TurnActionGraph(){
        numStates = 2;
        graph = new ArrayList<>();
        graph.add(new ArrayList<>());
        graph.add(new ArrayList<>());
    }

    public List<ActionType> allowedActions(int stateIdx){
        List<ActionType> allowed = new ArrayList<>();
        for(Pair<Integer, ActionType> edge : graph.get(stateIdx))
            allowed.add(edge.second());
        return allowed;
    }

    public int addState() {
        int n = numStates++;
        graph.add(new ArrayList<>());
        return n;
    }

    public void addTransition(int idxFrom, int idxTo, ActionType type){
        if(idxFrom < 0 || idxFrom >= numStates || idxTo < 0 || idxTo >= numStates)
            return;
        if(idxFrom == FINAL_STATE_IDX || idxTo == INITIAL_STATE_IDX)
            return;
        if(idxTo == FINAL_STATE_IDX && !type.equals(ActionType.END_TURN))
            return;
        if(allowedActions(idxFrom).contains(type))
            return;
        graph.get(idxFrom).add(new Pair<>(idxTo, type));
    }

    public int getNextState(int state, ActionType type){
        for(Pair<Integer, ActionType> edge : graph.get(state))
            if(edge.second().equals(type))
                return edge.first();
        return -1;
    }
}
