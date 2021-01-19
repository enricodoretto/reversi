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

    @Test
    void checkIfString1AIsValidCoordinate(){
        Coordinate coordinate = new Coordinate(1, 'A');
        assertEquals(coordinate, Coordinate.parseCoordinate("1A"));
    }

    @Test
    void checkIfString5EIsValidCoordinate(){
        Coordinate coordinate = new Coordinate(5, 'E');
        assertEquals(coordinate, Coordinate.parseCoordinate("5E"));
    }

    @Test
    void checkIfString11AIsNotValidCoordinate(){
        assertThrows(IllegalArgumentException.class, () -> Coordinate.parseCoordinate("11A"));
    }

    @Test
    void checkIfStringA1IsNotValidCoordinate(){
        assertThrows(IllegalArgumentException.class, () -> Coordinate.parseCoordinate("A1"));
    }

    @Test
    void checkIfString321IsNotValidCoordinate(){
        assertThrows(IllegalArgumentException.class, () -> Coordinate.parseCoordinate("321"));
    }

}
