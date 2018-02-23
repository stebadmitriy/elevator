package beans.buildingComponents.elevator;

import beans.passengers.Passenger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ElevatorControllerTest {

    private Passenger passenger;

    @Before
    public void setUp() {
        passenger = new Passenger(0, 1, 5);
    }

    @Test
    public void checkEqualDestinationFloorPassengerAndCurrentFloorElevator() {
        Elevator elevatorMock = mock(Elevator.class);
        Passenger passenger = new Passenger(0, 1, 5);
        when(elevatorMock.getCurrentFloor()).thenReturn(5);

        int currentFloor = elevatorMock.getCurrentFloor();
        assertEquals(currentFloor, passenger.getDestinationFloor());
    }

    @Test
    public void notifyAddingPassengerIntoElevatorContainerTest() {
        ElevatorController elevatorControllerMock = mock(ElevatorController.class);
        elevatorControllerMock.boadingPassenger(passenger);
        verify(elevatorControllerMock).boadingPassenger(passenger);
        verifyNoMoreInteractions(elevatorControllerMock);

    }

    @Test
    public void notifyRemovePassengerFromElevatorContainerTest() {
        ElevatorController elevatorControllerMock = mock(ElevatorController.class);
        elevatorControllerMock.deboadingPassenger(passenger);
        verify(elevatorControllerMock).deboadingPassenger(passenger);
        verifyNoMoreInteractions(elevatorControllerMock);

    }
}