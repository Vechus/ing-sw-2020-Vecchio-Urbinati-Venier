package it.polimi.ingsw.model;

import it.polimi.ingsw.model.god.God;
import it.polimi.ingsw.util.Vector2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkerTest {

    @Test
    void getPosition() {
        final Worker worker = new Worker(new Vector2(1, 4), new Player());
        assertEquals(worker.getPosition(), new Vector2(1, 4));
    }

    @Test
    void setPosition() {
        final Worker worker = new Worker(new Player());
        worker.setPosition(new Vector2(1, 2));
        assertEquals(worker.getPosition(), new Vector2(1, 2));
    }

    @Test
    void getOwner() {
        final Player player = new Player();
        assertSame(player.getWorker(0).getOwner(), player);
    }
}