package sdm.reversi.game;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import sdm.reversi.board.TestBoardIsRepresented;
import sdm.reversi.game.Game;
import sdm.reversi.game.OthelloGame;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class TestOthelloGameCreation {

    @ParameterizedTest
    @CsvSource({"4, othello4x4Board", "8, othello8x8Board", "16, othello16x16Board"})
    void initializesTheBoardCorrectly(int boardSize, String fileName) throws URISyntaxException, IOException {
        Game game = new OthelloGame("Bob", "Alice", boardSize);
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource(fileName);
        String initializedOthelloBoard = Files.readString(Paths.get(boardFile.toURI()));
        assertEquals(initializedOthelloBoard, game.getBoardRepresentation());
    }

    @ParameterizedTest
    @CsvSource({"allWhite8x8Board", "allBlack8x8Board", "othello4x4Board", "othello16x16Board"})
    void succeedsWithCustomBoardWithAtLeastFourDisks(String fileName) throws URISyntaxException, IOException {
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource(fileName);
        String initializedOthelloBoard = Files.readString(Paths.get(boardFile.toURI()));
        Game game = new OthelloGame("Bob", "Alice", boardFile);
        assertEquals(initializedOthelloBoard, game.getBoardRepresentation());
    }

    @ParameterizedTest
    @CsvSource({"empty4x4Board", "empty8x8Board","empty16x16Board"})
    void failsWithCustomBoardWithLessThanFourDisks(String fileName) {
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource(fileName);
        assertThrows(IllegalArgumentException.class, () -> new OthelloGame("Bob", "Alice", boardFile));
    }

}
