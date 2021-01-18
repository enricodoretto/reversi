package sdm.reversi;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCoordinate {
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
}
