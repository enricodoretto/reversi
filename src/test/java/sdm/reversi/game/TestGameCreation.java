package sdm.reversi.game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import sdm.reversi.Disk;
import sdm.reversi.player.Player;

public class TestGameCreation {

    @ParameterizedTest
    @CsvSource({"Bob, Alice", "Jack, John", "Simon, Leonard"})
    void withTwoPlayersWithDifferentNamesAndDefaultBoardSucceeds(String player1Name, String player2Name) {
        Game othelloGame = Game.GameBuilder.CLIGameBuilder(player1Name).withOpponent(player2Name).withBoardSize(8).buildOthello();
        Game reversiGame = Game.GameBuilder.CLIGameBuilder(player1Name).withOpponent(player2Name).withBoardSize(8).buildReversi();
        assertAll(
                () -> assertEquals(new Player(player1Name, Disk.Color.BLACK), othelloGame.getPlayer1()),
                () -> assertEquals(new Player(player2Name, Disk.Color.WHITE), othelloGame.getPlayer2()),
                () -> assertEquals(new Player(player1Name, Disk.Color.BLACK), reversiGame.getPlayer1()),
                () -> assertEquals(new Player(player2Name, Disk.Color.WHITE), reversiGame.getPlayer2())
        );
    }

    @ParameterizedTest
    @CsvSource({"Bob", "Alice", "John"})
    void failsWithBothPlayersWithSameNameWithGameBuilder(String playerName) {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> Game.GameBuilder.CLIGameBuilder(playerName).withOpponent(playerName).buildOthello()),
                () -> assertThrows(IllegalArgumentException.class, () -> Game.GameBuilder.CLIGameBuilder(playerName).withOpponent(playerName).buildReversi())
        );
    }
}
