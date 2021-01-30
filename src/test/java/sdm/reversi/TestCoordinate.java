package sdm.reversi;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class TestCoordinate {
    @ParameterizedTest
    @CsvSource({"1, A, 0, 0", "2, D, 1, 3", "3, C, 2, 2" , "28,Z,27,25"})
    public void hasSameRowAndColumnAsCreated(int row, char column, int boardRow, int boardColumn){
        Coordinate coordinate = new Coordinate(row, column);
        assertAll(
                () -> assertEquals(boardRow, coordinate.getRow()),
                () -> assertEquals(boardColumn, coordinate.getColumn())
        );
    }

    @ParameterizedTest
    @CsvSource({"1,A,1A", "5,E,5E", "3,D,3D", "8,H,8H", "28,H,28H", "29,D,29D"})
    void ifAValidInputStringIsValidCoordinate(int row, char column, String inputCoordinate){
        Coordinate coordinate = new Coordinate(row, column);
        assertEquals(coordinate, new Coordinate(inputCoordinate));
    }
}
