package it.polimi.ingsw.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector2Test {

    @Test
    void set() {
        final Vector2 v = new Vector2();
        v.set(1, 3);
        assertTrue(v.getX() == 1 && v.getY() == 3);
        v.set(0, 2);
        assertTrue(v.getX() == 0 && v.getY() == 2);
    }

    @Test
    void add() {
        final Vector2 v1 = new Vector2(2, 4);
        final Vector2 v2 = new Vector2(1, 0);
        final Vector2 result = v1.add(v2);
        assertTrue(result.getX() == 3 && result.getY() == 4);
    }

    @Test
    void sub() {
        final Vector2 v1 = new Vector2(2, 4);
        final Vector2 v2 = new Vector2(1, 0);
        final Vector2 result = v1.sub(v2);
        assertTrue(result.getX() == 1 && result.getY() == 4);
    }

    @Test
    void getX() {
        final Vector2 v = new Vector2(1, 0);
        assertEquals(v.getX(), 1);
        v.setX(5);
        assertEquals(v.getX(), 5);
    }

    @Test
    void setX() {
        final Vector2 v = new Vector2();
        v.setX(1);
        assertEquals(1, v.getX());
        v.setX(2);
        assertEquals(2, v.getX());
    }

    @Test
    void getY() {
        final Vector2 v = new Vector2(1, 0);
        assertEquals(v.getY(), 0);
        v.setY(5);
        assertEquals(v.getY(), 5);
    }

    @Test
    void setY() {
        final Vector2 v = new Vector2();
        v.setY(1);
        assertEquals(1, v.getY());
        v.setY(2);
        assertEquals(2, v.getY());
    }

    @Test
    void testClone() {
        final Vector2 v = new Vector2(2, 3);
        final Vector2 cloned;
        try {
            cloned = (Vector2) v.clone();
            assertTrue(cloned.getX() == v.getX() && cloned.getY() == v.getY());
        } catch (CloneNotSupportedException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    void testEquals() {
        final Vector2 v1 = new Vector2(1, 5);
        final Vector2 v2 = new Vector2();
        v2.set(1, 5);
        assertTrue(v1.equals(v2));
        v2.set(1, 4);
        assertFalse(v1.equals(v2));
        v2.set(2, 5);
        assertFalse(v1.equals(v2));
        v2.setY(8);
        assertFalse(v1.equals(v2));
    }

    @Test
    void testToString() {
        final Vector2 v = new Vector2(8, 19);
        final String s = v.toString();
        assertEquals(
                "Vector2{" +
                        "x=" + v.getX() +
                        ", y=" + v.getY() +
                        '}', s
        );
    }
}