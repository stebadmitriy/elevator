package initializer;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;

public class CreatorListPassengersTest {
    private CreatorListPassengers creatorListPassengers;

    @Before
    public void setUp() {
        creatorListPassengers = new CreatorListPassengers();
    }


    @Test
    public void notEqualStartFloorAndDestinationFloorTest() {

        int start = creatorListPassengers.createStartFloor(10);
        int destination = creatorListPassengers.createDestinationFloor(10, start);
        assertNotEquals(start, destination);

    }
}