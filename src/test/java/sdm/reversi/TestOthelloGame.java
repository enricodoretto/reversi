package sdm.reversi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

public class TestOthelloGame {

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
