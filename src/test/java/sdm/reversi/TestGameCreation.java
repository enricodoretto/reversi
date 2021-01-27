package sdm.reversi;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import sdm.reversi.game.Game;
import sdm.reversi.game.OthelloGame;
import sdm.reversi.game.ReversiGame;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

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

}
