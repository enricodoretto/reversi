package sdm.reversi.game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import sdm.reversi.Disk;

import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

public class TestReversiGame {

    @Test
    void hasBlackInStallWithBoardWithOnlyOneBlackDiskIn3DAndIsOver() throws IOException {
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource("fullBoards/allWhiteAndOneBlackIn3D8x8Board");
        Game game = new ReversiGame("Bob", "Alice", boardFile);
        assertAll(
                () -> assertTrue(game.getPlayer1().isInStall()),
                () -> assertFalse(game.getPlayer2().isInStall()),
                () -> assertTrue(game.isOver()));
    }

    @ParameterizedTest
    @CsvSource("finishedGameBoards/othello2011FinalBoard, finishedGameBoards/othello2017FinalBoard")
    void withOnePlayerInStallIsOver(String boardFileName) throws IOException {
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource(boardFileName);
        Game game = new ReversiGame("Bob", "Alice", boardFile);
        game.play();
        assertAll(
                () -> assertTrue(game.getPlayer1().isInStall()),
                () -> assertFalse(game.getPlayer2().isInStall()),
                () -> assertTrue(game.isOver()));
    }

    @ParameterizedTest
    @CsvSource("fullBoards/allWhite8x8Board, fullBoards/allBlack8x8Board, tieGameBoards/first4RowsWhiteAndLast4RowsBlack8x8Board")
    void withFullBoardIsOver(String boardFileName) throws IOException {
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource(boardFileName);
        Game game = new ReversiGame("Bob", "Alice", boardFile);
        game.play();
        assertTrue(game.isOver());
    }

    @ParameterizedTest
    @CsvSource("othelloInitialBoards/othello8x8Board, othelloInitialBoards/othello4x4Board, othelloInitialBoards/othello16x16Board")
    void withBoardNotFullAndAPlayerNotInStallIsNotOver(String boardFileName) throws IOException {
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource(boardFileName);
        Game game = new ReversiGame("Bob", "Alice", boardFile);
        assertFalse(game.isOver());
    }

    @ParameterizedTest
    @CsvSource("tieGameBoards/first4RowsWhiteAndLast4RowsBlack8x8Board, tieGameBoards/first4ColumnsWhiteAndLast4ColumnsBlack8x8Board, tieGameBoards/chequered4x4Board")
    void withEqualNumberOfBlackAndWhiteDisksHasNoWinner(String boardFileName) throws IOException {
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource(boardFileName);
        Game game = new ReversiGame("Bob", "Alice", boardFile);
        game.play();
        assertNull(game.getWinner());
    }

    @ParameterizedTest
    @CsvSource("finishedGameBoards/othello2017FinalBoard, fullBoards/othello4x4BoardWonByWhite, fullBoards/othello8x8BoardWonByWhite")
    void withMoreWhitesThanBlacksIsWonByWhite(String boardFileName) throws IOException {
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource(boardFileName);
        Game game = new ReversiGame("Bob", "Alice", boardFile);
        game.play();
        Assertions.assertEquals(Disk.Color.WHITE, game.getWinner().getColor());
    }

    @ParameterizedTest
    @CsvSource("fullBoards/othello4x4BoardWonByBlack, fullBoards/othello8x8BoardWonByBlack")
    void withMoreBlacksThanWhitesIsWonByBlack(String boardFileName) throws IOException {
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource(boardFileName);
        Game game = new ReversiGame("Bob", "Alice", boardFile);
        game.play();
        assertEquals(Disk.Color.BLACK, game.getWinner().getColor());
    }



}
