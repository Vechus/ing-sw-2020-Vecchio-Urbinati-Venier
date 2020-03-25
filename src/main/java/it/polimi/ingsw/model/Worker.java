package it.polimi.ingsw.model;

import it.polimi.ingsw.util.Vector2;

public class Worker {
    Vector2 position;
    Player owner;

    public Worker(Vector2 position, Player owner) {
        this.position = position;
        this.owner = owner;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Player getOwner() {
        return owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Worker)) return false;
        Worker worker = (Worker) o;
        return getPosition().equals(worker.getPosition()) &&
                getOwner().equals(worker.getOwner());
    }

    @Override
    public String toString() {
        return "Worker{" +
                "position=" + position.toString() +
                ", owner=" + owner.toString() +
                '}';
    }
}
