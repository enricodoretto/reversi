package sdm.reversi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestReversiGame {
    @ParameterizedTest
    @CsvSource({"Bob, Alice", "Jack, John", "Simon, Leonard"})
    void createGameWithTwoPlayers(String player1Name, String player2Name) {
        Game game = new ReversiGame(player1Name, player2Name);
        assertAll(
                () -> assertEquals(new Player(player1Name, Disk.Color.BLACK), game.getPlayer1()),
                () -> assertEquals(new Player(player2Name, Disk.Color.WHITE), game.getPlayer2())
        );
    }

    @ParameterizedTest
    @CsvSource({"Bob", "Alice", "John"})
    void failedToCreateGameWithBothPlayersWithSameName(String playerName) {
        assertThrows(IllegalArgumentException.class, () -> new ReversiGame(playerName, playerName));
    }

    @Test
    void initializeReversiBoard() {
        Game game = new ReversiGame("Bob", "Alice");
        String initializedReversiBoard = "--------\n" +
                "--------\n" +
                "--------\n" +
                "---WB---\n" +
                "---BW---\n" +
                "--------\n" +
                "--------\n" +
                "--------";
        assertEquals(initializedReversiBoard, game.getBoardRepresentation());
    }

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
        Coordinate coordinateToFlip = Coordinate.parseCoordinate(expectedDiskCoordinateToFlip);
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
                Coordinate.parseCoordinate("3D"), Set.of(Coordinate.parseCoordinate("4D")),
                Coordinate.parseCoordinate("4C"), Set.of(Coordinate.parseCoordinate("4D")),
                Coordinate.parseCoordinate("6E"), Set.of(Coordinate.parseCoordinate("5E")),
                Coordinate.parseCoordinate("5F"), Set.of(Coordinate.parseCoordinate("5E"))
        );
        assertEquals(possibleDisksToFlip, game.getPlayerPossibleMoves());
    }

    @Test
    void blackIn3DFlipsDiskIn4D() {
        Game game = new ReversiGame("Bob", "Alice");
        game.makeMove(Coordinate.parseCoordinate("3D"));
        assertAll(
                () -> assertEquals(Disk.Color.BLACK, game.board.getDiskColorFromCoordinate(Coordinate.parseCoordinate("3D"))),
                () -> assertEquals(Disk.Color.BLACK, game.board.getDiskColorFromCoordinate(Coordinate.parseCoordinate("4D"))),
                () -> assertEquals(Disk.Color.BLACK, game.board.getDiskColorFromCoordinate(Coordinate.parseCoordinate("5D"))),
                () -> assertEquals(Disk.Color.BLACK, game.board.getDiskColorFromCoordinate(Coordinate.parseCoordinate("4E"))),
                () -> assertEquals(Disk.Color.WHITE, game.board.getDiskColorFromCoordinate(Coordinate.parseCoordinate("5E")))
        );
    }

    @Test
    void blackIn6EFlipsDiskIn5E() {
        Game game = new ReversiGame("Bob", "Alice");
        game.makeMove(Coordinate.parseCoordinate("6E"));
        assertAll(
                () -> assertEquals(Disk.Color.BLACK, game.board.getDiskColorFromCoordinate(Coordinate.parseCoordinate("6E"))),
                () -> assertEquals(Disk.Color.BLACK, game.board.getDiskColorFromCoordinate(Coordinate.parseCoordinate("5E"))),
                () -> assertEquals(Disk.Color.BLACK, game.board.getDiskColorFromCoordinate(Coordinate.parseCoordinate("4E"))),
                () -> assertEquals(Disk.Color.BLACK, game.board.getDiskColorFromCoordinate(Coordinate.parseCoordinate("5D"))),
                () -> assertEquals(Disk.Color.WHITE, game.board.getDiskColorFromCoordinate(Coordinate.parseCoordinate("4D")))
        );
    }

    @Test
    void blackIn4CFlipsDiskIn4D() {
        Game game = new ReversiGame("Bob", "Alice");
        game.makeMove(Coordinate.parseCoordinate("4C"));
        assertAll(
                () -> assertEquals(Disk.Color.BLACK, game.board.getDiskColorFromCoordinate(Coordinate.parseCoordinate("4C"))),
                () -> assertEquals(Disk.Color.BLACK, game.board.getDiskColorFromCoordinate(Coordinate.parseCoordinate("4D"))),
                () -> assertEquals(Disk.Color.BLACK, game.board.getDiskColorFromCoordinate(Coordinate.parseCoordinate("4E"))),
                () -> assertEquals(Disk.Color.BLACK, game.board.getDiskColorFromCoordinate(Coordinate.parseCoordinate("5D"))),
                () -> assertEquals(Disk.Color.WHITE, game.board.getDiskColorFromCoordinate(Coordinate.parseCoordinate("5E")))
        );
    }

    @Test
    void cantMakeIllegalFirstMoveOn1A() {
        Game game = new ReversiGame("Bob", "Alice");
        assertThrows(IllegalArgumentException.class, () -> game.makeMove(Coordinate.parseCoordinate("1A")));
    }

    @Test
    void cantMakeIllegalFirstMoveOn10H() {
        Game game = new ReversiGame("Bob", "Alice");
        assertThrows(IllegalArgumentException.class, () -> game.makeMove(Coordinate.parseCoordinate("10H")));
    }
}
