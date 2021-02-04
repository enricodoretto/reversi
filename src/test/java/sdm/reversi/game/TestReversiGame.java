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
        Game game = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withCustomBoard(boardFile).buildReversi();
        assertAll(
                () -> assertTrue(game.getPlayer1().isInStall()),
                () -> assertFalse(game.getPlayer2().isInStall()),
                () -> assertTrue(game.isOver()));
    }

    @ParameterizedTest
    @CsvSource({"finishedGameBoards/2011FinalBoard", "finishedGameBoards/2017FinalBoard"})
    void withOnePlayerInStallIsOver(String boardFileName) throws IOException {
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource(boardFileName);
        Game game = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withCustomBoard(boardFile).buildReversi();
        game.play();
        assertAll(
                () -> assertTrue(game.getPlayer1().isInStall()),
                () -> assertFalse(game.getPlayer2().isInStall()),
                () -> assertTrue(game.isOver()));
    }

    @ParameterizedTest
    @CsvSource({"fullBoards/allWhite8x8Board", "fullBoards/allBlack8x8Board", "tieGameBoards/first4RowsWhiteAndLast4RowsBlack8x8Board"})
    void withFullBoardIsOver(String boardFileName) throws IOException {
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource(boardFileName);
        Game game = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withCustomBoard(boardFile).buildReversi();
        game.play();
        assertTrue(game.isOver());
    }

    @ParameterizedTest
    @CsvSource({"initialBoards/othello8x8Board", "initialBoards/othello4x4Board", "initialBoards/othello16x16Board"})
    void withBoardNotFullAndAPlayerNotInStallIsNotOver(String boardFileName) throws IOException {
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource(boardFileName);
        Game game = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withCustomBoard(boardFile).buildReversi();
        assertFalse(game.isOver());
    }

    @ParameterizedTest
    @CsvSource({"tieGameBoards/first4RowsWhiteAndLast4RowsBlack8x8Board", "tieGameBoards/first4ColumnsWhiteAndLast4ColumnsBlack8x8Board", "tieGameBoards/chequered4x4Board"})
    void withEqualNumberOfBlackAndWhiteDisksHasNoWinner(String boardFileName) throws IOException {
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource(boardFileName);
        Game game = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withCustomBoard(boardFile).buildReversi();
        game.play();
        assertNull(game.getWinner());
    }

    @ParameterizedTest
    @CsvSource({"finishedGameBoards/2017FinalBoard", "fullBoards/board4x4WonByWhite", "fullBoards/board8x8WonByWhite"})
    void withMoreWhitesThanBlacksIsWonByWhite(String boardFileName) throws IOException {
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource(boardFileName);
        Game game = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withCustomBoard(boardFile).buildReversi();
        game.play();
        Assertions.assertEquals(Disk.Color.WHITE, game.getWinner().getColor());
    }

    @ParameterizedTest
    @CsvSource({"fullBoards/board4x4WonByBlack", "fullBoards/board8x8WonByBlack"})
    void withMoreBlacksThanWhitesIsWonByBlack(String boardFileName) throws IOException {
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource(boardFileName);
        Game game = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withCustomBoard(boardFile).buildReversi();
        game.play();
        assertEquals(Disk.Color.BLACK, game.getWinner().getColor());
    }

    @Test
    void calculatesScoreCorrectlyBobVsCPU() throws IOException {
        URL inputMoveFile = Thread.currentThread().getContextClassLoader().getResource("gameInputs/movesFor8x8ReversiGameVsCPU");
        assert inputMoveFile != null;
        System.setIn(inputMoveFile.openStream());
        Game game = Game.GameBuilder.CLIGameBuilder("Bob").withCPUOpponent().withBoardSize(8).buildReversi();
        game.play();
        assertAll(() -> assertEquals(25, game.getPlayer1().getScore()),
                () -> assertEquals(35, game.getPlayer2().getScore()));
    }

    @Test
    void calculatesScoreCorrectlyBobVsAlice() throws IOException {
        URL inputMoveFile = Thread.currentThread().getContextClassLoader().getResource("gameInputs/movesFor8x8ReversiFullGame");
        assert inputMoveFile != null;
        System.setIn(inputMoveFile.openStream());
        Game game = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withBoardSize(8).buildReversi();
        game.play();
        assertAll(() -> assertEquals(25, game.getPlayer1().getScore()),
                () -> assertEquals(12, game.getPlayer2().getScore()));
    }


}
