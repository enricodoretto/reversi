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
    @CsvSource({"1,A,1A", "5,E,5E", "3,D,3D", "8,H,8H"})
    void ifAValidInputStringIsValidCoordinate(int row, char column, String inputCoordinate){
        Coordinate coordinate = new Coordinate(row, column);
        assertEquals(coordinate, Coordinate.parseCoordinate(inputCoordinate));
    }



}
