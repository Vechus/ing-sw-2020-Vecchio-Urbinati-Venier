package it.polimi.ingsw.model;

import it.polimi.ingsw.model.god.God;
import it.polimi.ingsw.util.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<Worker> workers;
    private God playerGod;
    private boolean isSpectator;
    private boolean isFinished;
    private Board board;

    /**
     * Constructor for player without any parameter
     * USE ONLY FOR TESTING PURPOSE
     */
    public Player() {
        this.board = new Board();
        this.playerGod = new God(this.board, this);
        this.isSpectator = false;
        this.isFinished = false;
        this.workers = new ArrayList<>();
        for(int i = 0; i < 2; i++) {
            this.workers.add(new Worker(this));
        }
    }

    /**
     * Constructs a Player given number of its Workers, its God and the Board
     * @param god God that the Player will be playing for the match
     * @param board Board that the Player will play onto
     */
    public Player(God god, Board board) {
        this.playerGod = god;
        this.isSpectator = false;
        this.isFinished = false;
        this.board = board;
        this.workers = new ArrayList<>();
        for(int i = 0; i < 2; i++) {
            this.workers.add(new Worker(this));
        }
    }


    /**
     * Instantiates a new Player, given a board. Warning! You must set the God afterwards!
     *
     * @param board the board
     */
    public Player(Board board) {
        this.isSpectator = false;
        this.isFinished = false;
        this.board = board;
        this.workers = new ArrayList<>();
        for(int i = 0; i < 2; i++) {
            this.workers.add(new Worker(this));
        }
    }

    /**
     * Begin a new turn, initialising all parameters.
     */
    public void beginNewTurn() {
        this.isFinished = false;
        this.playerGod.beginNewTurn();
    }

    /**
     * Checks Player win condition, by returning whether God's win condition is met
     * @return hasPlayerWon boolean: 1 if Player has won, 0 else
     */
    public boolean checkWinCondition(Action action) {
        return this.playerGod.checkWinCondition(action);
    }

    /**
     * For each Worker of this player, check if any move is possible: if none is possible than the Player loses the game
     * @return hasPlayerLost boolean: 1 if Player has lost, 0 else
     */
    public boolean checkLoseCondition() {
        Vector2 posDelta;
        for(Worker w : this.workers) {
            // iterate around the worker
            for(int x = -1; x <= 1; x++) {
                for(int y = -1; y <= 1; y++) {
                    posDelta = w.getPosition().add(new Vector2(x, y));
                    if(this.getPlayerGod().isWorkersMoveValid(new Action(w, posDelta, Action.ActionType.MOVE))) return false;
                }
            }
        }
        return true;
    }

    /**
     * Tries a move, if it's allowed then it executes it, if not -(throws InvalidMoveException)- prints a message
     * @param action Action target action
     */
    public boolean doAction(Action action) {
        if(action.getType().equals(Action.ActionType.BUILD)) {
            return this.playerGod.build(action);
        } else if(action.getType().equals(Action.ActionType.BUILD_DOME)) {
            return this.playerGod.buildDome(action);
        } else if(action.getType().equals(Action.ActionType.MOVE)) {
            return this.playerGod.move(action);
        } else {
            // throw InvalidMoveException
            return false;
        }
    }

    /**
     * Get the n-th worker of this Player
     * @param idx index of Worker
     * @return worker this.workers[idx]
     */
    public Worker getWorker(int idx) {
        return this.workers.get(idx);
    }

    /**
     * Set the n-th worker of this Player
     * @param idx index of the worker to be set to
     * @param w Worker to set
     */
    public void setWorker(int idx, Worker w) {
        this.workers.set(idx, w);
    }

    /**
     * Get Player's God
     * @return playerGod God of this Player
     */
    public God getPlayerGod() {
        return playerGod;
    }

    /**
     * Set Player's God
     * @param playerGod God to set the Player's God to
     */
    public void setPlayerGod(God playerGod) {
        this.playerGod = playerGod;
    }

    /**
     * Gets flag isSpectator: is this Player a spectator?
     * @return isSpectator boolean
     */
    public boolean isSpectator() {
        return isSpectator;
    }

    /**
     * Sets flag isSpectator
     * @param spectator boolean to set isSpectator to
     */
    public void setSpectator(boolean spectator) {
        this.isSpectator = spectator;
    }

    /**
     * Gets flag isFinished
     * @return isFinished boolean
     */
    public boolean isFinished() {
        return isFinished;
    }

    /**
     * Sets flag isFinished
     * @param finished boolean to set isFinished to
     */
    public void setFinished(boolean finished) {
        this.isFinished = finished;
    }
}
