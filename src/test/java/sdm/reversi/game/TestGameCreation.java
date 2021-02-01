package sdm.reversi.game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import sdm.reversi.Disk;
import sdm.reversi.player.Player;

public class TestGameCreation {

    @ParameterizedTest
    @CsvSource({"Bob, Alice", "Jack, John", "Simon, Leonard"})
    void withTwoPlayersWithDifferentNamesAndDefaultBoardSucceeds(String player1Name, String player2Name) {
        Game othelloGame = new OthelloGame(player1Name, player2Name);
        Game reversiGame = new ReversiGame(player1Name, player2Name);
        assertAll(
                () -> assertEquals(new Player(player1Name, Disk.Color.BLACK), othelloGame.getPlayer1()),
                () -> assertEquals(new Player(player2Name, Disk.Color.WHITE), othelloGame.getPlayer2()),
                () -> assertEquals(new Player(player1Name, Disk.Color.BLACK), reversiGame.getPlayer1()),
                () -> assertEquals(new Player(player2Name, Disk.Color.WHITE), reversiGame.getPlayer2())
        );
    }

    @ParameterizedTest
    @CsvSource({"Bob", "Alice", "John"})
    void failsWithBothPlayersWithSameName(String playerName) {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> new OthelloGame(playerName, playerName)),
                () -> assertThrows(IllegalArgumentException.class, () -> new ReversiGame(playerName, playerName))
        );
    }

    @ParameterizedTest
    @CsvSource({"Bob, Alice", "Jack, John", "Simon, Leonard"})
    void withTwoPlayersWithDifferentNamesAndDefaultBoardSucceedsWithBuilder(String player1Name, String player2Name) {
        Game othelloGame = Game.GameBuilder.CLIGameBuilder(player1Name).withOpponent(player2Name).buildOthello();
        Game reversiGame = Game.GameBuilder.CLIGameBuilder(player1Name).withOpponent(player2Name).buildReversi();
        assertAll(
                () -> assertEquals(new Player(player1Name, Disk.Color.BLACK), othelloGame.getPlayer1()),
                () -> assertEquals(new Player(player2Name, Disk.Color.WHITE), othelloGame.getPlayer2()),
                () -> assertEquals(new Player(player1Name, Disk.Color.BLACK), reversiGame.getPlayer1()),
                () -> assertEquals(new Player(player2Name, Disk.Color.WHITE), reversiGame.getPlayer2())
        );
    }
}
