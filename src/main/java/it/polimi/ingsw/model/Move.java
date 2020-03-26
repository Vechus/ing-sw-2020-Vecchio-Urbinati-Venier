package it.polimi.ingsw.model;

import it.polimi.ingsw.util.Vector2;

public class Move {
    private Vector2 initPos;
    private Vector2 finPos;
    private int heightDiff;

    public Move(Vector2 initPos, Vector2 finPos) {
        this.initPos = initPos;
        this.finPos = finPos;
        this.heightDiff = 0;
    }

    public Move(Vector2 initPos, Vector2 finPos, int heightDiff) {
        this.initPos = initPos;
        this.finPos = finPos;
        this.heightDiff = heightDiff;
    }

    public Vector2 getInitPos() {
        return initPos;
    }

    public Vector2 getFinPos() {
        return finPos;
    }

    public int getHeightDiff() {
        return heightDiff;
    }
}
