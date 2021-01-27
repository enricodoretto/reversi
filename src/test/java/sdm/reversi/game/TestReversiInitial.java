package sdm.reversi.game;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import sdm.reversi.Coordinate;
import sdm.reversi.board.TestBoardIsRepresented;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class TestReversiInitial {

    @ParameterizedTest
    @CsvSource({"8, 4D-4E-5D-5E", "4, 2B-2C-3B-3C", "10, 5E-5F-6E-6F"})
    void moveIsAllowedInCentralSquare(int boardSize, String coordinates) {
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
    void moveIsNotAllowedOutsideCentralSquare(int boardSize, String coordinate) {
        Game reversiGame = new ReversiGame("Bob", "Alice", boardSize);
        assertFalse(reversiGame.allowedMovesForCurrentPlayer.containsKey(coordinate));
    }

    @ParameterizedTest
    @CsvSource({"8, 4D", "4, 3C", "10, 6E"})
    void moveCanBeMadeInCentralSquare(int boardSize, Coordinate coordinate) {
        Game reversiGame = new ReversiGame("Bob", "Alice", boardSize);
        assertDoesNotThrow(() -> reversiGame.makeMove(coordinate));
    }

    @ParameterizedTest
    @CsvSource({"8, 3D", "4, 1A", "10, 7H"})
    void moveCannotBeMadeOutsideCentralSquare(int boardSize, Coordinate coordinate) {
        Game reversiGame = new ReversiGame("Bob", "Alice", boardSize);
        assertThrows(IllegalArgumentException.class, () -> reversiGame.makeMove(coordinate));
    }

    @Test
    void fourMovesCanBeMadeInCentralSquare() throws IOException {
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource("othello4x4BoardTwoMovesMissing");
        Game game = new OthelloGame("Bob", "Alice", boardFile);
        String moves = "4D" + System.lineSeparator() + "4E" + System.lineSeparator() +
                "5D" + System.lineSeparator() + "5E" + System.lineSeparator() + "q" + System.lineSeparator();
        ByteArrayInputStream bais = new ByteArrayInputStream(moves.getBytes());
        System.setIn(bais);
        assertDoesNotThrow(() -> game.play());
    }
}
