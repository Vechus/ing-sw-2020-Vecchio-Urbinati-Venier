package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.util.Vector2;

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
    /**
     * Has the player finished turn.
     */
    protected boolean hasFinishedTurn=false;
    /**
     * The Chosen worker.
     */
    protected Worker chosenWorker;


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
     * Get has finished turn boolean.
     *
     * @return the boolean.
     */
    public boolean getHasFinishedTurn(){return hasFinishedTurn;}


    /**
     * Has player won boolean.
     *
     * @param action the action
     * @return the boolean.
     */
    public boolean hasPlayerWon(Action action){
        return this.board.getHeight(action.getWorkerPos()) == 3;
    }


    /**
     * Choose action. Given an Action this class calls the right function to execute.
     *
     * @param action the action
     * @return the boolean.
     */
    public  boolean chooseAction (Action action){
        if(this.hasMoved ){
            if(action.getType()==Action.ActionType.BUILD){
                if(build(action)) {
                    hasFinishedTurn=true;
                    return true;
                }
            }else if(action.getType()==Action.ActionType.BUILD_DOME){
                if(buildDome(action)) {
                    hasFinishedTurn = true;
                    return true;
                }
            }
        }else if (action.getType()==Action.ActionType.MOVE) {
            if (move(action)) {
                chosenWorker=action.getWorker();
                this.hasMoved = true;
                return true;
            }
        }
        else if(action.getType()==Action.ActionType.END_TURN){
            if(endTurn(action)){

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

        // Vector2 currPos= action.getWorker().getPosition(); // unused variable
        // int heightDiff = this.board.getHeight(currPos)-this.board.getHeight(action.getTargetPos()); // unused variable
        //action will be built probably in model

        if(!this.isWorkersMoveValid(action)) {
            return false;
        }

        this.board.moveWorker(action);
        action.getWorker().setPosition(action.getTargetPos());


        return true;
    }

    /**
     * Is workers move valid boolean.
     *
     * @param action the action
     * @return the boolean.
     */
    public boolean isWorkersMoveValid (Action action){
        Vector2 currPos= action.getWorkerPos();
        Vector2 nextPos= action.getTargetPos();

        int heightDiff = this.board.getHeight(currPos)-this.board.getHeight(action.getTargetPos());

        if (this.board.getWorker(action.getTargetPos()) != null){
            return false;
        }

        if(heightDiff>1){
            return false;
        }

        if(this.board.isComplete(nextPos)){
            return false;
        }

        if (nextPos.getX()>=5 || nextPos.getY()>=5 || nextPos.getX()<0 || nextPos.getY()<0){
            return false;
        }

        if(nextPos.equals(currPos) ){
            return false;
        }

        if(Math.abs(nextPos.getX()-currPos.getX()) >1  || Math.abs(nextPos.getY()-currPos.getY()) >1){
            return false;
        }

        return true;
    }

    /**
     * Build. Returns True if the action has been correctly performed.
     *
     * @param action the action
     * @return the boolean.
     */
    public boolean build (Action action){

        if(isBuildValid(action)) {
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
    public boolean isBuildValid(Action action){
        Vector2 pos=action.getTargetPos();

        //check if pos is within board
        if(pos.getY()>=5 || pos.getX()>=5 || pos.getY()<0 || pos.getX()<0){
            return false;
        }
        //check if targeted pos doesn't have any other worker. It also checks that you don't build where your worker is.
       if (this.board.getWorker(pos) != null){
           return false;
       }

       if(this.board.isComplete(pos)){
           return false;
       }
       if (this.board.getHeight(pos)>=3){
           return false;
       }

       //check worker is building within their range
       if(action.getWorker().getPosition().getX() - pos.getX()>1 || action.getWorker().getPosition().getY()-pos.getY()>1){
           return false;
       }


       //check the worker is the same that moved
        return chosenWorker.equals(action.getWorker());
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

        //check if pos is within board
        if(action.getTargetPos().getY()>=5 || action.getTargetPos().getX()>=5 || action.getTargetPos().getY()<0 || action.getTargetPos().getX()<0){
            return false;
        }
        //check if targeted pos doesn't have any other worker
        if (this.board.getWorker(action.getTargetPos()) != null){
            return false;
        }


        if(this.board.isComplete(action.getTargetPos())){
            return false;
        }

        //check worker is building within their range
        if(action.getWorker().getPosition().getX() - action.getTargetPos().getX()>1 || action.getWorker().getPosition().getY()-action.getTargetPos().getY()>1){
            return false;
        }

        if(this.board.getHeight(action.getTargetPos())<3){
            return false;
        }
        //check the worker is the same that moved
        return chosenWorker.equals(action.getWorker());
    }


    /**
     * Check win condition. Returns True if the action performed is going to make the player win.
     *
     * @param action the action
     * @return the boolean.
     */
    public boolean checkWinCondition(Action action){
        return this.board.getHeight(action.getTargetPos()) - this.board.getHeight(action.getWorkerPos()) > 0
                && this.board.getHeight(action.getTargetPos()) == 3;
    }


    /**
     * Begin a new turn. Initializes all the attributes needed.
     */
    public void beginNewTurn(){
        this.hasMoved = false;
        this.hasFinishedTurn = false;
    }


    public boolean endTurn(Action action){
        if(!(isEndTurnValid(action))){
            return false;
        }

        return true;
    }

    public boolean isEndTurnValid(Action action){
        if(!hasMoved){
            return false;
        }
    }
}

