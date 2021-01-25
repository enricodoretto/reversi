package sdm.reversi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class TestBoard {

    @ParameterizedTest
    @CsvSource({"0A", "2Z", "10L"})
    public void checkingIfEmptyCellOutsideBoardIsNotPossible(Coordinate coordinate){
        Board board = new Board();
        assertThrows(IllegalArgumentException.class, () -> board.isCellEmpty(coordinate));
    }

    @ParameterizedTest
    @CsvSource({"1A", "2A", "5C"})
    public void putDiskInEmptyCell(Coordinate coordinate){
        Board board = new Board();
        assertTrue(board.putDisk(Disk.Color.BLACK, coordinate));
    }

    @ParameterizedTest
    @CsvSource({"1A", "2A", "5C"})
    public void putDiskInEmptyCellDelegatingDiskCreation(Coordinate coordinate){
        Board board = new Board();
        assertTrue(board.putDisk(Disk.Color.BLACK, coordinate));
    }


    @ParameterizedTest
    @CsvSource({"1A", "5F", "8H"})
    public void cantPutDiskInNonEmptyCell(Coordinate coordinate){
        Board board = new Board();
        board.putDisk(Disk.Color.BLACK,coordinate);
        assertFalse(board.putDisk(Disk.Color.BLACK, coordinate));
    }

    @ParameterizedTest
    @CsvSource({"1A", "5F", "8H"})
    public void cantPutDiskInNonEmptyCellDelegatingDiskCreation(Coordinate coordinate){
        Board board = new Board();
        board.putDisk(Disk.Color.BLACK,coordinate);
        assertFalse(board.putDisk(Disk.Color.BLACK, coordinate));
    }

    @ParameterizedTest
    @CsvSource({"11C", "0A", "4I"})
    public void cantPutDiskInCellOutOfBoard(Coordinate coordinate){
        Board board = new Board();
        assertThrows(IllegalArgumentException.class, () -> board.putDisk(Disk.Color.BLACK,coordinate));
    }

    @ParameterizedTest
    @CsvSource({"11C", "0A", "4I"})
    public void cantPutDiskInCellOutOfBoardDelegatingDiskCreation(Coordinate coordinate){
        Board board = new Board();
        assertThrows(IllegalArgumentException.class, () -> board.putDisk(Disk.Color.BLACK,coordinate));
    }


}
