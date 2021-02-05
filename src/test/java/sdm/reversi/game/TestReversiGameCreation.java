package sdm.reversi.game;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

}
