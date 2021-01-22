package sdm.reversi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

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
    void validFirstMove(String coordinate){
        Game game = new ReversiGame("Bob", "Alice");
        assertTrue(game.isValidMove(coordinate));
    }

    @ParameterizedTest
    @CsvSource("5D,6D,3E")
    void invalidFirstMove(String coordinate){
        Game game = new ReversiGame("Bob", "Alice");
        assertFalse(game.isValidMove(coordinate));
    }

    /*@ParameterizedTest
    @CsvSource("6E,3D,5F")
    void makeValidFirstMoveWithBlackDisk(String stringCoordinate){
        Game game = new ReversiGame("Bob", "Alice");
        assertTrue(game.makeMove(Coordinate.parseCoordinate(stringCoordinate)));
    }*/
    @ParameterizedTest
    @CsvSource("6D,6F,3E,3C")
    void invalidDiagonalFirstMove(String stringCoordinate){
        Game game = new ReversiGame("Bob", "Alice");
        assertFalse(game.isValidMove(stringCoordinate));
    }

    @Test
    void blackIn6EReturns5EToFlip(){
        Game game = new ReversiGame("Bob", "Alice");
        Coordinate coordinateToFlip = Coordinate.parseCoordinate("5E");
        List<Coordinate> coordinatesToFlip = List.of(coordinateToFlip);
        assertEquals(coordinatesToFlip, game.getDisksToFlip("6E"));
    }

    @Test
    void blackIn5FReturns5EToFlip(){
        Game game = new ReversiGame("Bob", "Alice");
        Coordinate coordinateToFlip = Coordinate.parseCoordinate("5E");
        List<Coordinate> coordinatesToFlip = List.of(coordinateToFlip);
        assertEquals(coordinatesToFlip, game.getDisksToFlip("5F"));
    }

    @Test
    void blackIn4CReturns4DToFlip(){
        Game game = new ReversiGame("Bob", "Alice");
        Coordinate coordinateToFlip = Coordinate.parseCoordinate("4D");
        List<Coordinate> coordinatesToFlip = List.of(coordinateToFlip);
        assertEquals(coordinatesToFlip, game.getDisksToFlip("4C"));
    }

    @Test
    void blackIn3DReturns4DToFlip(){
        Game game = new ReversiGame("Bob", "Alice");
        Coordinate coordinateToFlip = Coordinate.parseCoordinate("4D");
        List<Coordinate> coordinatesToFlip = List.of(coordinateToFlip);
        assertEquals(coordinatesToFlip, game.getDisksToFlip("3D"));
    }
}
