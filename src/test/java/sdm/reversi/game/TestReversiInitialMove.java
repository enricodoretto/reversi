package sdm.reversi.game;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import sdm.reversi.Coordinate;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class TestReversiInitialMove {

    @ParameterizedTest
    @CsvSource({"8, 4D-4E-5D-5E", "4, 2B-2C-3B-3C", "10, 5E-5F-6E-6F"})
    void isAllowedInCentralSquare(int boardSize, String coordinates) {
        Game reversiGame = new ReversiGame("Bob", "Alice", boardSize);
        Map<Coordinate, Set<Coordinate>> expectedCoordinates = Arrays.stream(coordinates.split("-"))
                .collect(Collectors.toMap(
                        stringCoordinate -> new Coordinate(stringCoordinate),
                        coordinate -> new HashSet<>()
                ));
        assertEquals(expectedCoordinates, reversiGame.allowedMovesForCurrentPlayer);
    }

    @ParameterizedTest
    @CsvSource({"8, 6D", "4, 1A", "10, 2B"})
    void isNotAllowedOutsideCentralSquare(int boardSize, String coordinate) {
        Game reversiGame = new ReversiGame("Bob", "Alice", boardSize);
        assertFalse(reversiGame.allowedMovesForCurrentPlayer.containsKey(coordinate));
    }

    @ParameterizedTest
    @CsvSource({"8, 4D", "4, 3C", "10, 6E"})
    void canBeMadeInCentralSquare(int boardSize, Coordinate coordinate) {
        Game reversiGame = new ReversiGame("Bob", "Alice", boardSize);
        assertDoesNotThrow(() -> reversiGame.makeMove(coordinate));
    }
}
