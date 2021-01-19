package sdm.reversi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestBoard {

    @ParameterizedTest
    @CsvSource({"1, A", "2, A", "5, C"})
    public void putDiskInEmptyCell(int row, char column){
        Board board = new Board();
        Disk disk = new Disk(Disk.Color.BLACK);
        assertTrue(board.putDisk(disk, row, column));
    }

    @Test
    public void putDiskIn1AEmptyCell(){
        Board board = new Board();
        Disk disk = new Disk(Disk.Color.BLACK);
        assertEquals(true, board.putDisk(disk,1,'A'));
    }

    @Test
    public void putDiskIn4AEmptyCell(){
        Board board = new Board();
        Disk disk = new Disk(Disk.Color.BLACK);
        assertEquals(true, board.putDisk(disk,4,'A'));
    }

    @Test
    public void putDiskIn5CEmptyCell(){
        Board board = new Board();
        Disk disk = new Disk(Disk.Color.BLACK);
        assertEquals(true, board.putDisk(disk,5,'C'));
    }

}
