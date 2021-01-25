package sdm.reversi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBoardIsRepresented {

    @ParameterizedTest
    @CsvSource({"empty8x8Board, 8", "empty4x4Board, 4", "empty16x16Board, 16"})
    void asStringOfDashesWhenEmpty(String fileName, int size) throws IOException, URISyntaxException {
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource(fileName);
        String emptyBoard = Files.readString(Paths.get(boardFile.toURI()));
        Board board = new Board(size);
        assertEquals(emptyBoard, board.toString());
    }

    @Test
    public void asStringOfDashesWithInitialWWhenThereIsOnlyOneWhiteDiskIn1A() throws URISyntaxException, IOException {
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource("whiteIn1A8x8Board");
        String boardRepresentation = Files.readString(Paths.get(boardFile.toURI()));
        Board board = new Board();
        board.putDisk(Disk.Color.WHITE,new Coordinate("1A"));
        assertEquals(boardRepresentation, board.toString());
    }

    @Test
    public void asStringOfDashesWithInitialWWhenThereIsAWhiteDiskIn1AAndABlackDiskIn8H() throws URISyntaxException, IOException {
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource("whiteIn1AblackIn8H8x8Board");
        String boardRepresentation = Files.readString(Paths.get(boardFile.toURI()));
        Board board = new Board();
        board.putDisk(Disk.Color.WHITE,new Coordinate("1A"));
        board.putDisk(Disk.Color.BLACK,new Coordinate("8H"));
        assertEquals(boardRepresentation, board.toString());
    }


}
