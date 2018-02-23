package initializer;

import org.junit.Before;
import org.junit.Test;

import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;

public class ConfigReaderTest {

    private ResourceBundle config;
    private ConfigReader configReader;

    @Before
    public void setUp() {
        this.config = ResourceBundle.getBundle("config");
        this.configReader = new ConfigReader();
    }

    @Test(expected = IllegalArgumentException.class)
    public void floorNumberIsNotNegativeValueTest() {
        configReader.validationNotNegativeValue(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void valueIsNotNegativeValueTest() {
        configReader.validationNotNegativeValue(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void valueIsNotEqualZero() {
        configReader.validationNotEqualZero(0);
    }

    @Test
    public void floorNumberEqualPresetValueTest() {
        int floorNumber = Integer.parseInt(config.getString("floorsNumber"));
        assertEquals(configReader.getFLOORS_NUMBER(), floorNumber);
    }

    @Test
    public void elevatorCapacityEqualPresetValueTest() {
        int elevatorCapacity = Integer.parseInt(config.getString("elevatorCapacity"));
        assertEquals(configReader.getELEVATOR_CAPACITY(), elevatorCapacity);
    }

    @Test
    public void passengersNumberEqualPresetValueTest() {
        int passengersNumber = Integer.parseInt(config.getString("passengersNumber"));
        assertEquals(configReader.getPASSENGERS_NUMBER(), passengersNumber);

    }
}