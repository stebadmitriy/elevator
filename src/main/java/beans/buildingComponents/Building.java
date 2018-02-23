package beans.buildingComponents;

import beans.buildingComponents.elevator.Elevator;

import java.util.ArrayList;
import java.util.List;

/**
 * Class The building stores as a field a collection of floors and elevator.
 * Has a method for creating this collection.
 */

public class Building {
    private List<Floor> floors;
    private Elevator elevator;

    public Building(int elevatorCapacity, int floorsNumber) {
        this.elevator = new Elevator(elevatorCapacity);
        createListFloors(floorsNumber);
    }

    public List<Floor> getFloors() {
        return floors;
    }

    public Elevator getElevator() {
        return elevator;
    }

    public void createListFloors(int floorsNumber) {
        floors = new ArrayList<>();
        for (int i = 0; i < floorsNumber; i++) {
            floors.add(new Floor(i));
        }
    }

    @Override
    public String toString() {
        return "Building{" +
                "floors=" + floors.size() +
                ", elevator=" + elevator +
                '}';
    }
}
