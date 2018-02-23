package validation;

import beans.buildingComponents.Floor;
import beans.buildingComponents.elevator.Elevator;
import beans.passengers.Passenger;
import beans.passengers.TransportationState;
import org.apache.log4j.Logger;

import java.util.List;

public class ValidationResults {
    private final Logger LOG = Logger.getLogger(ValidationResults.class);

    public void validateDispatchFloorContainer(List<Floor> floors) {
        for (Floor floor : floors) {
            int dispatchContainerSize = floor.getDispatchFloorContainer().size();
            if (dispatchContainerSize > 0) {
                try {
                    throw new ValidationException("Dispatch Floor Container on " + (floor.getFloorNumber() + 1) + " floor isn't empty");
                } catch (ValidationException e) {
                    LOG.error(e.getMessage());
                }
            } else {
                LOG.info("Floor " + (floor.getFloorNumber() + 1) + ": " + dispatchContainerSize + " passengers are into dispatch container");
            }
        }
    }

    public void validateElevatorContainer(Elevator elevator) {
        int elevatorSize = elevator.getElevatorContainer().size();
        if (elevatorSize > 0) {
            try {
                throw new ValidationException("Elevator Container isn't empty: " + elevatorSize);
            } catch (ValidationException e) {
                LOG.error(e.getMessage());
            }
        } else {
            LOG.info("Elevator Container is empty: " + elevatorSize + " passengers");
        }
    }

    public void validateTransportationState(List<Floor> floors) {
        for (Floor floor : floors) {
            List<Passenger> arrivalContainer = floor.getArrivalFloorContainer();
            for (Passenger passenger : arrivalContainer) {
                TransportationState state = passenger.getState();
                int passengerId = passenger.getPassengerID();
                if (state != TransportationState.COMPLETED) {
                    try {
                        throw new ValidationException("Passenger id = " + passengerId + ": has got state is " + state);
                    } catch (ValidationException e) {
                        LOG.error(e.getMessage());
                    }
                } else {
                    LOG.info("Passenger id = " + passengerId + " : transportation state is " + state);
                }
            }
        }
    }

    public void validateDestinationFloor(List<Floor> floors) {
        for (Floor floor : floors) {
            List<Passenger> arrivalContainer = floor.getArrivalFloorContainer();
            for (Passenger passenger : arrivalContainer) {
                int destinationFloor = passenger.getDestinationFloor();
                int passengerId = passenger.getPassengerID();
                int floorNumber = floor.getFloorNumber();
                if (destinationFloor != floorNumber) {
                    try {
                        throw new ValidationException("Passenger id = " + passengerId + "has got destination floor  " + (destinationFloor + 1) + ". Arrived to " + (floorNumber + 1));
                    } catch (ValidationException e) {
                        LOG.error(e.getMessage());
                    }
                } else {
                    LOG.info("Passenger id = " + passengerId + " : destination floor " + (destinationFloor + 1) + " = arrival floor " + (floorNumber + 1));
                }
            }
        }
    }

    public void validateWhetherAllArrived(List<Floor> floors, int passengerNumber) {
        int passengerAllFloor = 0;
        for (Floor floor : floors) {
            passengerAllFloor += floor.getArrivalFloorContainer().size();
        }
        if (passengerAllFloor != passengerNumber) {
            try {
                throw new ValidationException("The number of passengers arriving (" + passengerAllFloor + ") is not equal to the total number (" + passengerNumber + ")");
            } catch (ValidationException e) {
                LOG.error(e.getMessage());
            }
        } else {
            LOG.info("The number of passengers arriving " + passengerAllFloor + ". All passenger number " + passengerNumber);
        }
    }


}
