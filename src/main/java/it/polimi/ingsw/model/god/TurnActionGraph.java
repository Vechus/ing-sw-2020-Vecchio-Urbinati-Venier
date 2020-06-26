package it.polimi.ingsw.model.god;

import it.polimi.ingsw.util.ActionType;
import it.polimi.ingsw.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the possible paths of action that a player can make during their turn.
 */
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

    /**
     * For a state it tells what action the player can make.
     * @param stateIdx the index of the node of the graph
     * @return list of actions allowed.
     */
    public List<ActionType> allowedActions(int stateIdx){
        List<ActionType> allowed = new ArrayList<>();
        for(Pair<Integer, ActionType> edge : graph.get(stateIdx))
            allowed.add(edge.second());
        return allowed;
    }

    /**
     *Method that adds a state to the graph
     * @return the new number of states
     */
    public int addState() {
        int n = numStates++;
        graph.add(new ArrayList<>());
        return n;
    }

    /**
     *adds an edge in the graph
     * @param idxFrom the state from which the edge starts
     * @param idxTo the state where the edge lands
     * @param type the type of the action
     */
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

    /**
     *Given the starting state and the type of the action if gives the final state
     * @param state the starting state
     * @param type the type of the action
     * @return the final state
     */
    public int getNextState(int state, ActionType type){
        for(Pair<Integer, ActionType> edge : graph.get(state))
            if(edge.second().equals(type))
                return edge.first();
        return -1;
    }
}
