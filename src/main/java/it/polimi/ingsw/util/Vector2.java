package it.polimi.ingsw.util;

import java.util.Objects;

public class Vector2 implements Cloneable{
    private int x;
    private int y;


    public Vector2() {
        this.x = -1;
        this.y = -1;
    }

    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2 add(Vector2 v) {
        return new Vector2(this.getX() + v.getX(), this.getY() + v.getY());
    }

    public Vector2 sub(Vector2 v) {
        return new Vector2(this.getX() - v.getX(), this.getY() - v.getY());
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector2)) return false;
        Vector2 vector2 = (Vector2) o;
        return getX() == vector2.getX() &&
                getY() == vector2.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }

    @Override
    public String toString() {
        return "Vector2{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
