package beans.buildingComponents;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class BuildingTest {
    private Building buildingMock;

    @Before
    public void setUp() {
        buildingMock = Mockito.mock(Building.class);
    }

    @Test
    public void addListFloorsTest() {
        buildingMock.createListFloors(1);
        verify(buildingMock).createListFloors(1);
        verifyNoMoreInteractions(buildingMock);
    }
}