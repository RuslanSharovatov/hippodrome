import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HorseTest {
    @Test
    void constructor_nameIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Horse(null, 0, 0));
        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n"})
    void constructor_nameIsEmpty(String str) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(str, 0, 0));
    }

    @Test
    void constructor_speedIsNegative() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Horse("name", -1, 0));
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    void constructor_distanceIsNegative() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Horse("name", 0, -1));
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    void getParametersTest() {
        assertEquals("name", (new Horse("name", 0, 0).getName()));
        assertEquals(1, (new Horse("name", 1, 0).getSpeed()));
        assertEquals(0, (new Horse("name", 1, 0).getDistance()));
    }

    @Test
    void moveTest() {
        try(MockedStatic<Horse> horse = Mockito.mockStatic(Horse.class)) {
            new Horse("name", 28, 154).move();
            horse.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.1, 0.2, 0.3, 111.111, 0.8})
    void moveDistanceTest(double random) {
        try(MockedStatic<Horse> mockedHorse = Mockito.mockStatic(Horse.class)) {
            Horse horse = new Horse("name", 28, 154);
            mockedHorse.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(random);
            horse.move();
            assertEquals(154 + 28 * random, horse.getDistance());
        }
    }
}
