package sdm.reversi;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class TestCoordinate {
    @ParameterizedTest
    @CsvSource({"0, 0", "1, 3", "2, 2", "27,25"})
    public void hasSameRowAndColumnAsCreated(int row, int column){
        Coordinate coordinate = new Coordinate(row, column);
        assertAll(
                () -> assertEquals(row, coordinate.getRow()),
                () -> assertEquals(column, coordinate.getColumn())
        );
    }

    @ParameterizedTest
    @CsvSource({"0,0,1A", "4,4,5E", "2,3,3d", "7,7,8H", "27,7,28H", "28,3,29d", "26,26,27AA","30,30,31AE","99,0,100A"})
    void ifAValidInputStringIsValidCoordinate(int row, int column, String inputCoordinate){
        Coordinate coordinate = new Coordinate(row, column);
        assertEquals(coordinate, new Coordinate(inputCoordinate));
    }

    @ParameterizedTest
    @CsvSource({"z2", "az6", "1234", "jg", "w3r"})
    void failsIfNotValidInputString(String coordinate){
        assertThrows(IllegalArgumentException.class, () -> new Coordinate(coordinate));
    }

    @ParameterizedTest
    @CsvSource({"279ABBA","31AE","100A"})
    void isCorrectlyRepresentedAsString(String coordinate){
        assertEquals(coordinate, new Coordinate(coordinate).toString());
    }

    @ParameterizedTest
    @CsvSource({"1A, RIGHT, 1B", "1B, LEFT, 1A", "2A, UP, 1A", "1A, DOWN, 2A",
            "1A, DOWN_RIGHT, 2B", "2B, DOWN_LEFT, 3A", "2A, UP_RIGHT, 1B", "3B, UP_LEFT, 2A"})
    void isCorrectlyShiftedInADirection(Coordinate initialCoordinate, ShiftDirection shiftDirection, Coordinate shiftedCoordinate){
        assertEquals(shiftedCoordinate, initialCoordinate.getShiftedCoordinate(shiftDirection));
    }


}
