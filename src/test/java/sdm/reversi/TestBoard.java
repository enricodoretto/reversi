package sdm.reversi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class TestBoard {

    @ParameterizedTest
    @CsvSource({"1, A", "2, A", "5, C"})
    public void putDiskInEmptyCell(int row, char column){
        Board board = new Board();
        Disk disk = new Disk(Disk.Color.BLACK);
        assertTrue(board.putDisk(disk, row, column));
    }

    @ParameterizedTest
    @CsvSource({"1, A", "5, F", "8, H"})
    public void cantPutDiskInNonEmptyCell(int row, char column){
        Board board = new Board();
        Disk disk = new Disk(Disk.Color.BLACK);
        board.putDisk(disk,row,column);
        Disk duplicatedDisk = new Disk(Disk.Color.BLACK);
        assertFalse(board.putDisk(duplicatedDisk, row, column));
    }

    @Test
    public void cantPutDiskInCell11C(){
        Board board = new Board();
        Disk disk = new Disk(Disk.Color.BLACK);
        assertThrows(IllegalArgumentException.class, () -> board.putDisk(disk,11,'C'));
    }

}
