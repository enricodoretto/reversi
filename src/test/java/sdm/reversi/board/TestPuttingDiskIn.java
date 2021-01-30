package sdm.reversi.board;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import sdm.reversi.Board;
import sdm.reversi.Coordinate;
import sdm.reversi.Disk;

import static org.junit.jupiter.api.Assertions.*;

public class TestPuttingDiskIn {

    @ParameterizedTest
    @CsvSource({"8,1A", "8,5C", "4,4D", "4,1C", "26,26Z", "26,15C"})
    public void emptyCellInsideBoardSucceeds(int boardSize, Coordinate coordinate){
        Board board = new Board(boardSize);
        assertTrue(board.putDisk(Disk.Color.BLACK, coordinate));
    }

    @ParameterizedTest
    @CsvSource({"8,1A", "8,8H", "4,1C", "4,2B", "22,22H", "22,16D"})
    public void nonEmptyCellInsideBoardFails(int boardSize, Coordinate coordinate){
        Board board = new Board(boardSize);
        board.putDisk(Disk.Color.BLACK,coordinate);
        assertFalse(board.putDisk(Disk.Color.BLACK, coordinate));
    }

    @ParameterizedTest
    @CsvSource({"11C", "0A", "4I"})
    public void cellOutside8x8BoardFails(Coordinate coordinate){
        Board board = new Board();
        assertThrows(IllegalArgumentException.class, () -> board.putDisk(Disk.Color.BLACK,coordinate));
    }

    @ParameterizedTest
    @CsvSource({"1A", "2A", "4D"})
    public void emptyCellInside4x4BoardSucceeds(Coordinate coordinate){
        Board board = new Board();
        assertTrue(board.putDisk(Disk.Color.BLACK, coordinate));
    }

}
