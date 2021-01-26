package sdm.reversi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TestReversiMove {

    @ParameterizedTest
    @CsvSource("6E,3D,5F")
    void validFirstMove(String coordinate) {
        Game game = new ReversiGame("Bob", "Alice");
        assertTrue(game.isValidMove(coordinate));
    }

    @ParameterizedTest
    @CsvSource("5D,6D,3E")
    void invalidFirstMove(String coordinate) {
        Game game = new ReversiGame("Bob", "Alice");
        assertFalse(game.isValidMove(coordinate));
    }

    @ParameterizedTest
    @CsvSource("6D,6F,3E,3C")
    void invalidDiagonalFirstMove(String stringCoordinate) {
        Game game = new ReversiGame("Bob", "Alice");
        assertFalse(game.isValidMove(stringCoordinate));
    }

    @ParameterizedTest
    @CsvSource({"6E,5E", "5F,5E", "4C,4D", "3D,4D"})
    void validFirstBlackMoveReturnsCoordinatesToFlip(String blackDiskPosition, String expectedDiskCoordinateToFlip) {
        Game game = new ReversiGame("Bob", "Alice");
        Coordinate coordinateToFlip = new Coordinate(expectedDiskCoordinateToFlip);
        Set<Coordinate> coordinatesToFlip = Set.of(coordinateToFlip);
        assertEquals(coordinatesToFlip, game.getDisksToFlip(blackDiskPosition));
    }

    @ParameterizedTest
    @CsvSource("6F,6D, 3E, 3C ")
    void invalidFirstBlackMoveReturnsNoCoordinatesToFlip(String blackDiskPosition) {
        Game game = new ReversiGame("Bob", "Alice");
        assertNull(game.getDisksToFlip(blackDiskPosition));
    }

    @Test
    void validPositionsFirstBlackMove() {
        Game game = new ReversiGame("Bob", "Alice");
        Map<Coordinate, Set<Coordinate>> possibleDisksToFlip = Map.of(
                new Coordinate("3D"), Set.of(new Coordinate("4D")),
                new Coordinate("4C"), Set.of(new Coordinate("4D")),
                new Coordinate("6E"), Set.of(new Coordinate("5E")),
                new Coordinate("5F"), Set.of(new Coordinate("5E"))
        );
        assertEquals(possibleDisksToFlip, game.getPlayerPossibleMoves());
    }

    @Test
    void blackIn3DFlipsDiskIn4D() {
        Game game = new ReversiGame("Bob", "Alice");
        game.makeMove(new Coordinate("3D"));
        assertAll(
                () -> assertEquals(Disk.Color.BLACK, game.board.getDiskColorFromCoordinate(new Coordinate("3D"))),
                () -> assertEquals(Disk.Color.BLACK, game.board.getDiskColorFromCoordinate(new Coordinate("4D"))),
                () -> assertEquals(Disk.Color.BLACK, game.board.getDiskColorFromCoordinate(new Coordinate("5D"))),
                () -> assertEquals(Disk.Color.BLACK, game.board.getDiskColorFromCoordinate(new Coordinate("4E"))),
                () -> assertEquals(Disk.Color.WHITE, game.board.getDiskColorFromCoordinate(new Coordinate("5E")))
        );
    }

    @Test
    void blackIn6EFlipsDiskIn5E() {
        Game game = new ReversiGame("Bob", "Alice");
        game.makeMove(new Coordinate("6E"));
        assertAll(
                () -> assertEquals(Disk.Color.BLACK, game.board.getDiskColorFromCoordinate(new Coordinate("6E"))),
                () -> assertEquals(Disk.Color.BLACK, game.board.getDiskColorFromCoordinate(new Coordinate("5E"))),
                () -> assertEquals(Disk.Color.BLACK, game.board.getDiskColorFromCoordinate(new Coordinate("4E"))),
                () -> assertEquals(Disk.Color.BLACK, game.board.getDiskColorFromCoordinate(new Coordinate("5D"))),
                () -> assertEquals(Disk.Color.WHITE, game.board.getDiskColorFromCoordinate(new Coordinate("4D")))
        );
    }

    @Test
    void blackIn4CFlipsDiskIn4D() {
        Game game = new ReversiGame("Bob", "Alice");
        game.makeMove(new Coordinate("4C"));
        assertAll(
                () -> assertEquals(Disk.Color.BLACK, game.board.getDiskColorFromCoordinate(new Coordinate("4C"))),
                () -> assertEquals(Disk.Color.BLACK, game.board.getDiskColorFromCoordinate(new Coordinate("4D"))),
                () -> assertEquals(Disk.Color.BLACK, game.board.getDiskColorFromCoordinate(new Coordinate("4E"))),
                () -> assertEquals(Disk.Color.BLACK, game.board.getDiskColorFromCoordinate(new Coordinate("5D"))),
                () -> assertEquals(Disk.Color.WHITE, game.board.getDiskColorFromCoordinate(new Coordinate("5E")))
        );
    }

    @ParameterizedTest
    @CsvSource("1A, 10H, 3C")
    void cantMakeIllegalFirstMove(String blackDiskCoordinate) {
        Game game = new ReversiGame("Bob", "Alice");
        assertThrows(IllegalArgumentException.class, () -> game.makeMove(new Coordinate(blackDiskCoordinate)));
    }
}