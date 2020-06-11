package it.polimi.ingsw.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class BuildingTest {

    @Test
    void testComplete(){
        final Building building = new Building();
        assertFalse(building.isComplete());
        building.setComplete(true);
        assertTrue(building.isComplete());
    }

    @Test
    void testHeightValid(){
        final Building building = new Building();
        assertEquals(building.getHeight(), 0);
        for(int i=0;i<=3;i++){
            building.setHeight(i);
            assertEquals(building.getHeight(), i);
        }
    }

    @Test
    void testHeightInvalid(){
        final Building building = new Building();
        building.setHeight(4);
        assertEquals(building.getHeight(), 0);
        building.setHeight(-1);
        assertEquals(building.getHeight(), 0);
    }
}
