package initializer;

import java.util.ResourceBundle;

/**
 * Class ConfigReader is intended for reading configuration parameters from the file config.properties.
 * Also contains 2 methods of data validation
 */

public class ConfigReader {
    private final int FLOORS_NUMBER;
    private final int ELEVATOR_CAPACITY;
    private final int PASSENGERS_NUMBER;
    private final int ZERO_VALUE = 0;

    public ConfigReader() {
        ResourceBundle config = ResourceBundle.getBundle("config");
        this.FLOORS_NUMBER = Integer.parseInt(config.getString("floorsNumber"));
        validationNotNegativeValue(FLOORS_NUMBER);
        this.ELEVATOR_CAPACITY = Integer.parseInt(config.getString("elevatorCapacity"));
        validationNotNegativeValue(ELEVATOR_CAPACITY);
        validationNotEqualZero(ELEVATOR_CAPACITY);
        this.PASSENGERS_NUMBER = Integer.parseInt(config.getString("passengersNumber"));
        validationNotNegativeValue(PASSENGERS_NUMBER);
        validationNotEqualZero(PASSENGERS_NUMBER);
    }

    public int getFLOORS_NUMBER() {
        return FLOORS_NUMBER;
    }

    public int getELEVATOR_CAPACITY() {
        return ELEVATOR_CAPACITY;
    }

    public int getPASSENGERS_NUMBER() {
        return PASSENGERS_NUMBER;
    }

    public void validationNotNegativeValue(int value) {
        if (value < ZERO_VALUE) {
            throw new IllegalArgumentException("The value can not be negative");
        }
    }

    public void validationNotEqualZero(int value) {
        if (value == ZERO_VALUE) {
            throw new IllegalArgumentException("The value can not be zero");
        }
    }

}
