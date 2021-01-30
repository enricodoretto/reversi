package sdm.reversi.game;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class TestOthelloGameCreation {

    @ParameterizedTest
    @CsvSource({"4, initialBoards/othello4x4Board", "8, initialBoards/othello8x8Board", "16, initialBoards/othello16x16Board"})
    void initializesTheBoardCorrectly(int boardSize, String fileName) throws URISyntaxException, IOException {
        Game game = new OthelloGame("Bob", "Alice", boardSize);
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource(fileName);
        String initializedOthelloBoard = Files.readString(Paths.get(boardFile.toURI()));
        assertEquals(initializedOthelloBoard, game.getBoard().toString());
    }

    @ParameterizedTest
    @CsvSource({"fullBoards/allWhite8x8Board", "fullBoards/allBlack8x8Board", "initialBoards/othello4x4Board", "initialBoards/othello16x16Board"})
    void succeedsWithCustomBoardWithAtLeastFourDisks(String fileName) throws URISyntaxException, IOException {
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource(fileName);
        String initializedOthelloBoard = Files.readString(Paths.get(boardFile.toURI()));
        Game game = new OthelloGame("Bob", "Alice", boardFile);
        assertEquals(initializedOthelloBoard, game.getBoard().toString());
    }

    @ParameterizedTest
    @CsvSource({"emptyBoards/empty4x4Board", "emptyBoards/empty8x8Board", "emptyBoards/empty16x16Board"})
    void failsWithCustomBoardWithLessThanFourDisks(String fileName) {
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource(fileName);
        assertThrows(IllegalArgumentException.class, () -> new OthelloGame("Bob", "Alice", boardFile));
    }

}
