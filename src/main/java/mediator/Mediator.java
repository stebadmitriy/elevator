package mediator;

import beans.passengers.Passenger;

public interface Mediator {
    void boadingPassenger(Passenger passenger);

    void deboadingPassenger(Passenger passenger);
}
