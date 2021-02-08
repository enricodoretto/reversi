package sdm.reversi.game;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import sdm.reversi.board.Coordinate;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class TestReversiInitial {

    @ParameterizedTest
    @CsvSource({"8, 4D-4E-5D-5E", "4, 2B-2C-3B-3C", "10, 5E-5F-6E-6F"})
    void moveIsAllowedInCentralSquare(int boardSize, String coordinates) {
        Game reversiGame = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withBoardSize(boardSize).buildReversi();
        Map<Coordinate, Set<Coordinate>> expectedCoordinates = Arrays.stream(coordinates.split("-"))
                .collect(Collectors.toMap(
                        Coordinate::new,
                        coordinate -> new HashSet<>()
                ));
        assertEquals(expectedCoordinates, reversiGame.allowedMovesForCurrentPlayer);
    }

    @ParameterizedTest
    @CsvSource({"8, 6D", "4, 1A", "10, 2B"})
    void moveIsNotAllowedOutsideCentralSquare(int boardSize, Coordinate coordinate) {
        Game reversiGame = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withBoardSize(boardSize).buildReversi();
        assertFalse(reversiGame.allowedMovesForCurrentPlayer.containsKey(coordinate));
    }

    @ParameterizedTest
    @CsvSource({"8, 4D", "4, 3C", "10, 6E"})
    void moveCanBeMadeInCentralSquare(int boardSize, Coordinate coordinate) {
        Game reversiGame = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withBoardSize(boardSize).buildReversi();
        assertDoesNotThrow(() -> reversiGame.makeMove(coordinate));
    }

    @ParameterizedTest
    @CsvSource({"8, 3D", "4, 1A", "10, 7H"})
    void moveCannotBeMadeOutsideCentralSquare(int boardSize, Coordinate coordinate) {
        Game reversiGame = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withBoardSize(boardSize).buildReversi();
        assertThrows(IllegalArgumentException.class, () -> reversiGame.makeMove(coordinate));
    }

    @ParameterizedTest
    @CsvSource({"8, 4D-4E-5D-5E-q-", "4, 2B-2C-3B-3C-q-", "10, 5E-5F-6E-6F-q-"})
    void fourMovesCanBeMadeInCentralSquare(int boardSize, String gameSequence) {
        ByteArrayInputStream bais = new ByteArrayInputStream(gameSequence.replace("-",System.lineSeparator()).getBytes());
        System.setIn(bais);
        Game game = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withBoardSize(boardSize).buildReversi();
        assertDoesNotThrow(game::play);
    }

}
