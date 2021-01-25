package sdm.reversi;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBoardIsRepresented {

    @Test
    public void asStringOfDashesWhenEmpty(){
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
    public void asStringOfDashesWithInitialWWhenThereIsOnlyOneWhiteDiskIn1A(){
        String boardRepresentation = """
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
        assertEquals(boardRepresentation, board.toString());
    }

    @Test
    public void asStringOfDashesWithInitialWAndFinalBWhenThereIsAWhiteDiskIn1AAndABlackDiskIn8H(){
        String boardRepresentation = """
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
        assertEquals(boardRepresentation, board.toString());
    }

}
