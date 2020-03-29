package it.polimi.ingsw.model;

import it.polimi.ingsw.util.Vector2;

public class Move {
    private Vector2 initPos;
    private Vector2 finPos;
    private int heightDiff;


    /**
     * Create a Move object with initial position, final position and height difference
     * @param initPos Vector2 initial position
     * @param finPos Vector2 final position
     * @param heightDiff int height difference
     */
    public Move(Vector2 initPos, Vector2 finPos, int heightDiff) {
        this.initPos = initPos;
        this.finPos = finPos;
        this.heightDiff = heightDiff;
    }

    /**
     * Get initial position
     * @return this.initPos
     */
    public Vector2 getInitPos() {
        return this.initPos;
    }

    /**
     * Get final position
     * @return this.finPos
     */
    public Vector2 getFinPos() {
        return this.finPos;
    }

    /**
     * Get height difference
     * @return this.heightDiff
     */
    public int getHeightDiff() {
        return this.heightDiff;
    }
}
