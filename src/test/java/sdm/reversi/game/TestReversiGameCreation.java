package sdm.reversi.game;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import sdm.reversi.board.TestBoardIsRepresented;
import sdm.reversi.game.Game;
import sdm.reversi.game.ReversiGame;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestReversiGameCreation {

    @ParameterizedTest
    @CsvSource({"4, empty4x4Board", "8, empty8x8Board", "16, empty16x16Board"})
    void initializesTheBoardCorrectly(int boardSize, String fileName) throws URISyntaxException, IOException {
        Game game = new ReversiGame("Bob", "Alice", boardSize);
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource(fileName);
        String initializedOthelloBoard = Files.readString(Paths.get(boardFile.toURI()));
        assertEquals(initializedOthelloBoard, game.getBoardRepresentation());
    }

}
