package sdm.reversi.game;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestReversiGameCreation {

    @ParameterizedTest
    @CsvSource({"4, emptyBoards/empty4x4BoardOutput", "8, emptyBoards/empty8x8BoardOutput", "16, emptyBoards/empty16x16BoardOutput"})
    void initializesTheBoardCorrectly(int boardSize, String fileName) throws URISyntaxException, IOException {
        Game game = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withBoardSize(boardSize).buildReversi();
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource(fileName);
        assert boardFile != null;
        String initializedReversiBoard = Files.readString(Paths.get(boardFile.toURI()));
        assertEquals(initializedReversiBoard, game.getBoard().toString());
    }

    @ParameterizedTest
    @CsvSource({"4, initialBoards/othello4x4BoardOutput", "8, initialBoards/othello8x8BoardOutput", "16, initialBoards/othello16x16BoardOutput"})
    void succeedsWithDifferentBoardSizes(int boardSize, String fileNameOutput) throws URISyntaxException, IOException {
        Game game = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withBoardSize(boardSize).buildOthello();
        URL boardFileOutput = Thread.currentThread().getContextClassLoader().getResource(fileNameOutput);
        assert boardFileOutput != null;
        String initializedReversiBoard = Files.readString(Paths.get(boardFileOutput.toURI()));
        assertEquals(initializedReversiBoard, game.getBoard().toString());
    }

    @ParameterizedTest
    @CsvSource({"fullBoards/allWhite8x8Board,fullBoards/allWhite8x8BoardOutput", "fullBoards/allBlack8x8Board,fullBoards/allBlack8x8BoardOutput",
            "initialBoards/othello4x4Board, initialBoards/othello4x4BoardOutput", "initialBoards/othello16x16Board, initialBoards/othello16x16BoardOutput"})
    void succeedsWithCustomBoardWithAtLeastFourDisks(String fileNameInput, String fileNameOutput) throws URISyntaxException, IOException {
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource(fileNameInput);
        URL boardFileOutput = Thread.currentThread().getContextClassLoader().getResource(fileNameOutput);
        assert boardFile != null;
        assert boardFileOutput != null;
        String initializedReversiBoard = Files.readString(Paths.get(boardFileOutput.toURI()));
        Game game = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withCustomBoard(boardFile).buildReversi();
        assertEquals(initializedReversiBoard, game.getBoard().toString());
    }

    @ParameterizedTest
    @CsvSource({"emptyBoards/empty4x4Board", "emptyBoards/empty8x8Board", "emptyBoards/empty16x16Board"})
    void failsWithCustomBoardWithLessThanFourDisks(String fileName) {
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource(fileName);
        assertThrows(IllegalArgumentException.class, () -> Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withCustomBoard(boardFile).buildReversi());
    }

}
