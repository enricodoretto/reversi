package sdm.reversi;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
    @CsvSource({"Bob", "Alice", "John"})
    void failsWithBothPlayersWithSameName(String playerName) {
        assertThrows(IllegalArgumentException.class, () -> new ReversiGame(playerName, playerName));
    }

}
