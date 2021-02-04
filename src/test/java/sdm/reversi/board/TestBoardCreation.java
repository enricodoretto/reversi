package sdm.reversi.board;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import sdm.reversi.Board;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class TestBoardCreation {

    @ParameterizedTest
    @CsvSource({"4", "8", "26"})
    public void succeedsWithEvenSizeBetween4And26(int boardSize){
        assertDoesNotThrow(() -> new Board(boardSize));
    }

    @ParameterizedTest
    @CsvSource({"emptyBoards/empty8x8Board", "emptyBoards/empty4x4Board", "emptyBoards/empty16x16Board", "whiteIn1A8x8Board", "whiteIn1A4x4Board", "whiteIn1A16x16Board", "whiteIn1AblackIn4D8x8Board", "whiteIn1AblackIn4D4x4Board", "whiteIn1AblackIn4D16x16Board"})
    void succeedsFromProperlyFormattedFile(String fileName) throws IOException, URISyntaxException {
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource(fileName);
        assert boardFile != null;
        String emptyBoard = Files.readString(Paths.get(boardFile.toURI()));
        Board board = new Board(boardFile);
        assertEquals(emptyBoard, board.toString());
    }

    @ParameterizedTest
    @CsvSource({"invalidBoards/illegal8x8Board", "invalidBoards/illegal4x4Board", "invalidBoards/illegal16x16Board"})
    void failsFromFileWithIllegalCharacters(String fileName) {
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource(fileName);
        assertThrows(IllegalArgumentException.class, () -> new Board(boardFile));
    }

    @ParameterizedTest
    @CsvSource({"invalidBoards/wronglyFormatted8x7Board", "invalidBoards/wronglyFormatted4x5Board", "invalidBoards/wronglyFormattedBoardWithIrregularLengthLines"})
    void failsFromWronglyFormattedFile(String fileName) {
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource(fileName);
        assertThrows(IllegalArgumentException.class, () -> new Board(boardFile));
    }

    @ParameterizedTest
    @CsvSource({"nonExistingFile", "inventedFile"})
    void failsFromNonExistingFile(String fileName) {
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource(fileName);
        assertThrows(FileNotFoundException.class, () -> new Board(boardFile));
    }

    @ParameterizedTest
    @CsvSource({"5","7","15"})
    public void failsWithOddSize(int boardSize){
        assertThrows(IllegalArgumentException.class, () -> new Board(boardSize));
    }

    @ParameterizedTest
    @CsvSource({"-4", "0", "2"})
    public void failsWithSizeSmallerThan4(int boardSize){
        assertThrows(IllegalArgumentException.class, () -> new Board(boardSize));
    }

    @ParameterizedTest
    @CsvSource({"28", "30", "64"})
    public void failsWithSizeGreaterThan26(int boardSize){
        assertThrows(IllegalArgumentException.class, () -> new Board(boardSize));
    }
}
