package it.polimi.ingsw.model;

import it.polimi.ingsw.model.god.God;
import it.polimi.ingsw.util.Vector2;

import java.util.ArrayList;

public class Player {
    private int numOfWorkers;
    private ArrayList<Worker> workers;
    private God playerGod;
    private boolean isSpectator;
    private boolean isFinished;


    public Player(int numOfWorkers, God god) {
        this.numOfWorkers = numOfWorkers;
        this.playerGod = god;
        this.isSpectator = false;
        this.isFinished = false;
        workers = new ArrayList<Worker>();
        for(int i = 0; i < this.numOfWorkers; i++) {
            workers.add(new Worker(this));
        }
    }

    public boolean checkWinCondition() {
        return this.playerGod.checkWinCondition();
    }

    /*
    For each Worker of this player, check if any move is possible
     */
    public boolean checkLoseCondition() {
        Vector2 pos;
        Worker w;
        boolean out;
        for(int i = 0; i < this.numOfWorkers; i ++) {
            w = workers.get(i);
            pos = w.getPosition();
            // iterate around the worker
            for(int x = -1; x <= 1; x++) {
                for(int y = -1; y <= 1; y++) {
                    if(this.playerGod.move(w, new Move(pos, pos.add(x, y)))) return false;
                }
            }
        }
        return true;
    }

    public void doAction(Worker worker, Vector2 pos) {
        // check if the move is allowed, else throw an exception
        if(this.playerGod.move(worker, new Move(worker.getPosition(), pos))) {
            worker.setPosition(pos);
        } else {
            // throw new InvalidMoveException;
            System.out.println("*** Invalid move ***");
        }
    }

    public Worker getWorker(int idx) {
        return this.workers.get(idx);
    }

    public void setWorker(int idx, Worker w) {
        this.workers.set(idx, w);
    }

    public int getNumOfWorkers() {
        return numOfWorkers;
    }

    public void setNumOfWorkers(int numOfWorkers) {
        this.numOfWorkers = numOfWorkers;
    }

    public God getPlayerGod() {
        return playerGod;
    }

    public void setPlayerGod(God playerGod) {
        this.playerGod = playerGod;
    }

    public boolean isSpectator() {
        return isSpectator;
    }

    public void setSpectator(boolean spectator) {
        this.isSpectator = spectator;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        this.isFinished = finished;
    }
}
