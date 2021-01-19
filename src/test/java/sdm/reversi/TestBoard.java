package sdm.reversi;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBoard {

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
