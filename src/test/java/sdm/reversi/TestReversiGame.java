package sdm.reversi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
        assertTrue(game.isValidMove(game.getPlayer1(), coordinate));
    }
    @Test
    void blackIn6EIsValidFirstMove() {
        Game game = new ReversiGame("Bob", "Alice");
        assertTrue(game.isValidMove(game.getPlayer1(), "6E"));
    }

    @Test
    void blackIn3DIsValidFirstMove() {
        Game game = new ReversiGame("Bob", "Alice");
        assertTrue(game.isValidMove(game.getPlayer1(), "3D"));
    }

    @Test
    void blackIn5FIsValidFirstMove() {
        Game game = new ReversiGame("Bob", "Alice");
        assertTrue(game.isValidMove(game.getPlayer1(), "5F"));
    }

    @ParameterizedTest
    @CsvSource("5D,6D,3E")
    void invalidFirstMove(String coordinate){
        Game game = new ReversiGame("Bob", "Alice");
        assertFalse(game.isValidMove(game.getPlayer1(), coordinate));
    }
}
