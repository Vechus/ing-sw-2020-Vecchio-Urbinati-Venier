package it.polimi.ingsw.util;

import java.util.Objects;

public class Vector2 implements Cloneable{
    private int x;
    private int y;


    /**
     * default constructor, sets coordinates to (-1, -1)
     */
    public Vector2() {
        this.x = -1;
        this.y = -1;
    }

    /**
     * complete constructor, sets coordinates (x, y)
     * @param x x coordinate
     * @param y y coordinate
     */
    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * setter for both coordinates
     * @param x x coordinate
     * @param y y coordinate
     */
    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * addition between two vectors
     * @param v vector to add to this
     * @return result as new object of type Vector2
     */
    public Vector2 add(Vector2 v) {
        return new Vector2(this.getX() + v.getX(), this.getY() + v.getY());
    }

    /**
     * subtraction between two vectors
     * @param v vector to subtract from this
     * @return result as new object of type Vector2
     */
    public Vector2 sub(Vector2 v) {
        return new Vector2(this.getX() - v.getX(), this.getY() - v.getY());
    }

    /**
     * @return x x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * @param x x coordinate to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return y y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * @param y y coordinate to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return cloned Vector2 object
     * @throws CloneNotSupportedException if clone is not supported
     */
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    /**
     * equals: returns true if argument's coordinates are the same as this
     * @param o object to check
     * @return result
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector2)) return false;
        Vector2 vector2 = (Vector2) o;
        return getX() == vector2.getX() &&
                getY() == vector2.getY();
    }

    @Override
    /**
     * hashes the vector
     * @return hash value
     */
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }

    @Override
    /**
     * converts this to string, format "Vector2{x=[value], y=[value]}"
     * @return vector converted to string
     */
    public String toString() {
        return "Vector2{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
