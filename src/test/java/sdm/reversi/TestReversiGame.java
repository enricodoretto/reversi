package sdm.reversi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;

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
    void initializeReversiBoard() throws URISyntaxException, IOException {
        Game game = new ReversiGame("Bob", "Alice");
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource("reversi8x8Board");
        String initializedReversiBoard = Files.readString(Paths.get(boardFile.toURI()));
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

    @Test
    void initializeReversiGameWithAllWhiteBoard() throws URISyntaxException, IOException {
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource("allWhite8x8Board");
        String initializedReversiBoard = Files.readString(Paths.get(boardFile.toURI()));
        Board board = new Board(boardFile);
        Game game = new ReversiGame("Bob", "Alice", board);
        assertEquals(initializedReversiBoard, game.getBoardRepresentation());
    }

    @Test
    void blackIsInStallWithCustomBoardWithOnlyOneBlackDiskIn3D() throws URISyntaxException, IOException {
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource("allWhiteAndOneBlackIn3D8x8Board");
        Board board = new Board(boardFile);
        Game game = new ReversiGame("Bob", "Alice", board);
        assertTrue(game.getPlayer1().isInStall());
    }

    @Test
    void gameWithFullWhiteBoardIsOver() throws IOException {
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource("allWhite8x8Board");
        Board board = new Board(boardFile);
        Game game = new ReversiGame("Bob", "Alice", board);
        assertTrue(game.isOver());
    }

    @Test
    void gameWithFullBlackBoardIsOver() throws IOException {
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource("allBlack8x8Board");
        Board board = new Board(boardFile);
        Game game = new ReversiGame("Bob", "Alice", board);
        assertTrue(game.isOver());
    }

    @Test
    void gameWithHalfBlackAndHalfWhiteBoardHasNoWinner() {
        Board board = new Board();
        for (int row = 0; row < 4; row++) {
            for (int column = 0; column < 8; column++) {
                board.putDisk(Disk.Color.BLACK, new Coordinate(row, column));
            }
        }
        for (int row = 4; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                board.putDisk(Disk.Color.WHITE, new Coordinate(row, column));
            }
        }
        Game game = new ReversiGame("Bob", "Alice", board);
        assertNull(game.getWinner());
    }

    @Test
    void gameWith2017FinalBoardIsOver() {
        Board board = new Board();
        board.putDisk(Disk.Color.WHITE, new Coordinate("1E"));
        board.putDisk(Disk.Color.WHITE, new Coordinate("2E"));
        board.putDisk(Disk.Color.WHITE, new Coordinate("2F"));
        board.putDisk(Disk.Color.WHITE, new Coordinate("3A"));
        board.putDisk(Disk.Color.WHITE, new Coordinate("3B"));
        board.putDisk(Disk.Color.WHITE, new Coordinate("3C"));
        board.putDisk(Disk.Color.WHITE, new Coordinate("3D"));
        board.putDisk(Disk.Color.WHITE, new Coordinate("3E"));
        board.putDisk(Disk.Color.WHITE, new Coordinate("3F"));
        board.putDisk(Disk.Color.WHITE, new Coordinate("3G"));
        board.putDisk(Disk.Color.WHITE, new Coordinate("4C"));
        board.putDisk(Disk.Color.WHITE, new Coordinate("4D"));
        board.putDisk(Disk.Color.WHITE, new Coordinate("4E"));
        board.putDisk(Disk.Color.WHITE, new Coordinate("4F"));
        board.putDisk(Disk.Color.WHITE, new Coordinate("5C"));
        board.putDisk(Disk.Color.WHITE, new Coordinate("5D"));
        board.putDisk(Disk.Color.WHITE, new Coordinate("5E"));
        board.putDisk(Disk.Color.BLACK, new Coordinate("3H"));
        board.putDisk(Disk.Color.BLACK, new Coordinate("4H"));
        board.putDisk(Disk.Color.BLACK, new Coordinate("5H"));
        Game game = new ReversiGame("Bob", "Alice", board);
        assertTrue(game.isOver());
    }

    @Test
    void gameWith2017FinalBoardIsWonByAlice() {
        Board board = new Board();
        board.putDisk(Disk.Color.WHITE, new Coordinate("1E"));
        board.putDisk(Disk.Color.WHITE, new Coordinate("2E"));
        board.putDisk(Disk.Color.WHITE, new Coordinate("2F"));
        board.putDisk(Disk.Color.WHITE, new Coordinate("3A"));
        board.putDisk(Disk.Color.WHITE, new Coordinate("3B"));
        board.putDisk(Disk.Color.WHITE, new Coordinate("3C"));
        board.putDisk(Disk.Color.WHITE, new Coordinate("3D"));
        board.putDisk(Disk.Color.WHITE, new Coordinate("3E"));
        board.putDisk(Disk.Color.WHITE, new Coordinate("3F"));
        board.putDisk(Disk.Color.WHITE, new Coordinate("3G"));
        board.putDisk(Disk.Color.WHITE, new Coordinate("4C"));
        board.putDisk(Disk.Color.WHITE, new Coordinate("4D"));
        board.putDisk(Disk.Color.WHITE, new Coordinate("4E"));
        board.putDisk(Disk.Color.WHITE, new Coordinate("4F"));
        board.putDisk(Disk.Color.WHITE, new Coordinate("5C"));
        board.putDisk(Disk.Color.WHITE, new Coordinate("5D"));
        board.putDisk(Disk.Color.WHITE, new Coordinate("5E"));
        board.putDisk(Disk.Color.BLACK, new Coordinate("3H"));
        board.putDisk(Disk.Color.BLACK, new Coordinate("4H"));
        board.putDisk(Disk.Color.BLACK, new Coordinate("5H"));
        Game game = new ReversiGame("Bob", "Alice", board);
        assertEquals("Alice", game.getWinner().getName());
    }

}
