package beans.buildingComponents.elevator;

import beans.passengers.Passenger;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Class Elevator stores
 * the capacity of the elevator,
 * the current floor,
 * the current direction of traffic,
 * the concurrent collection of passengers in the elevator and
 * the starting floor number.
 * <p>
 * Through notification from ElevatorContainer adds and removes passengers from the elevator cabin
 */

public class Elevator {
    private int elevatorCapacity;
    private int currentFloor;
    private boolean directionMovement;
    private BlockingQueue<Passenger> elevatorContainer;
    private final int START_FLOOR = 0;

    public Elevator(int elevatorCapacity) {
        this.elevatorCapacity = elevatorCapacity;
        this.currentFloor = START_FLOOR;
        this.directionMovement = true;
        this.elevatorContainer = new ArrayBlockingQueue<>(elevatorCapacity);
    }

    public boolean addPassengerToElevatorContainer(Passenger passenger) {
        return elevatorContainer.offer(passenger);
    }

    public boolean removePassengerFromElevatorContainer(Passenger passenger) {
        return elevatorContainer.remove(passenger);
    }

    public BlockingQueue<Passenger> getElevatorContainer() {
        return elevatorContainer;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public boolean isDirectionMovement() {
        return directionMovement;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public void setDirectionMovement(boolean directionMovement) {
        this.directionMovement = directionMovement;
    }

    public int getElevatorCapacity() {
        return elevatorCapacity;
    }

    @Override
    public String toString() {
        return "Elevator{" +
                "elevatorCapacity=" + elevatorCapacity +
                ", currentFloor=" + currentFloor +
                ", directionMovement=" + directionMovement +
                '}';
    }
}
