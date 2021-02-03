package sdm.reversi.game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import sdm.reversi.Disk;
import sdm.reversi.player.Player;

public class TestGameCreation {

    @ParameterizedTest
    @CsvSource({"Bob, Alice", "Jack, John", "Simon, Leonard"})
    void withTwoPlayersWithDifferentNamesAndBoardSize8Succeeds(String player1Name, String player2Name) {
        Game othelloGameCLI = Game.GameBuilder.CLIGameBuilder(player1Name).withOpponent(player2Name).withBoardSize(8).buildOthello();
        Game reversiGameCLI = Game.GameBuilder.CLIGameBuilder(player1Name).withOpponent(player2Name).withBoardSize(8).buildReversi();
        assertAll(
                () -> assertEquals(new Player(player1Name, Disk.Color.BLACK), othelloGameCLI.getPlayer1()),
                () -> assertEquals(new Player(player2Name, Disk.Color.WHITE), othelloGameCLI.getPlayer2()),
                () -> assertEquals(new Player(player1Name, Disk.Color.BLACK), reversiGameCLI.getPlayer1()),
                () -> assertEquals(new Player(player2Name, Disk.Color.WHITE), reversiGameCLI.getPlayer2())
        );
    }

    @ParameterizedTest
    @CsvSource({"Bob", "Alice", "John"})
    void failsWithBothPlayersWithSameName(String playerName) {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> Game.GameBuilder.CLIGameBuilder(playerName).withOpponent(playerName).withBoardSize(8).buildOthello()),
                () -> assertThrows(IllegalArgumentException.class, () -> Game.GameBuilder.CLIGameBuilder(playerName).withOpponent(playerName).withBoardSize(8).buildReversi())
        );
    }
}
