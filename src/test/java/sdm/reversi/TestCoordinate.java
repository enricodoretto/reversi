package sdm.reversi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    void hasRow1AndColumnAAsCreated() {
        Coordinate coordinate = new Coordinate(1, 'A');
        assertAll(
                () -> assertEquals(1, coordinate.getRow()),
                () -> assertEquals('A', coordinate.getColumn())
        );
    }

    @Test
    void hasRow2AndColumnDAsCreated() {
        Coordinate coordinate = new Coordinate(2, 'D');
        assertAll(
                () -> assertEquals(2, coordinate.getRow()),
                () -> assertEquals('D', coordinate.getColumn())
        );
    }

    @Test
    void hasRow3AndColumnCAsCreated() {
        Coordinate coordinate = new Coordinate(3, 'C');
        assertAll(
                () -> assertEquals(3, coordinate.getRow()),
                () -> assertEquals('C', coordinate.getColumn())
        );
    }


}
