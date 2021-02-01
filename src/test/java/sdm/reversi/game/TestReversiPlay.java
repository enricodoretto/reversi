package sdm.reversi.game;

import org.junit.jupiter.api.Test;
import sdm.reversi.manager.CLIManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class TestReversiPlay {

    @Test
    void isOverAfterTwoMoves() throws IOException, URISyntaxException {
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource("board8x8WithTwoMovesMissing");
        URL logFile = Thread.currentThread().getContextClassLoader().getResource("gameLog/logOfIsOverAfterTwoMoves");
        URL inputMoveFile = Thread.currentThread().getContextClassLoader().getResource("gameInputs/movesForIsOverAfterTwoMoves");
        String messages = Files.readString(Paths.get(logFile.toURI()));
        System.setIn(inputMoveFile.openStream());
        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));
        Game game = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withCustomBoard(boardFile).buildReversi();
        game.play();
        assertAll(() -> assertEquals("Bob", game.getWinner().getName()),
                () -> assertEquals(messages, fakeStandardOutput.toString()));
    }

    @Test
    void requiresMoveRepetitionIfGivenMoveIsInvalid() throws IOException, URISyntaxException {
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource("board8x8WithTwoMovesMissing");
        URL logFile = Thread.currentThread().getContextClassLoader().getResource("gameLog/logOfRequiresMoveRepetitionIfGivenMoveIsInvalid");
        URL inputMoveFile = Thread.currentThread().getContextClassLoader().getResource("gameInputs/movesForRequiresMoveRepetitionIfGivenMoveIsInvalid");
        String messages = Files.readString(Paths.get(logFile.toURI()));
        System.setIn(inputMoveFile.openStream());
        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));
        Game game = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withCustomBoard(boardFile).buildReversi();
        game.play();
        assertAll(() -> assertEquals("Bob", game.getWinner().getName()),
                () -> assertEquals(messages, fakeStandardOutput.toString()));
    }

    @Test
    void isOverIfAPlayerHasNoPossibleMoves() throws IOException, URISyntaxException {
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource("board4x4BlackCantMove");
        URL logFile = Thread.currentThread().getContextClassLoader().getResource("gameLog/logOfReversiIsOverIfAPlayerHasNoPossibleMoves");
        URL inputMoveFile = Thread.currentThread().getContextClassLoader().getResource("gameInputs/movesForMakesSkipTurnIfThereAreNoAvailableMoves");
        String messages = Files.readString(Paths.get(logFile.toURI()));
        System.setIn(inputMoveFile.openStream());
        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));
        Game game = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withCustomBoard(boardFile).buildReversi();
        game.play();
        assertAll(() -> assertEquals("Alice", game.getWinner().getName()),
                () -> assertEquals(messages, fakeStandardOutput.toString()));
    }

    @Test
    void isOverWhenPlayerQuits() throws IOException {
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource("board8x8WithTwoMovesMissing");
        URL inputMoveFile = Thread.currentThread().getContextClassLoader().getResource("gameInputs/movesForIsOverWhenPlayerQuits");
        System.setIn(inputMoveFile.openStream());
        Game game = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withCustomBoard(boardFile).buildReversi();
        game.play();
        assertTrue(game.isOver());
    }

    @Test
    void full4x4GameWonByBob() throws URISyntaxException, IOException {
        URL logFile = Thread.currentThread().getContextClassLoader().getResource("gameLog/expectedGameLogReversi4x4");
        URL inputMoveFile = Thread.currentThread().getContextClassLoader().getResource("gameInputs/movesFor4x4ReversiGameWonByBob");
        String messages = Files.readString(Paths.get(logFile.toURI()));
        System.setIn(inputMoveFile.openStream());
        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));
        Game game = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withBoardSize(4).buildReversi();
        game.play();
        assertAll(() -> assertEquals("Bob", game.getWinner().getName()),
                () -> assertEquals(messages, fakeStandardOutput.toString()));
    }

    @Test
    void full8x8GameWonByBob() throws URISyntaxException, IOException {
        URL logFile = Thread.currentThread().getContextClassLoader().getResource("gameLog/expectedGameLogFullReversi8x8");
        URL inputMoveFile = Thread.currentThread().getContextClassLoader().getResource("gameInputs/movesFor8x8ReversiFullGame");
        String messages = Files.readString(Paths.get(logFile.toURI()));
        System.setIn(inputMoveFile.openStream());
        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));
        Game game = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withBoardSize(8).buildReversi();
        game.play();
        assertAll(() -> assertEquals("Bob", game.getWinner().getName()),
                () -> assertEquals(messages, fakeStandardOutput.toString()));
    }

    @Test
    void full8x8GameVsCPUWonByCPU() throws URISyntaxException, IOException {
        URL logFile = Thread.currentThread().getContextClassLoader().getResource("gameLog/expectedGameLogVsCPU");
        URL inputMoveFile = Thread.currentThread().getContextClassLoader().getResource("gameInputs/movesFor8x8GameVsCPU");
        String messages = Files.readString(Paths.get(logFile.toURI()));
        System.setIn(inputMoveFile.openStream());
        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));
        Game game = Game.GameBuilder.CLIGameBuilder("Bob").withCPUOpponent().buildReversi();
        game.play();
        assertAll(() -> assertEquals("CPU", game.getWinner().getName()),
                () -> assertEquals(messages, fakeStandardOutput.toString()));
    }

}

