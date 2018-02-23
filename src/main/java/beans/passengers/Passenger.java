package beans.passengers;

/**
 * Class Passenger keeps in itself the state of the object, such as
 * id, start floor,
 * destination floor,
 * direction of movement (true-up, false-down) and
 * Transportation State.
 * <p>
 */
public class Passenger {
    private int passengerID;
    private int startFloor;
    private int destinationFloor;
    private boolean directionMovement;
    private TransportationState state;


    public Passenger(int passengerID, int startStory, int destinationStory) {
        this.passengerID = passengerID;
        this.startFloor = startStory;
        this.destinationFloor = destinationStory;
        this.state = TransportationState.NOT_STARTED;
        this.directionMovement = (startStory - destinationStory) < 0 ? true : false;

    }

    public int getDestinationFloor() {
        return destinationFloor;
    }

    public boolean isDirectionMovement() {
        return directionMovement;
    }

    public void setState(TransportationState state) {
        this.state = state;
    }

    public int getPassengerID() {
        return passengerID;
    }

    public TransportationState getState() {
        return state;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "passengerID=" + passengerID +
                ", startFloor=" + startFloor +
                ", destinationFloor=" + destinationFloor +
                ", directionMovement=" + directionMovement +
                ", state=" + state +
                '}';
    }
}
