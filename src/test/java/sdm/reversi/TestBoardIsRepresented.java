package sdm.reversi;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBoardIsRepresented {

    @Test void asStringOfDashesReadFromFileWhenEmpty() throws IOException, URISyntaxException {
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource("empty8x8Board");
        String emptyBoard = Files.readString(Paths.get(boardFile.toURI()));

        Board board = new Board();
        assertEquals(emptyBoard, board.toString());
    }

    @Test
    public void asStringOfDashesReadFromFileWithInitialWWhenThereIsOnlyOneWhiteDiskIn1A() throws URISyntaxException, IOException {

        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource("whiteIn1A8x8Board");
        String boardRepresentation = Files.readString(Paths.get(boardFile.toURI()));


        Board board = new Board();
        board.putDisk(Disk.Color.WHITE,new Coordinate("1A"));
        assertEquals(boardRepresentation, board.toString());
    }

    @Test
    public void asStringOfDashesWhenEmpty(){
        String emptyBoard = "--------\n" +
                            "--------\n" +
                            "--------\n" +
                            "--------\n" +
                            "--------\n" +
                            "--------\n" +
                            "--------\n" +
                            "--------" ;
        Board board = new Board();
        assertEquals(emptyBoard, board.toString());
    }

    @Test
    public void asStringOfDashesWithInitialWWhenThereIsOnlyOneWhiteDiskIn1A(){
        String boardRepresentation = "W-------\n" +
                                     "--------\n" +
                                     "--------\n" +
                                     "--------\n" +
                                     "--------\n" +
                                     "--------\n" +
                                     "--------\n" +
                                     "--------" ;
        Board board = new Board();
        board.putDisk(Disk.Color.WHITE,new Coordinate("1A"));
        assertEquals(boardRepresentation, board.toString());
    }

    @Test
    public void asStringOfDashesWithInitialWAndFinalBWhenThereIsAWhiteDiskIn1AAndABlackDiskIn8H(){
        String boardRepresentation = "W-------\n" +
                                     "--------\n" +
                                     "--------\n" +
                                     "--------\n" +
                                     "--------\n" +
                                     "--------\n" +
                                     "--------\n" +
                                     "-------B"  ;
        Board board = new Board();
        board.putDisk(Disk.Color.WHITE,new Coordinate("1A"));
        board.putDisk(Disk.Color.BLACK,new Coordinate("8H"));
        assertEquals(boardRepresentation, board.toString());
    }

}
