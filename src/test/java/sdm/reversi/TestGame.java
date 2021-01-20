package sdm.reversi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class TestGame {
    @ParameterizedTest
    @CsvSource({"Bob, Alice", "Jack, John", "Simon, Leonard"})
    void createGameWithTwoPlayers(String player1Name, String player2Name) {
        Game game = new Game(player1Name, player2Name);
        assertAll(
                () -> assertEquals(new Player(player1Name, Disk.Color.BLACK), game.getPlayer1()),
                () -> assertEquals(new Player(player2Name, Disk.Color.WHITE), game.getPlayer2())
        );
    }

    @Test
    void failedToCreateGameWithBothPlayersBob() {
        assertThrows(IllegalArgumentException.class, () -> new Game("Bob", "Bob"));
    }

    @Test
    void failedToCreateGameWithBothPlayersAlice() {
        assertThrows(IllegalArgumentException.class, () -> new Game("Alice", "Alice"));
    }

    @Test
    void failedToCreateGameWithBothPlayersJohn() {
        assertThrows(IllegalArgumentException.class, () -> new Game("John", "John"));
    }
}
