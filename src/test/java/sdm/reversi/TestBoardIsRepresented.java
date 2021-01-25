package sdm.reversi;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBoardIsRepresented {

    @Test
    public void emptyBoardRepresentedAsString(){
        String emptyBoard = """
                --------
                --------
                --------
                --------
                --------
                --------
                --------
                --------""";
        Board board = new Board();
        assertEquals(emptyBoard, board.toString());
    }

    @Test
    public void boardWithWhiteDiskInCell1ARepresentedAsString(){
        String emptyBoard = """
                W-------
                --------
                --------
                --------
                --------
                --------
                --------
                --------""";
        Board board = new Board();
        board.putDisk(Disk.Color.WHITE,new Coordinate("1A"));
        assertEquals(emptyBoard, board.toString());
    }

    @Test
    public void boardWithWhiteDiskInCell1AAndBlackDiskInCell8HRepresentedAsString(){
        String emptyBoard = """
                W-------
                --------
                --------
                --------
                --------
                --------
                --------
                -------B""";
        Board board = new Board();
        board.putDisk(Disk.Color.WHITE,new Coordinate("1A"));
        board.putDisk(Disk.Color.BLACK,new Coordinate("8H"));
        assertEquals(emptyBoard, board.toString());
    }

}
