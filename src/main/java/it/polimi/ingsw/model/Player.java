package it.polimi.ingsw.model;

import it.polimi.ingsw.model.god.God;
import it.polimi.ingsw.util.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private int numOfWorkers;
    private List<Worker> workers;
    private God playerGod;
    private boolean isSpectator;
    private boolean isFinished;
    private Board board;


    /**
     * Constructs a Player given number of its Workers, its God and the Board
     * @param numOfWorkers number of workers
     * @param god God that the Player will be playing for the match
     * @param board Board that the Player will play onto
     */
    public Player(int numOfWorkers, God god, Board board) {
        this.numOfWorkers = numOfWorkers;
        this.playerGod = god;
        this.isSpectator = false;
        this.isFinished = false;
        this.board = board;
        this.workers = new ArrayList<>();
        for(int i = 0; i < this.numOfWorkers; i++) {
            this.workers.add(new Worker(this));
        }
    }

    /**
     * Checks Player win condition, by returning whether God's win condition is met
     * @return hasPlayerWon boolean: 1 if Player has won, 0 else
     */
    public boolean checkWinCondition() {
        return this.playerGod.checkWinCondition();
    }

    /**
     * For each Worker of this player, check if any move is possible: if none is possible than the Player loses the game
     * @return hasPlayerLost boolean: 1 if Player has lost, 0 else
     */
    public boolean checkLoseCondition() {
        Vector2 pos, posDelta;
        int workerHeight;
        for(Worker w : this.workers) {
            pos = w.getPosition();
            workerHeight = this.board.getCell(pos).getHeight();
            // iterate around the worker
            for(int x = -1; x <= 1; x++) {
                for(int y = -1; y <= 1; y++) {
                    posDelta = pos.add(new Vector2(x, y));
                    if(this.getPlayerGod().move(w, new Action(pos,
                            posDelta,
                            this.board.getCell(posDelta).getHeight() - workerHeight))) return false;
                }
            }
        }
        return true;
    }

    /**
     * Tries a move, if it's allowed then it executes it, if not -(throws InvalidMoveException)- prints a message
     * @param worker Worker to do the action with
     * @param pos Vector2 position to move into
     */
    // TODO: discuss movement: not robust structure for movement
    public void doAction(Worker worker, Vector2 pos) {
        // check if the move is allowed, else throw an exception
        Vector2 workerPos = worker.getPosition();
        int workerHeight = this.board.getCell(workerPos).getHeight();
        if(this.getPlayerGod().move(worker, new Action(workerPos, pos, this.board.getCell(pos).getHeight() - workerHeight))) {
            worker.setPosition(pos);
        } else {
            // throw new InvalidMoveException;
            System.out.println("*** Invalid move ***");
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
     * Get number of this Player's workers
     * @return this.numOfWorkers int number of this Player's workers
     */
    public int getNumOfWorkers() {
        return this.numOfWorkers;
    }

    /**
     * Set number of this Player's workers
     * @param numOfWorkers int number of workers to set
     */
    public void setNumOfWorkers(int numOfWorkers) {
        this.numOfWorkers = numOfWorkers;
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
