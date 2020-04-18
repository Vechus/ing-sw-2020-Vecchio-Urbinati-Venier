package it.polimi.ingsw.model;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class BuildingTest {

    @Test
    void testComplete(){
        final Building building = new Building();
        Assert.assertFalse(building.isComplete());
        building.setComplete(true);
        Assert.assertTrue(building.isComplete());
    }

    @Test
    void testHeightValid(){
        final Building building = new Building();
        Assert.assertEquals(building.getHeight(), 0);
        for(int i=0;i<=3;i++){
            building.setHeight(i);
            Assert.assertEquals(building.getHeight(), i);
        }
    }

    @Test
    void testHeightInvalid(){
        final Building building = new Building();
        building.setHeight(4);
        Assert.assertEquals(building.getHeight(), 0);
        building.setHeight(-1);
        Assert.assertEquals(building.getHeight(), 0);
    }
}
