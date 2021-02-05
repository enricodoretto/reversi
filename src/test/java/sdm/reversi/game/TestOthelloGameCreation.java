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
    @CsvSource({"4, initialBoards/othello4x4Board, initialBoards/othello4x4BoardOutput",
            "8, initialBoards/othello8x8Board, initialBoards/othello8x8BoardOutput",
            "16, initialBoards/othello16x16Board, initialBoards/othello16x16BoardOutput"})
    void succeedsWithDifferentBoardSizes(int boardSize, String fileNameInput, String fileNameOutput) throws URISyntaxException, IOException {
        Game game = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withBoardSize(boardSize).buildOthello();
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource(fileNameInput);
        URL boardFileOutput = Thread.currentThread().getContextClassLoader().getResource(fileNameOutput);
        assert boardFile != null;
        String initializedOthelloBoard = Files.readString(Paths.get(boardFileOutput.toURI()));
        assertEquals(initializedOthelloBoard, game.getBoard().toString());
    }

    @ParameterizedTest
    @CsvSource({"fullBoards/allWhite8x8Board,fullBoards/allWhite8x8BoardOutput", "fullBoards/allBlack8x8Board,fullBoards/allBlack8x8BoardOutput",
            "initialBoards/othello4x4Board, initialBoards/othello4x4BoardOutput", "initialBoards/othello16x16Board, initialBoards/othello16x16BoardOutput"})
    void succeedsWithCustomBoardWithAtLeastFourDisks(String fileNameInput, String fileNameOutput) throws URISyntaxException, IOException {
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource(fileNameInput);
        URL boardFileOutput = Thread.currentThread().getContextClassLoader().getResource(fileNameOutput);
        assert boardFile != null;
        String initializedOthelloBoard = Files.readString(Paths.get(boardFileOutput.toURI()));
        Game game = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withCustomBoard(boardFile).buildOthello();
        assertEquals(initializedOthelloBoard, game.getBoard().toString());
    }

    @ParameterizedTest
    @CsvSource({"emptyBoards/empty4x4Board", "emptyBoards/empty8x8Board", "emptyBoards/empty16x16Board"})
    void failsWithCustomBoardWithLessThanFourDisks(String fileName) {
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource(fileName);
        assertThrows(IllegalArgumentException.class, () -> Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withCustomBoard(boardFile).buildOthello());
    }
}
