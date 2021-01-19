package sdm.reversi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class TestCoordinate {
    @ParameterizedTest
    @CsvSource({"1, A", "2, D", "3, C"})
    public void hasSameRowAndColumnAsCreated(int row, char column){
        Coordinate coordinate = new Coordinate(row, column);
        assertAll(
                () -> assertEquals(row, coordinate.getRow()),
                () -> assertEquals(column, coordinate.getColumn())
        );
    }

    @ParameterizedTest
    @CsvSource({"10, A", "11, A", "12, B"})
    public void withRowGreaterThanEightIsNotValid(int row, char column){
        assertThrows(IllegalArgumentException.class, () -> new Coordinate(row,column));
    }

    @ParameterizedTest
    @CsvSource({"0, A", "-2, A", "-10, B"})
    void withRowSmallerThanOneIsNotValid(int row, char column) {
        assertThrows(IllegalArgumentException.class, () -> new Coordinate(row,column));
    }

    @ParameterizedTest
    @CsvSource({"1, L", "3, è", "6, T"})
    void withColumnNotBetweenAAndHIgnoreCaseIsNotValid(int row, char column) {
        assertThrows(IllegalArgumentException.class, () -> new Coordinate(row,column));
    }

    @ParameterizedTest
    @CsvSource({"1,A,1A", "5,E,5E", "3,D,3D", "8,H,8H"})
    void checkIfStringIsValidStringCoordinate(int row, char column, String inputCoordinate){
        Coordinate coordinate = new Coordinate(row, column);
        assertEquals(coordinate, Coordinate.parseCoordinate(inputCoordinate));
    }

    @ParameterizedTest
    @CsvSource({"A1","11A","321"})
    void checkIfANotValidStringIsNotAValidCoordinate(String inputCoordinate){
        assertThrows(IllegalArgumentException.class, () -> Coordinate.parseCoordinate(inputCoordinate));
    }

}
