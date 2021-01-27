package sdm.reversi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import sdm.reversi.game.Game;
import sdm.reversi.game.OthelloGame;
import sdm.reversi.game.ReversiGame;

import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

public class TestReversiGame {

    @Test
    void hasBlackInStallWithBoardWithOnlyOneBlackDiskIn3DAndIsOver() throws IOException {
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource("allWhiteAndOneBlackIn3D8x8Board");
        Game game = new ReversiGame("Bob", "Alice", boardFile);
        assertAll(
                () -> assertTrue(game.getPlayer1().isInStall()),
                () -> assertFalse(game.getPlayer2().isInStall()),
                () -> assertTrue(game.isOver()));
    }

    @ParameterizedTest
    @CsvSource("othello2011FinalBoard, othello2017FinalBoard")
    void withOnePlayerInStallIsOver(String boardFileName) throws IOException {
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource(boardFileName);
        Game game = new ReversiGame("Bob", "Alice", boardFile);
        game.play();
        assertAll(
                () -> assertTrue(game.getPlayer1().isInStall()),
                () -> assertFalse(game.getPlayer2().isInStall()),
                () -> assertTrue(game.isOver()));
    }

    @ParameterizedTest
    @CsvSource("allWhite8x8Board, allBlack8x8Board, first4RowsWhiteAndLast4RowsBlack8x8Board")
    void withFullBoardIsOver(String boardFileName) throws IOException {
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource(boardFileName);
        Game game = new ReversiGame("Bob", "Alice", boardFile);
        game.play();
        assertTrue(game.isOver());
    }

    @ParameterizedTest
    @CsvSource("othello8x8Board, othello4x4Board, othello16x16Board")
    void withBoardNotFullAndAPlayerNotInStallIsNotOver(String boardFileName) throws IOException {
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource(boardFileName);
        Game game = new ReversiGame("Bob", "Alice", boardFile);
        assertFalse(game.isOver());
    }

    @ParameterizedTest
    @CsvSource("first4RowsWhiteAndLast4RowsBlack8x8Board")
    void withEqualNumberOfBlackAndWhiteDisksHasNoWinner(String boardFileName) throws IOException {
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource(boardFileName);
        Game game = new ReversiGame("Bob", "Alice", boardFile);
        game.play();
        assertNull(game.getWinner());
    }

    /*@Test
    void gameWith2017FinalBoardIsWonByAlice() throws IOException {
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource("othello2017FinalBoard");
        Game game = new OthelloGame("Bob", "Alice", boardFile);
        game.play();
        assertEquals("Alice", game.getWinner().getName());
    }*/


}
