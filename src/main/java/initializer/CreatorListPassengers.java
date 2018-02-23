package initializer;

import beans.buildingComponents.Floor;
import beans.passengers.Passenger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**The class CreatorListPassengers is designed to create the floors of destination and
 * destination by means of random numbers*/

public class CreatorListPassengers {
    private Random random;
    public CreatorListPassengers() {
        random = new Random();
    }

    public List<Passenger> createListPassengers(List<Floor> floors, int passengersNumber) {//2
        List<Passenger> passengers = new ArrayList<>();
        int numberFloors = floors.size();


        for (int i = 0; i < passengersNumber; i++) {
            int startFloor = createStartFloor(numberFloors);

            int destinationFloor = createDestinationFloor(numberFloors,startFloor);
            Passenger passenger = new Passenger(i, startFloor, destinationFloor);
            passengers.add(passenger);
            floors.get(startFloor).addDispatchFloorContainer(passenger);
        }
        return passengers;
    }

    protected int createStartFloor(int numberFloors) {
        return random.nextInt(numberFloors);
    }

    protected int createDestinationFloor(int numberFloor, int startFloor){
        int destinationFloor = random.nextInt(numberFloor);
        while (startFloor == destinationFloor) {
            destinationFloor = random.nextInt(numberFloor);
        }
        return destinationFloor;
    }
}
