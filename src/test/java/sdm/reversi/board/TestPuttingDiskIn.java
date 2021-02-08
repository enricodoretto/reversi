package sdm.reversi.board;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class TestPuttingDiskIn {

    @ParameterizedTest
    @CsvSource({"8,1A", "8,5C", "4,4D", "4,1C", "26,26Z", "26,15C"})
    public void emptyCellInsideBoardSucceeds(int boardSize, Coordinate coordinate){
        Board board = new Board(boardSize);
        assertDoesNotThrow(() -> board.putDisk(Disk.Color.BLACK, coordinate));
    }

    @ParameterizedTest
    @CsvSource({"8,1A", "8,8H", "4,1C", "4,2B", "22,22H", "22,16D"})
    public void nonEmptyCellInsideBoardFails(int boardSize, Coordinate coordinate){
        Board board = new Board(boardSize);
        board.putDisk(Disk.Color.BLACK,coordinate);
        assertThrows(IllegalArgumentException.class ,() ->board.putDisk(Disk.Color.BLACK, coordinate));
    }

    @ParameterizedTest
    @CsvSource({"8,11C", "8,0A", "4,5D", "4,4E", "26,27A", "26,0Z"})
    public void cellOutsideBoardFails(int boardSize, Coordinate coordinate){
        Board board = new Board(boardSize);
        assertThrows(IllegalArgumentException.class, () -> board.putDisk(Disk.Color.BLACK,coordinate));
    }
    
}
