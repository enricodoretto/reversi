package sdm.reversi;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class TestCreateReversiGame {

    @ParameterizedTest
    @CsvSource({"Bob, Alice", "Jack, John", "Simon, Leonard"})
    void withTwoPlayersWithDifferentNamesAndDefaultBoardSucceeds(String player1Name, String player2Name) {
        Game game = new ReversiGame(player1Name, player2Name);
        assertAll(
                () -> assertEquals(new Player(player1Name, Disk.Color.BLACK), game.getPlayer1()),
                () -> assertEquals(new Player(player2Name, Disk.Color.WHITE), game.getPlayer2())
        );
    }

    @ParameterizedTest
    @CsvSource({"4, reversi4x4Board", "8, reversi8x8Board", "16, reversi16x16Board"})
    void initializesTheBoardCorrectly(int boardSize, String fileName) throws URISyntaxException, IOException {
        Game game = new ReversiGame("Bob", "Alice", boardSize);
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource(fileName);
        String initializedReversiBoard = Files.readString(Paths.get(boardFile.toURI()));
        assertEquals(initializedReversiBoard, game.getBoardRepresentation());
    }

    @ParameterizedTest
    @CsvSource({"Bob", "Alice", "John"})
    void failsWithBothPlayersWithSameName(String playerName) {
        assertThrows(IllegalArgumentException.class, () -> new ReversiGame(playerName, playerName));
    }

}
