package it.polimi.ingsw.util;

/**
 * Defines the type of the action.
 * How to use it somewhere else:
     import it.polimi.ingsw.util.Action
     Action.ActionType foo = Action.ActionType.BUILD;
 */
public enum ActionType {PLACE_WORKER, BUILD, MOVE, BUILD_DOME, END_TURN}
