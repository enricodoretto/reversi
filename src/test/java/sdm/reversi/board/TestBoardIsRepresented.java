package sdm.reversi.board;

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
    @CsvSource({"emptyBoards/empty8x8Board, emptyBoards/empty8x8BoardOutput,8",
            "emptyBoards/empty4x4Board,emptyBoards/empty4x4BoardOutput ,4",
            "emptyBoards/empty16x16Board,emptyBoards/empty16x16BoardOutput ,16"})
    void asStringOfDashesWhenEmpty(String fileNameInput, String fileNameOutput, int size) throws IOException, URISyntaxException {
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource(fileNameInput);
        URL boardFileOutput = TestBoardIsRepresented.class.getClassLoader().getResource(fileNameOutput);
        assert boardFile != null;
        String emptyBoard = Files.readString(Paths.get(boardFileOutput.toURI()));
        Board board = new Board(size);
        assertEquals(emptyBoard, board.toString());
    }

    @ParameterizedTest
    @CsvSource({"whiteIn1A8x8Board, whiteIn1A8x8BoardOutput,8", "whiteIn1A4x4Board, whiteIn1A4x4BoardOutput,4", "whiteIn1A16x16Board, whiteIn1A16x16BoardOutput,16"})
    public void asStringOfDashesWithInitialWWhenThereIsOnlyOneWhiteDiskIn1A(String fileNameInput, String fileNameOutput,int size) throws URISyntaxException, IOException {
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource(fileNameInput);
        URL boardFileOutput = TestBoardIsRepresented.class.getClassLoader().getResource(fileNameOutput);
        assert boardFile != null;
        String boardRepresentation = Files.readString(Paths.get(boardFileOutput.toURI()));
        Board board = new Board(size);
        board.putDisk(Disk.Color.WHITE,new Coordinate("1A"));
        assertEquals(boardRepresentation, board.toString());
    }

    @ParameterizedTest
    @CsvSource({"whiteIn1ABlackIn4D8x8Board, whiteIn1ABlackIn4D8x8BoardOutput,8",
            "whiteIn1ABlackIn4D4x4Board,whiteIn1ABlackIn4D4x4BoardOutput ,4",
            "whiteIn1ABlackIn4D16x16Board, whiteIn1ABlackIn4D16x16BoardOutput,16"})
    public void asStringOfDashesWithWAndBWhenThereIsAWhiteDiskIn1AAndABlackDiskIn4D(String fileNameInput, String fileNameOutput ,int size) throws URISyntaxException, IOException {
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource(fileNameInput);
        URL boardFileOutput = TestBoardIsRepresented.class.getClassLoader().getResource(fileNameOutput);
        assert boardFile != null;
        String boardRepresentation = Files.readString(Paths.get(boardFileOutput.toURI()));
        Board board = new Board(size);
        board.putDisk(Disk.Color.WHITE,new Coordinate("1A"));
        board.putDisk(Disk.Color.BLACK,new Coordinate("4D"));
        assertEquals(boardRepresentation, board.toString());
    }

}
