package beans.buildingComponents;

import beans.passengers.Passenger;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The Floor class stores the floor number, and also provides methods for adding / removing passengers
 * to the dispatch and arrival containers
 */

public class Floor {
    private int floorNumber;
    private List<Passenger> dispatchFloorContainer = new CopyOnWriteArrayList();
    private List<Passenger> arrivalFloorContainer = new CopyOnWriteArrayList();

    public Floor(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public void addDispatchFloorContainer(Passenger passenger) {
        dispatchFloorContainer.add(passenger);
    }

    public void removeDispatchFloorContainer(Passenger passenger) {
        dispatchFloorContainer.remove(passenger);
    }

    public List<Passenger> getDispatchFloorContainer() {
        return dispatchFloorContainer;
    }

    public List<Passenger> getArrivalFloorContainer() {
        return arrivalFloorContainer;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    @Override
    public String toString() {
        return "Floor{" +
                "floorNumber=" + floorNumber +
                ", dispatchFloorContainer=" + dispatchFloorContainer +
                ", arrivalFloorContainer=" + arrivalFloorContainer +
                '}';
    }
}
