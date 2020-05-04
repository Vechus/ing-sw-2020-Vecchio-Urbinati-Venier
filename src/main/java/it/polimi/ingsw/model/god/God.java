package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;
import org.testng.internal.collections.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * The type God.
 */
public class God {
    /**
     * The Board.
     */
    protected Board board;
    /**
     * The Player.
     */
    protected Player player;
    /**
     * Has the player moved.
     */
    protected boolean hasMoved= false;

    protected boolean hasBuilt=false;

    public boolean getHasMoved(){return hasMoved;  }
    public boolean getHasBuilt(){ return hasBuilt; }
    /**
     * Has the player finished turn.
     */
    protected boolean hasFinishedTurn=false;
    /**
     * The Chosen worker.
     */
    protected Worker chosenWorker= null;

    List<Function<Pair<Action, Board>, Boolean>> moveValidationFunctions = new ArrayList<>(
            Arrays.asList(GodValidationMethods::isTargetPosWithinBoard,
                    GodValidationMethods::isCellWorkersFree,
                    GodValidationMethods::isTargetPosOnDifferentCell,
                    GodValidationMethods::isTargetPosDomesFree,
                    GodValidationMethods::isTargetPosAdjacent,
                    GodValidationMethods::isMoveHeightLessThanOne
            ));

    List<Function<Pair<Action, Board>, Boolean>> buildBlockValidationFunctions = new ArrayList<>(
            Arrays.asList(GodValidationMethods::isTargetPosWithinBoard,
                    GodValidationMethods::isCellWorkersFree,
                    GodValidationMethods::isTargetPosOnDifferentCell,
                    GodValidationMethods::isTargetPosDomesFree,
                    GodValidationMethods::isTargetPosAdjacent,
                    GodValidationMethods::isBuildingHeightLessThanThree
            ));

    List<Function<Pair<Action, Board>, Boolean>> buildDomeValidationFunctions = new ArrayList<>(
            Arrays.asList(GodValidationMethods::isTargetPosWithinBoard,
                    GodValidationMethods::isCellWorkersFree,
                    GodValidationMethods::isTargetPosOnDifferentCell,
                    GodValidationMethods::isTargetPosDomesFree,
                    GodValidationMethods::isTargetPosAdjacent,
                    GodValidationMethods::isBuildingHeightThree
            ));


    /**
     * Instantiates a new God.
     *
     * @param board the board.
     */
     public God(Board board) {
        this.board = board;
    }

    /**
     * Instantiates a new God.
     *
     * @param board  the board
     * @param player the player.
     */
    public God(Board board, Player player){
        this.board=board;
        this.player=player;
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
     *
     * @return the boolean.
     */
    public boolean getHasFinishedTurn(){return hasFinishedTurn;}



    /**
     * Choose action. Given an Action this class calls the right function to execute.
     *
     * @param action the action
     * @return the boolean.
     */
    public  boolean chooseAction (Action action){
        if (chosenWorker==null){ chosenWorker=action.getWorker(); }

        if(this.hasMoved ){
            if(action.getType()==Action.ActionType.BUILD && chosenWorker==action.getWorker()&&hasBuilt==false){
                if(buildBlock(action)) {
                    this.hasBuilt=true;
                    return true;
                }
            }else if(action.getType()==Action.ActionType.BUILD_DOME&& chosenWorker==action.getWorker()&&hasBuilt==false){
                if(buildDome(action)) {
                    this.hasBuilt=true;
                    return true;
                }
            }
        }else if (action.getType()==Action.ActionType.MOVE && chosenWorker==action.getWorker()&&hasBuilt==false){
            if (move(action)) {
                this.hasMoved = true;
                return true;
            }
        }
        if(action.getType()==Action.ActionType.END_TURN ) {
            if (endTurn()) {
                this.hasFinishedTurn = true;
                return true;
            }
        }
        return false;
    }


    /**
     * Move a Worker. Returns True if the action has been correctly performed.
     *
     * @param action the action
     * @return the boolean.
     */
    public boolean move(Action action) {
        if(!this.isMoveValid(action)) {
            return false;
        }
        this.board.moveWorker(action);

        return true;
    }

    /**
     * Is workers move valid boolean.
     *
     * @param action the action
     * @return the boolean.
     */
    public boolean isMoveValid (Action action){
        return checkConditions(moveValidationFunctions, action);
    }

    /**
     * Build. Returns True if the action has been correctly performed.
     *
     * @param action the action
     * @return the boolean.
     */
    public boolean buildBlock (Action action){
        if(isBuildBlockValid(action)) {
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
     * @return the boolean.
     */
    public boolean isBuildBlockValid(Action action){
        return checkConditions(buildBlockValidationFunctions, action);
    }


    /**
     * Build a dome. Returns True if the action has been correctly performed.
     *
     * @param action the action
     * @return the boolean.
     */
    public boolean buildDome(Action action){
        if (!isBuildDomeValid(action)){
            return false;
        }else{
            this.board.setComplete(action.getTargetPos(), true);
            return true;
        }
    }

    /**
     * Is build dome valid boolean.
     *
     * @param action the action
     * @return the boolean.
     */
    public boolean isBuildDomeValid(Action action){
        return checkConditions(buildDomeValidationFunctions, action);
    }


    /**
     * Check win condition. Returns True if the action performed is going to make the player win.
     *
     * @param action the action
     * @return the boolean.
     */
    public boolean checkWinCondition(Action action){
        return this.board.getHeight(action.getTargetPos()) - this.board.getHeight(action.getWorkerPos()) > 0
                && this.board.getHeight(action.getTargetPos()) == 3
                && action.getType() == Action.ActionType.MOVE;
    }


    /**
     * Begin a new turn. Initializes all the attributes needed.
     */
    public void beginNewTurn(){
        this.hasMoved = false;
        this.hasFinishedTurn = false;
        this.hasBuilt=false;
    }


    public boolean endTurn(){
        if(!(isEndTurnValid())){
            return false;
        }
        return true;
    }

    public boolean isEndTurnValid(){
        if(!hasMoved){
            return false;
        }
        if(!hasBuilt){
            return false;
        }
        return true;
    }

    protected boolean checkConditions(List<Function<Pair<Action, Board>, Boolean>> list, Action action){
        Pair<Action, Board> arg = new Pair<>(action, this.board);
        for(Function<Pair<Action, Board>, Boolean> check : list){
            if(!check.apply(arg))
                return false;
        }
        return true;
    }
}

