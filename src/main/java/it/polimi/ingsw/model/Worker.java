package it.polimi.ingsw.model;

import it.polimi.ingsw.util.Vector2;

public class Worker {
    private Vector2 position;
    private Player owner;

    /**
     * Creates a Worker object, to be placed on the board afterwards. Its position is initialised to (-1,-1)
     * @param owner Player who owns the Worker
     */
    public Worker(Player owner){
        this.position = new Vector2(-1, -1);
        this.owner = owner;
    }

    /**
     * Creates a Worker object, with given position
     * @param position Vector2 position, the position to set the Worker to
     * @param owner Player who owns the Worker
     */
    public Worker(Vector2 position, Player owner) {
        this.position = position;
        this.owner = owner;
    }

    /**
     * Get the position of this Worker
     * @return position of Worker
     */
    public Vector2 getPosition() {
        return position;
    }

    /**
     * Sets the position of this Worker
     * @param position Vector2 position to set
     */
    public void setPosition(Vector2 position) {
        this.position = position;
    }

    /**
     * Gets the owner of this Worker
     * @return owner Player who own this Worker
     */
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
