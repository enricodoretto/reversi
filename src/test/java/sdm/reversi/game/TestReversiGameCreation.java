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
    @CsvSource({"4, emptyBoards/empty4x4Board", "8, emptyBoards/empty8x8Board", "16, emptyBoards/empty16x16Board"})
    void initializesTheBoardCorrectly(int boardSize, String fileName) throws URISyntaxException, IOException {
        Game game = new ReversiGame("Bob", "Alice", boardSize);
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource(fileName);
        String initializedOthelloBoard = Files.readString(Paths.get(boardFile.toURI()));
        assertEquals(initializedOthelloBoard, game.getBoard().toString());
    }

}
