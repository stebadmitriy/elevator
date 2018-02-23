package beans.passengers;

/**
 * Class TransportationTask determines the behavior of the passenger during its transportation.
 * Through this class the passenger can notify the class ElevatorController about the desire
 * to exit from the elevator cabin.
 * <p>
 * An instance of this class is created in the ElevatorController class in
 * the createListTransportationTaskOnFloor() method. Thus, tasks are not created that will be idle
 * when the passenger can't added to the elevator cabin. TransportationTask can be created only
 * under the conditions that the capacity of the elevator cabin allows to add a passenger,
 * and that the direction of the movement of the elevator and passenger coincide.
 */

import mediator.Mediator;

public class TransportationTask implements Runnable {
    private Passenger passenger;
    private Mediator mediator;


    public TransportationTask(Passenger passenger, Mediator mediator) {
        this.passenger = passenger;
        this.mediator = mediator;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    @Override
    public void run() {
        mediator.deboadingPassenger(passenger);
        passenger.setState(TransportationState.COMPLETED);

    }

    @Override
    public String toString() {
        return "TransportationTask{" +
                "passenger=" + passenger;
    }
}
