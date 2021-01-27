package sdm.reversi.board;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import sdm.reversi.Board;
import sdm.reversi.Coordinate;
import sdm.reversi.Disk;

import static org.junit.jupiter.api.Assertions.*;

public class TestPuttingDiskIn {

    @ParameterizedTest
    @CsvSource({"1A", "2A", "5C"})
    public void emptyCellInsideBoardSucceeds(Coordinate coordinate){
        Board board = new Board();
        assertTrue(board.putDisk(Disk.Color.BLACK, coordinate));
    }

    @ParameterizedTest
    @CsvSource({"1A", "5F", "8H"})
    public void nonEmptyCellInsideBoardFails(Coordinate coordinate){
        Board board = new Board();
        board.putDisk(Disk.Color.BLACK,coordinate);
        assertFalse(board.putDisk(Disk.Color.BLACK, coordinate));
    }

    @ParameterizedTest
    @CsvSource({"11C", "0A", "4I"})
    public void cellOutsideBoardFails(Coordinate coordinate){
        Board board = new Board();
        assertThrows(IllegalArgumentException.class, () -> board.putDisk(Disk.Color.BLACK,coordinate));
    }

}
