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

    @Test
    void tenAIsNotValid() {
        assertThrows(IllegalArgumentException.class, () -> new Coordinate(10,'A'));
    }
}
