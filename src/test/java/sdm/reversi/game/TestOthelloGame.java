package sdm.reversi.game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import sdm.reversi.Coordinate;
import sdm.reversi.Disk;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TestOthelloGame {

    @Test
    void suggestsValidPositionsForFirstBlackMove() {
        Game game = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withBoardSize(8).buildOthello();
        Map<Coordinate, Set<Coordinate>> possibleDisksToFlip = Map.of(
                new Coordinate("3D"), Set.of(new Coordinate("4D")),
                new Coordinate("4C"), Set.of(new Coordinate("4D")),
                new Coordinate("6E"), Set.of(new Coordinate("5E")),
                new Coordinate("5F"), Set.of(new Coordinate("5E"))
        );
        assertEquals(possibleDisksToFlip, game.getPlayerPossibleMoves());
    }

    @Test
    void hasBlackInStallWithBoardWithOnlyOneBlackDiskIn3D() throws IOException {
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource("fullBoards/allWhiteAndOneBlackIn3D8x8Board");
        Game game = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withCustomBoard(boardFile).buildOthello();
        game.play();
        assertTrue(game.getPlayer1().isInStall());
    }

    @ParameterizedTest
    @CsvSource("finishedGameBoards/2011FinalBoard, finishedGameBoards/2017FinalBoard")
    void withBothPlayersInStallIsOver(String boardFileName) throws IOException {
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource(boardFileName);
        Game game = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withCustomBoard(boardFile).buildOthello();
        game.play();
        assertTrue(game.isOver());
    }

    @ParameterizedTest
    @CsvSource("fullBoards/allWhite8x8Board, allBlack8x8Board, /tieGameBoards/first4RowsWhiteAndLast4RowsBlack8x8Board")
    void withFullBoardIsOver(String boardFileName) throws IOException {
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource(boardFileName);
        Game game = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withCustomBoard(boardFile).buildOthello();
        game.play();
        assertTrue(game.isOver());
    }

    @ParameterizedTest
    @CsvSource("initialBoards/othello8x8Board, othelloInitialBoards/othello4x4Board, othelloInitialBoards/othello16x16Board")
    void withBoardNotFullAndPlayersNotInStallIsNotOver(String boardFileName) throws IOException {
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource(boardFileName);
        Game game = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withCustomBoard(boardFile).buildOthello();
        assertFalse(game.isOver());
    }

    @ParameterizedTest
    @CsvSource("tieGameBoards/first4RowsWhiteAndLast4RowsBlack8x8Board, tieGameBoards/first4ColumnsWhiteAndLast4ColumnsBlack8x8Board, tieGameBoards/chequered4x4Board, tieGameBoards/chequered14x14Board")
    void withEqualNumberOfBlackAndWhiteDisksHasNoWinner(String boardFileName) throws IOException {
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource(boardFileName);
        Game game = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withCustomBoard(boardFile).buildOthello();
        game.play();
        assertNull(game.getWinner());
    }

    @ParameterizedTest
    @CsvSource("finishedGameBoards/2017FinalBoard, fullBoards/board4x4WonByWhite, fullBoards/board8x8WonByWhite")
    void withMoreWhitesThanBlacksIsWonByWhite(String boardFileName) throws IOException {
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource(boardFileName);
        Game game = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withCustomBoard(boardFile).buildOthello();
        game.play();
        Assertions.assertEquals(Disk.Color.WHITE, game.getWinner().getColor());
    }

    @ParameterizedTest
    @CsvSource("fullBoards/board4x4WonByBlack, fullBoards/board8x8WonByBlack")
    void withMoreBlacksThanWhitesIsWonByBlack(String boardFileName) throws IOException {
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource(boardFileName);
        Game game = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withCustomBoard(boardFile).buildOthello();
        game.play();
        assertEquals(Disk.Color.BLACK, game.getWinner().getColor());
    }


}
