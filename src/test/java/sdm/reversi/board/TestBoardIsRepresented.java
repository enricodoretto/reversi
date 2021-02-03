package sdm.reversi.board;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import sdm.reversi.Board;
import sdm.reversi.Coordinate;
import sdm.reversi.Disk;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBoardIsRepresented {

    @ParameterizedTest
    @CsvSource({"emptyBoards/empty8x8Board, 8", "emptyBoards/empty4x4Board, 4", "emptyBoards/empty16x16Board, 16"})
    void asStringOfDashesWhenEmpty(String fileName, int size) throws IOException, URISyntaxException {
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource(fileName);
        assert boardFile != null;
        String emptyBoard = Files.readString(Paths.get(boardFile.toURI()));
        Board board = new Board(size);
        assertEquals(emptyBoard, board.toString());
    }

    @ParameterizedTest
    @CsvSource({"whiteIn1A8x8Board, 8", "whiteIn1A4x4Board, 4", "whiteIn1A16x16Board, 16"})
    public void asStringOfDashesWithInitialWWhenThereIsOnlyOneWhiteDiskIn1A(String fileName, int size) throws URISyntaxException, IOException {
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource(fileName);
        assert boardFile != null;
        String boardRepresentation = Files.readString(Paths.get(boardFile.toURI()));
        Board board = new Board(size);
        board.putDisk(Disk.Color.WHITE,new Coordinate("1A"));
        assertEquals(boardRepresentation, board.toString());
    }

    @ParameterizedTest
    @CsvSource({"whiteIn1AblackIn4D8x8Board, 8", "whiteIn1AblackIn4D4x4Board, 4", "whiteIn1AblackIn4D16x16Board, 16"})
    public void asStringOfDashesWithInitialWWhenThereIsAWhiteDiskIn1AAndABlackDiskIn8H(String fileName, int size) throws URISyntaxException, IOException {
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource(fileName);
        assert boardFile != null;
        String boardRepresentation = Files.readString(Paths.get(boardFile.toURI()));
        Board board = new Board(size);
        board.putDisk(Disk.Color.WHITE,new Coordinate("1A"));
        board.putDisk(Disk.Color.BLACK,new Coordinate("4D"));
        assertEquals(boardRepresentation, board.toString());
    }

}
