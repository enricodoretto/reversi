package sdm.reversi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TestOthelloMove {

    @ParameterizedTest
    @CsvSource("6E,3D,5F")
    void isValidIfCellIsAvailableAndHasNeighborOfDifferentColorAndCausesDisksToFlip(Coordinate coordinate) {
        Game game = new OthelloGame("Bob", "Alice");
        assertTrue(game.isValidMove(coordinate));
    }

    @ParameterizedTest
    @CsvSource("5D,5F,4D,4E")
    void isInvalidIfCellIsAlreadyFull(Coordinate coordinate) {
        Game game = new OthelloGame("Bob", "Alice");
        assertFalse(game.isValidMove(coordinate));
    }

    @ParameterizedTest
    @CsvSource("6C,1A,8H")
    void isInvalidIfCellDoesNotHaveNeighborOfDifferentColor(Coordinate stringCoordinate) {
        Game game = new OthelloGame("Bob", "Alice");
        assertFalse(game.isValidMove(stringCoordinate));
    }

    @ParameterizedTest
    @CsvSource("3C,6F")
    void isInvalidIfDoesNotCauseDisksToFlip(Coordinate stringCoordinate) {
        Game game = new OthelloGame("Bob", "Alice");
        assertFalse(game.isValidMove(stringCoordinate));
    }

    @ParameterizedTest
    @CsvSource({"6E,5E", "5F,5E", "4C,4D", "3D,4D"})
    void returnsCoordinatesToFlipIfValid(Coordinate blackDiskPosition, Coordinate coordinateToFlip) {
        Game game = new OthelloGame("Bob", "Alice");
        Set<Coordinate> coordinatesToFlip = Set.of(coordinateToFlip);
        assertEquals(coordinatesToFlip, game.getDisksToFlip(blackDiskPosition));
    }

    @ParameterizedTest
    @CsvSource("6F,6D, 3E, 3C ")
    void returnsNoCoordinatesToFlipIfInvalid(Coordinate blackDiskPosition) {
        Game game = new OthelloGame("Bob", "Alice");
        assertNull(game.getDisksToFlip(blackDiskPosition));
    }

    @Test
    void validPositionsFirstBlackMove() {
        Game game = new OthelloGame("Bob", "Alice");
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
        Game game = new OthelloGame("Bob", "Alice");
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
        Game game = new OthelloGame("Bob", "Alice");
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
        Game game = new OthelloGame("Bob", "Alice");
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
        Game game = new OthelloGame("Bob", "Alice");
        assertThrows(IllegalArgumentException.class, () -> game.makeMove(new Coordinate(blackDiskCoordinate)));
    }
}
