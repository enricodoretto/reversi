package sdm.reversi.game;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class TestOthelloPlay {

    @Test
    void isOverAfterTwoMovesWithCustomBoard() throws IOException, URISyntaxException {
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource("board8x8WithTwoMovesMissing");
        URL logFile = Thread.currentThread().getContextClassLoader().getResource("gameLog/isOverAfterTwoMoves");
        URL inputMoveFile = Thread.currentThread().getContextClassLoader().getResource("gameInputs/movesForIsOverAfterTwoMoves");
        assert logFile != null;
        String messages = Files.readString(Paths.get(logFile.toURI()));
        assert inputMoveFile != null;
        System.setIn(inputMoveFile.openStream());
        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));
        Game game = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withCustomBoard(boardFile).buildOthello();
        game.play();
        assertAll(() -> assertEquals("Bob", game.getWinner().getName()),
                () -> assertEquals(messages, fakeStandardOutput.toString()));
    }

    @Test
    void requiresMoveRepetitionIfGivenMoveIsInvalid() throws IOException, URISyntaxException {
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource("board8x8WithTwoMovesMissing");
        URL logFile = Thread.currentThread().getContextClassLoader().getResource("gameLog/requiresMoveRepetitionIfGivenMoveIsInvalid");
        URL inputMoveFile = Thread.currentThread().getContextClassLoader().getResource("gameInputs/movesForRequiresMoveRepetitionIfGivenMoveIsInvalid");
        assert logFile != null;
        String messages = Files.readString(Paths.get(logFile.toURI()));
        assert inputMoveFile != null;
        System.setIn(inputMoveFile.openStream());
        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));
        Game game = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withCustomBoard(boardFile).buildOthello();
        game.play();
        assertAll(() -> assertEquals("Bob", game.getWinner().getName()),
                () -> assertEquals(messages, fakeStandardOutput.toString()));
    }

    @Test
    void makesSkipTurnIfThereAreNoAvailableMoves() throws IOException, URISyntaxException {
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource("board4x4BlackCantMove");
        URL logFile = Thread.currentThread().getContextClassLoader().getResource("gameLog/makesSkipTurnIfThereAreNoAvailableMoves");
        URL inputMoveFile = Thread.currentThread().getContextClassLoader().getResource("gameInputs/movesForMakesSkipTurnIfThereAreNoAvailableMoves");
        assert logFile != null;
        String messages = Files.readString(Paths.get(logFile.toURI()));
        assert inputMoveFile != null;
        System.setIn(inputMoveFile.openStream());
        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));
        Game game = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withCustomBoard(boardFile).buildOthello();
        game.play();
        assertAll(() -> assertEquals("Alice", game.getWinner().getName()),
                () -> assertEquals(messages, fakeStandardOutput.toString()));
    }

    @Test
    void isOverWhenPlayerQuits() throws IOException {
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource("board8x8WithTwoMovesMissing");
        URL inputMoveFile = Thread.currentThread().getContextClassLoader().getResource("gameInputs/movesForIsOverWhenPlayerQuits");
        assert inputMoveFile != null;
        System.setIn(inputMoveFile.openStream());
        Game game = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withCustomBoard(boardFile).buildOthello();
        game.play();
        assertTrue(game.isOver());
    }

    @Test
    void endsInTie() throws URISyntaxException, IOException {
        URL logFile = Thread.currentThread().getContextClassLoader().getResource("gameLog/othello8x8EndsTie");
        assert logFile != null;
        String messages = Files.readString(Paths.get(logFile.toURI()));
        ByteArrayInputStream bais = new ByteArrayInputStream(("q").getBytes());
        System.setIn(bais);
        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));
        Game game = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withBoardSize(8).buildOthello();
        game.play();
        assertAll(() -> assertNull(game.getWinner()),
                () -> assertEquals(messages, fakeStandardOutput.toString()));
    }

    @Test
    void full4x4GameVsCPUWonByCPU() throws URISyntaxException, IOException {
        URL logFile = Thread.currentThread().getContextClassLoader().getResource("gameLog/fullOthello4x4_1vCPU");
        URL inputMoveFile = Thread.currentThread().getContextClassLoader().getResource("gameInputs/movesFor4x4OthelloGameVsCPU");
        assert logFile != null;
        String messages = Files.readString(Paths.get(logFile.toURI()));
        assert inputMoveFile != null;
        System.setIn(inputMoveFile.openStream());
        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));
        Game game = Game.GameBuilder.CLIGameBuilder("bob").withCPUOpponent().withBoardSize(4).buildOthello();
        game.play();
        assertAll(() -> assertEquals("CPU", game.getWinner().getName()),
                () -> assertEquals(messages, fakeStandardOutput.toString()));
    }

    @Test
    void full4x4GameWonByBob() throws URISyntaxException, IOException {
        URL logFile = Thread.currentThread().getContextClassLoader().getResource("gameLog/fullOthello4x4_1v1");
        URL inputMoveFile = Thread.currentThread().getContextClassLoader().getResource("gameInputs/movesFor4x4OthelloGameWonByBob");
        assert logFile != null;
        String messages = Files.readString(Paths.get(logFile.toURI()));
        assert inputMoveFile != null;
        System.setIn(inputMoveFile.openStream());
        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));
        Game game = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withBoardSize(4).buildOthello();
        game.play();
        assertAll(() -> assertEquals("Bob", game.getWinner().getName()),
                () -> assertEquals(messages, fakeStandardOutput.toString()));
    }

}
