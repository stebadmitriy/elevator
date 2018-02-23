package beans.buildingComponents.elevator;

import beans.buildingComponents.Floor;
import beans.passengers.Passenger;
import beans.passengers.TransportationState;
import beans.passengers.TransportationTask;
import mediator.Mediator;
import org.apache.log4j.Logger;
import validation.ValidationResults;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Class ElevatorContainer is class of elevator management.
 * <p>
 * It create TransportationTasks  from the number of passengers on the current floor.
 * Tasks are created under the condition that there is free space in the elevator
 * and that the direction of movement of the elevator coincides
 * with the direction of travel of the passenger.
 * <p>
 */


public class ElevatorController implements Runnable, Mediator {
    private Elevator elevator;
    private List<Floor> floors;

    private int passengersTotalNumber;
    private int passengersNumberAwaitingTransportation;
    private final int NUMBER_THREADS = 100;
    private final int NO_PASSENGER_FOR_TRANSPORTATION = 0;

    /**
     * A queue of passengers to enter the elevator
     */
    private boolean isFullQueueToElevator = false;
    /**
     * A queue of passengers to exit the elevator
     */
    private boolean isFullQueueFromElevator = false;

    private ExecutorService es;
    private Lock elevatorLock = new ReentrantLock();

    /**
     * Permission to exit the elevator
     */
    private Condition leaveElevatorPermission = elevatorLock.newCondition();
    /**
     * Permission to move the elevator to the next floor
     */
    private Condition goNextFloorPermission = elevatorLock.newCondition();

    private final Logger LOG = Logger.getLogger(ElevatorController.class);

    public ElevatorController(Elevator elevator, List<Floor> floors, int passengersNumber) {
        this.elevator = elevator;
        this.floors = floors;
        this.passengersTotalNumber = passengersNumber;
        this.passengersNumberAwaitingTransportation = passengersNumber;
        this.es = Executors.newFixedThreadPool(NUMBER_THREADS);
    }

    private void moveElevator() {
        LOG.info("STARTING_TRANSPORTATION");
        while (passengersNumberAwaitingTransportation > NO_PASSENGER_FOR_TRANSPORTATION
                || !elevator.getElevatorContainer().isEmpty()) {
            LOG.info("\n");

            /*Assume that if there are passengers in the elevator,
            then anybody of them needs to get out of the elevator*/
            if (!elevator.getElevatorContainer().isEmpty()) {
                isFullQueueFromElevator = true;
            }
            elevatorLock.lock();
            try {
                /*Inform the passenger that he can leave elevator*/
                leaveElevatorPermission.signalAll();

                /*If there is an opportunity, then we create tasks*/
                createListTransportationTaskOnFloor();

                /*The elevator is waiting until the queues of passengers to the entrance
                 * and exit will become empty*/
                while (isFullQueueToElevator || isFullQueueFromElevator) {
                    goNextFloorPermission.await();
                }
                goNextFloor();


            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                elevatorLock.unlock();
            }
        }
        LOG.info("COMPLETION_TRANSPORTATION");
        es.shutdown();
        validationResult();
    }

    private void goNextFloor() {
        int currentFloor = elevator.getCurrentFloor();
        boolean directionMovement = elevator.isDirectionMovement();
        int previousFloor;

        /*Set the next floor*/
        currentFloor += directionMovement ? 1 : -1;
        previousFloor = directionMovement ? currentFloor - 1 : currentFloor + 1;
        elevator.setCurrentFloor(currentFloor);

        /*Set the direction movement*/
        if (currentFloor == 0 || currentFloor == floors.size() - 1) {
            changeDirection();
        }
        LOG.info("MOVING_ELEVATOR (from floor - " + (previousFloor + 1) + " to floor - " + (currentFloor + 1));
    }

    private void changeDirection() {
        boolean directionMovement = elevator.isDirectionMovement();
        elevator.setDirectionMovement(!directionMovement);
    }

    /**
     * The method is called when the Transportation task is created, if there is an empty seat in the elevator
     * and the direction of movement of the passenger and the elevator are the same.
     */
    public void boadingPassenger(Passenger passenger) {
        elevatorLock.lock();
        try {
            if (elevator.addPassengerToElevatorContainer(passenger)) {
                LOG.info("BOADING_OF_PASSENGER (passengerID = " + passenger.getPassengerID() + " on floor - " + (elevator.getCurrentFloor() + 1));
                int currentFloor = elevator.getCurrentFloor();

                /*Remove passenger from dispatch container*/
                floors.get(currentFloor).removeDispatchFloorContainer(passenger);

                /*Reduce the number of passengers waiting for transportation*/
                passengersNumberAwaitingTransportation--;
            }
        } finally {
            elevatorLock.unlock();
        }
    }

    /**
     * The method allows  to move the passenger from the elevator cabin to the destination floor
     */
    @Override
    public void deboadingPassenger(Passenger passenger) {
        elevatorLock.lock();
        try {
            /*Check for equality of floors*/
            while (!checkEqualDestinationFloorPassengerAndCurrentFloorElevator(passenger)) {
                isFullQueueFromElevator = false;
                goNextFloorPermission.signalAll();
                leaveElevatorPermission.await();
            }
            if (elevator.removePassengerFromElevatorContainer(passenger)) {
                passenger.setState(TransportationState.COMPLETED);
                LOG.info("DEBOADING_OF_PASSENGER (passengerID = " + passenger.getPassengerID() + " on floor - " + (elevator.getCurrentFloor() + 1));

                /*Add the passenger to the arrival container*/
                int currentFloor = elevator.getCurrentFloor();
                List<Passenger> arrivalFloorContainer = floors.get(currentFloor).getArrivalFloorContainer();
                arrivalFloorContainer.add(passenger);
                isFullQueueFromElevator = false;
                goNextFloorPermission.signalAll();
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            elevatorLock.unlock();
        }
    }

    @Override
    public void run() {
        moveElevator();
    }

    /**
     * The method checks the direction movement of the passenger and elevator
     */
    private boolean checkEqualDirectionMovementPassengerAndElevator(Passenger passenger) {
        boolean directionMovementPassenger = passenger.isDirectionMovement();
        boolean directionMovementElevator = elevator.isDirectionMovement();
        return directionMovementPassenger == directionMovementElevator;
    }

    /**
     * The method checks the equality of the current floor of the elevator and the destination floor of the passenger
     */
    private boolean checkEqualDestinationFloorPassengerAndCurrentFloorElevator(Passenger passenger) {
        int destinationStoryPassenger = passenger.getDestinationFloor();
        int currentStoryElevator = elevator.getCurrentFloor();
        return destinationStoryPassenger == currentStoryElevator;
    }


    /**
     * The method creates list of Transportation tasks on current floor
     */
    private void createListTransportationTaskOnFloor() {
        int currentFloor = elevator.getCurrentFloor();
        List<Passenger> passengers = floors.get(currentFloor).getDispatchFloorContainer();
        for (Passenger passenger : passengers) {
            boolean equalDirectionMovement = checkEqualDirectionMovementPassengerAndElevator(passenger);
            elevatorLock.lock();
            try {
                int elevatorContainerSize = elevator.getElevatorContainer().size();
                /*Tasks are created under the condition that there is free space in the elevator
                 and that the direction of movement of the elevator coincides
                 with the direction of travel of the passenger*/
                if (equalDirectionMovement && elevatorContainerSize < elevator.getElevatorCapacity()) {
                    TransportationTask task = new TransportationTask(passenger, this);
                    passenger.setState(TransportationState.IN_PROGRESS);

                    isFullQueueToElevator = true;
                    es.execute(task);
                    boadingPassenger(passenger);
                }

            } finally {
                elevatorLock.unlock();
            }
        }
        isFullQueueToElevator = false;
        goNextFloorPermission.signalAll();
    }

    private void validationResult() {
        LOG.info("\n\n");
        LOG.info("VALIDATION OF RESULTS");
        ValidationResults validationResults = new ValidationResults();
        LOG.info("\n");
        validationResults.validateDispatchFloorContainer(floors);
        LOG.info("\n");
        validationResults.validateElevatorContainer(elevator);
        LOG.info("\n");
        validationResults.validateTransportationState(floors);
        LOG.info("\n");
        validationResults.validateDestinationFloor(floors);
        LOG.info("\n");
        validationResults.validateWhetherAllArrived(floors, passengersTotalNumber);
    }

}

