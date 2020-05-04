package it.polimi.ingsw.model;

import it.polimi.ingsw.model.god.God;
import it.polimi.ingsw.util.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkerTest {
    Worker worker = new Worker(new Player());
    Worker worker1 = new Worker(new Vector2(1, 4), new Player());

    @BeforeEach
    void setup() {

    }

    @Test
    void getPosition() {
        assertEquals(worker1.getPosition(), new Vector2(1, 4));
    }

    @Test
    void setPosition() {
        worker.setPosition(new Vector2(1, 2));
        assertEquals(worker.getPosition(), new Vector2(1, 2));
    }

    @Test
    void getOwner() {
        final Player player = new Player();
        Worker w = new Worker(player);
        assertSame(w.getOwner(), player);
    }

    @Test
    void testEquals() {
        assertFalse(worker.equals("haha"));
        Worker wTest = new Worker(worker.getPosition(), worker.getOwner());
        assertTrue(wTest.equals(worker));
    }
}