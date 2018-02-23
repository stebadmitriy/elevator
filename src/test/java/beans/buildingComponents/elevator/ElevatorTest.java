package beans.buildingComponents.elevator;

import beans.passengers.Passenger;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class ElevatorTest {
    private Elevator elevatorMock;
    private Passenger passenger;

    @Before
    public void setUp() throws Exception {
        elevatorMock = mock(Elevator.class);
        passenger = new Passenger(0, 1, 8);
    }

    @Test
    public void addPassengerIntoElevatorContainerTest() {

        elevatorMock.addPassengerToElevatorContainer(passenger);
        verify(elevatorMock).addPassengerToElevatorContainer(passenger);
        verifyNoMoreInteractions(elevatorMock);
    }

    @Test
    public void removePassengerFromElevatorContainerTest() {
        elevatorMock.removePassengerFromElevatorContainer(passenger);
        verify(elevatorMock).removePassengerFromElevatorContainer(passenger);
        verifyNoMoreInteractions(elevatorMock);
    }
}