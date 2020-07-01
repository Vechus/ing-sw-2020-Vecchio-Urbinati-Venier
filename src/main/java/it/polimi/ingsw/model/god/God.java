package it.polimi.ingsw.model.god;

import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.util.ActionType;
import it.polimi.ingsw.util.Pair;
import it.polimi.ingsw.util.Vector2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * The type God.
 */
public class God {
    /**
     * The name of the god, to be displayed to the user
     */
    private String name = "God";

    private Board board;

    private Player player;

    /**
     * Getter of moveCtr
     * @return how many times the player moved this turn
     */
    public int getMoveCount(){return moveCtr;  }

    /**
     *Getter of buildCtr
     * @return how many times the player built this turn
     */
    public int getBuildCount(){ return buildCtr; }

    private boolean hasFinishedTurn=false;

     //The chosen worker by the player in that turn. Only that worker can be used after the initial action.
    private Worker chosenWorker= null;


    private Vector2 initPos = null, buildPos = null;

    //count how many moves and builds the player has done in that turn
    private int moveCtr = 0, buildCtr = 0;

    private int turnState;

    private List<Function<Pair<Action, Board>, Boolean>> moveValidationFunctions = new ArrayList<>(
            Arrays.asList(GodValidationMethods::isActionPermittedByEffects,
                    GodValidationMethods::isTargetPosWithinBoard,
                    GodValidationMethods::isCellWorkersFree,
                    GodValidationMethods::isTargetPosOnDifferentCell,
                    GodValidationMethods::isTargetPosDomesFree,
                    GodValidationMethods::isTargetPosAdjacent,
                    GodValidationMethods::isMoveHeightLessThanOne
            ));

    private List<Function<Pair<Action, Board>, Boolean>> buildBlockValidationFunctions = new ArrayList<>(
            Arrays.asList(GodValidationMethods::isActionPermittedByEffects,
                    GodValidationMethods::isTargetPosWithinBoard,
                    GodValidationMethods::isCellWorkersFree,
                    GodValidationMethods::isTargetPosOnDifferentCell,
                    GodValidationMethods::isTargetPosDomesFree,
                    GodValidationMethods::isTargetPosAdjacent,
                    GodValidationMethods::isBuildingHeightLessThanThree
            ));

    private List<Function<Pair<Action, Board>, Boolean>> buildDomeValidationFunctions = new ArrayList<>(
            Arrays.asList(GodValidationMethods::isActionPermittedByEffects,
                    GodValidationMethods::isTargetPosWithinBoard,
                    GodValidationMethods::isCellWorkersFree,
                    GodValidationMethods::isTargetPosOnDifferentCell,
                    GodValidationMethods::isTargetPosDomesFree,
                    GodValidationMethods::isTargetPosAdjacent,
                    GodValidationMethods::isBuildingHeightThree
            ));

    private List<Function<Pair<Action, Board>, Boolean>> winValidationFunctions = new ArrayList<>(
            Arrays.asList(GodWinMethods::isWinPermittedByEffects,
                    GodWinMethods::defaultWinCondition
            ));

    private Function<Pair<Action, Board>, Boolean> moveFunction = GodMoveMethods::defaultMove;

    TurnActionGraph actionGraph = new TurnActionGraph();

    /**
     * Instantiates a new God.
     *
     * @param board  the board
     * @param player the player.
     */
    public God(Board board, Player player){
        this.player=player;
        this.board = board;
        player.setPlayerGod(this);
        name = "God";
        turnState = actionGraph.INITIAL_STATE_IDX;
    }

    /**
     * Set the move validation functions
     *
     * @param funcs the functions to apply when validating a move
     */
    public void setMoveValidationMethods(List<Function<Pair<Action, Board>, Boolean>> funcs){
        this.moveValidationFunctions = funcs;
    }

    /**
     * Set the block build validation functions
     *
     * @param funcs the functions to apply when validating a block build
     */
    public void setBuildBlockValidationMethods(List<Function<Pair<Action, Board>, Boolean>> funcs){
        this.buildBlockValidationFunctions = funcs;
    }

    /**
     * Set the dome build validation functions
     *
     * @param funcs the functions to apply when validating a dome build
     */
    public void setBuildDomeValidationMethods(List<Function<Pair<Action, Board>, Boolean>> funcs){
        this.buildDomeValidationFunctions = funcs;
    }

    /**
     * Set the win validation functions
     *
     * @param funcs the functions to apply when checking if a player won
     */
    public void setWinValidationFunctions(List<Function<Pair<Action, Board>, Boolean>> funcs){
        this.winValidationFunctions = funcs;
    }

    public void setMoveMethod(Function<Pair<Action, Board>, Boolean> func){
        this.moveFunction = func;
    }

    /**
     * Set the god's player
     *
     * @param player the player
     */
    public void setPlayer(Player player){
        this.player = player;
    }

    /**
     * Get the god's player
     * @return the player
     */
    public Player getPlayer(){ return this.player; }

    /**
     * Get has finished turn boolean.
     * @return the boolean.
     */
    public boolean getHasFinishedTurn(){return hasFinishedTurn;}

    /**
     * Choose action. Given an Action this class calls the right function to execute.
     *
     * @param action the action
     * @return the result of the call of the action. True if it succeeded, otherwise false.
     */
    public boolean chooseAction (Action action){
        int nextState = actionGraph.getNextState(turnState, action.getType());
        if(nextState == -1 || action.getType() != ActionType.END_TURN && chosenWorker != null && !chosenWorker.equals(action.getWorker()))
            return false;

        boolean res = true;
        if(action.getType() == ActionType.MOVE)
            res = move(action);
        else if(action.getType() == ActionType.BUILD)
            res = buildBlock(action);
        else if(action.getType() == ActionType.BUILD_DOME)
            res = buildDome(action);

        if(!res)
            return false;

        if (chosenWorker==null){ chosenWorker=action.getWorker(); }
        if(nextState == actionGraph.FINAL_STATE_IDX)
            hasFinishedTurn = true;

        turnState = nextState;
        return true;
    }


    /**
     * Move a Worker. Returns True if the action has been correctly performed.
     *
     * @param action the action chosen.
     * @return true if it succeeded.
     */
    public boolean move(Action action) {
        if(!this.isMoveValid(action))
            return false;

        if(initPos == null) initPos = action.getWorkerPos();
        moveCtr++;

        return moveFunction.apply(new Pair<>(action, board));
    }

    /**
     * Is workers move valid.
     *
     * @param action the action chosen.
     * @return true is the move is valid.
     * */
    public boolean isMoveValid (Action action){
        return checkConditions(moveValidationFunctions, action, board);
    }

    /**
     * @param action the action chosen
     * @return True if the action has been correctly performed.
     */
    public boolean buildBlock (Action action){
        if(isBuildBlockValid(action)) {
            if(buildPos == null) buildPos = action.getTargetPos();
            buildCtr++;
            this.board.setHeight(action.getTargetPos(), board.getHeight(action.getTargetPos()) + 1);
            return true;
        }else{
            return false;
        }

    }

    /**
     * Is build valid boolean.
     *
     * @param action the action
     * @return true if the building the block is valid
     */
    public boolean isBuildBlockValid(Action action){
        return checkConditions(buildBlockValidationFunctions, action, board);
    }


    /**
     *
     * @param action the action
     * @return Returns True if the action has been correctly performed.
     */
    public boolean buildDome(Action action){
        if (!isBuildDomeValid(action)){
            return false;
        }else{
            if(buildPos == null) buildPos = action.getTargetPos();
            buildCtr++;
            this.board.setComplete(action.getTargetPos(), true);
            return true;
        }
    }

    /**
     * @param action the action
     * @return true if building a  dome is valid
     */
    public boolean isBuildDomeValid(Action action){
        return checkConditions(buildDomeValidationFunctions, action, board);
    }


    /**
     * Check win condition. Returns True if the action performed is going to make the player win.
     *
     * @param action the action
     * @return the boolean.
     */
    public boolean checkWinCondition(Action action){
        return checkConditions(winValidationFunctions, action, board);
    }


    /**
     * Begin a new turn. Initializes all the attributes needed.
     */
    public void beginNewTurn(){
        this.hasFinishedTurn = false;
        turnState = actionGraph.INITIAL_STATE_IDX;
        initPos = null;
        buildPos = null;
        moveCtr = 0;
        buildCtr = 0;
        chosenWorker = null;
    }

    /**
     *
     * @param list of the constraint
     * @param action
     * @return false if a condition is not met.
     */
    public static boolean checkConditions(List<Function<Pair<Action, Board>, Boolean>> list, Action action, Board board){
        Pair<Action, Board> arg = new Pair<>(action, board);
        for(Function<Pair<Action, Board>, Boolean> check : list){
            if(!check.apply(arg))
                return false;
        }
        return true;
    }

    /**
     *
     * @return the action that the player can perform in that situation
     */
    public List<ActionType> getAllowedActions() {
        return actionGraph.allowedActions(turnState);
    }

    //@Override
    //public String toString() {
   //     return getName();
    //}

    public void setName(String godName) {
        this.name = godName;
    }

    public String getName() {
        return this.name;
    }

    public Vector2 getInitPos(){
        return this.initPos;
    }

    public Vector2 getBuildPos(){
        return this.buildPos;
    }

    public void setActionGraph(TurnActionGraph graph){
        this.actionGraph = graph;
    }
}

