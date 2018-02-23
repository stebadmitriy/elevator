package beans.buildingComponents;

import beans.passengers.Passenger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class FloorTest {

    private Floor floorMock;

    @Before
    public void setUp() {
        floorMock = Mockito.mock(Floor.class);

    }

    @Test
    public void addDispatchFloorContainerTest() {

        Passenger passengerOne = new Passenger(0, 2, 3);
        Passenger passengerTwo = new Passenger(1, 5, 7);
        Passenger passengerThree = new Passenger(2, 8, 9);

        floorMock.addDispatchFloorContainer(passengerOne);
        floorMock.addDispatchFloorContainer(passengerTwo);
        floorMock.addDispatchFloorContainer(passengerThree);

        verify(floorMock).addDispatchFloorContainer(passengerOne);
        verify(floorMock).addDispatchFloorContainer(passengerTwo);
        verify(floorMock).addDispatchFloorContainer(passengerThree);
        verifyNoMoreInteractions(floorMock);


    }

    @Test
    public void removeDispatchFloorContainerTest() {
        Passenger passengerOne = new Passenger(0, 2, 3);
        Passenger passengerTwo = new Passenger(1, 5, 7);
        Passenger passengerThree = new Passenger(2, 8, 9);

        floorMock.removeDispatchFloorContainer(passengerOne);
        floorMock.removeDispatchFloorContainer(passengerTwo);
        floorMock.removeDispatchFloorContainer(passengerThree);

        verify(floorMock).removeDispatchFloorContainer(passengerOne);
        verify(floorMock).removeDispatchFloorContainer(passengerTwo);
        verify(floorMock).removeDispatchFloorContainer(passengerThree);
        verifyNoMoreInteractions(floorMock);
    }
}