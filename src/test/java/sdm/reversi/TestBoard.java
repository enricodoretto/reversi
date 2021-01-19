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

    @Test
    public void putDiskInNonEmpty1ACell(){
        Board board = new Board();
        Disk disk = new Disk(Disk.Color.BLACK);
        board.putDisk(disk,1,'A');
        Disk duplicatedDisk = new Disk(Disk.Color.BLACK);
        assertFalse(board.putDisk(duplicatedDisk,1,'A'));

    }

}
