package sdm.reversi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class TestCoordinate {
    @ParameterizedTest
    @CsvSource({"1, A, 0, 0", "2, D, 1, 3", "3, C, 2, 2"})
    public void hasSameRowAndColumnAsCreated(int row, char column, int boardRow, int boardColumn){
        Coordinate coordinate = new Coordinate(row, column);
        assertAll(
                () -> assertEquals(boardRow, coordinate.getRow()),
                () -> assertEquals(boardColumn, coordinate.getColumn())
        );
    }

    @ParameterizedTest
    @CsvSource({"1,A,1A", "5,E,5E", "3,D,3D", "8,H,8H"})
    void ifAValidInputStringIsValidCoordinate(int row, char column, String inputCoordinate){
        Coordinate coordinate = new Coordinate(row, column);
        assertEquals(coordinate, Coordinate.parseCoordinate(inputCoordinate));
    }

    @Test
    void checkIf1AStringIsValidCoordinateUsingStringConstructor(){
        Coordinate coordinate = new Coordinate("1A");
        assertAll(
                () -> assertEquals(0, coordinate.getRow()),
                () -> assertEquals(0, coordinate.getColumn())
        );
    }
}
