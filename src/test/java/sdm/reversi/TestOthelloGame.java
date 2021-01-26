package sdm.reversi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TestOthelloGame {

    @Test
    void suggestsValidPositionsForFirstBlackMove() {
        Game game = new OthelloGame("Bob", "Alice");
        Map<Coordinate, Set<Coordinate>> possibleDisksToFlip = Map.of(
                new Coordinate("3D"), Set.of(new Coordinate("4D")),
                new Coordinate("4C"), Set.of(new Coordinate("4D")),
                new Coordinate("6E"), Set.of(new Coordinate("5E")),
                new Coordinate("5F"), Set.of(new Coordinate("5E"))
        );
        assertEquals(possibleDisksToFlip, game.getPlayerPossibleMoves());
    }

    //this name can be improved -> maybe parametrized test?
    @Test
    void hasBlackInStallWithBoardWithOnlyOneBlackDiskIn3D() throws IOException {
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource("allWhiteAndOneBlackIn3D8x8Board");
        Game game = new OthelloGame("Bob", "Alice", boardFile);
        assertTrue(game.getPlayer1().isInStall());
    }

    @ParameterizedTest
    @CsvSource("othello2011FinalBoard, othello2017FinalBoard")
    void withBothPlayersInStallIsOver(String boardFileName) throws IOException {
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource(boardFileName);
        Game game = new OthelloGame("Bob", "Alice", boardFile);
        assertTrue(game.isOver());
    }

    @ParameterizedTest
    @CsvSource("allWhite8x8Board, allBlack8x8Board, first4RowsWhiteAndLast4RowsBlack8x8Board")
    void withFullBoardIsOver(String boardFileName) throws IOException {
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource(boardFileName);
        Game game = new OthelloGame("Bob", "Alice", boardFile);
        assertTrue(game.isOver());
    }

    @ParameterizedTest
    @CsvSource("othello8x8Board, othello4x4Board, othello16x16Board")
    void withBoardNotFullAndAPlayerNotInStallIsOver(String boardFileName) throws IOException {
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource(boardFileName);
        Game game = new OthelloGame("Bob", "Alice", boardFile);
        assertFalse(game.isOver());
    }

    @ParameterizedTest
    @CsvSource("first4RowsWhiteAndLast4RowsBlack8x8Board")
    void withEqualNumberOfBlackAndWhiteDisksHasNoWinner(String boardFileName) throws IOException {
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource(boardFileName);
        Game game = new OthelloGame("Bob", "Alice", boardFile);
        assertNull(game.getWinner());
    }

    @Test
    void gameWith2017FinalBoardIsWonByAlice() throws IOException {
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource("othello2017FinalBoard");
        Game game = new OthelloGame("Bob", "Alice", boardFile);
        assertEquals("Alice", game.getWinner().getName());
    }


}
