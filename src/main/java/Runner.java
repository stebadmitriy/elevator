import beans.buildingComponents.Building;
import beans.buildingComponents.Floor;
import beans.buildingComponents.elevator.Elevator;
import beans.buildingComponents.elevator.ElevatorController;
import initializer.ConfigReader;
import initializer.CreatorListPassengers;

import java.util.List;


public class Runner {

    public static void main(String[] args) {



        ConfigReader configReader = new ConfigReader();
        int floorsNumber = configReader.getFLOORS_NUMBER();
        int elevatorCapacity = configReader.getELEVATOR_CAPACITY();
        int passengersNumber = configReader.getPASSENGERS_NUMBER();


        Building building = new Building(elevatorCapacity, floorsNumber);
        List<Floor> floors = building.getFloors();

        Elevator elevator = building.getElevator();
        ElevatorController elevatorController = new ElevatorController(elevator, floors, passengersNumber);

        CreatorListPassengers creatorListPassengers = new CreatorListPassengers();
        creatorListPassengers.createListPassengers(floors, passengersNumber);

        new Thread(elevatorController).start();
    }
}
