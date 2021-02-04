package sdm.reversi.game;

import org.junit.jupiter.api.Test;
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
    void isOverAfterTwoMovesWithCustomBoard() throws IOException, URISyntaxException {
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource("board8x8WithTwoMovesMissing");
        URL logFile = Thread.currentThread().getContextClassLoader().getResource("gameLog/logOfIsOverAfterTwoMoves");
        URL inputMoveFile = Thread.currentThread().getContextClassLoader().getResource("gameInputs/movesForIsOverAfterTwoMoves");
        assert logFile != null;
        String messages = Files.readString(Paths.get(logFile.toURI()));
        assert inputMoveFile != null;
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
        assert logFile != null;
        String messages = Files.readString(Paths.get(logFile.toURI()));
        assert inputMoveFile != null;
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
        assert logFile != null;
        String messages = Files.readString(Paths.get(logFile.toURI()));
        assert inputMoveFile != null;
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
        assert inputMoveFile != null;
        System.setIn(inputMoveFile.openStream());
        Game game = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withCustomBoard(boardFile).buildReversi();
        game.play();
        assertTrue(game.isOver());
    }

    @Test
    void full4x4GameWonByBob() throws URISyntaxException, IOException {
        URL logFile = Thread.currentThread().getContextClassLoader().getResource("gameLog/expectedGameLogReversi4x4");
        URL inputMoveFile = Thread.currentThread().getContextClassLoader().getResource("gameInputs/movesFor4x4ReversiGameWonByBob");
        assert logFile != null;
        String messages = Files.readString(Paths.get(logFile.toURI()));
        assert inputMoveFile != null;
        System.setIn(inputMoveFile.openStream());
        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));
        Game game = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withBoardSize(4).buildReversi();
        game.play();
        assertAll(() -> assertEquals("Bob", game.getWinner().getName()),
                () -> assertEquals(messages, fakeStandardOutput.toString()));
    }

    @Test
    void endsInTie() throws URISyntaxException, IOException {
        URL logFile = Thread.currentThread().getContextClassLoader().getResource("gameLog/expectedGameLogReversiEndsTie");
        URL inputMoveFile = Thread.currentThread().getContextClassLoader().getResource("gameInputs/movesFor8x8ReversiGameEndsTie");
        assert logFile != null;
        String messages = Files.readString(Paths.get(logFile.toURI()));
        assert inputMoveFile != null;
        System.setIn(inputMoveFile.openStream());
        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));
        Game game = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withBoardSize(8).buildReversi();
        game.play();
        assertAll(() -> assertNull(game.getWinner()),
                () -> assertEquals(messages, fakeStandardOutput.toString()));
    }

    @Test
    void full8x8GameWonByBob() throws URISyntaxException, IOException {
        URL logFile = Thread.currentThread().getContextClassLoader().getResource("gameLog/expectedGameLogFullReversi8x8");
        URL inputMoveFile = Thread.currentThread().getContextClassLoader().getResource("gameInputs/movesFor8x8ReversiFullGame");
        assert logFile != null;
        String messages = Files.readString(Paths.get(logFile.toURI()));
        assert inputMoveFile != null;
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
        URL logFile = Thread.currentThread().getContextClassLoader().getResource("gameLog/expectedGameLogVsCPU8x8Reversi");
        URL inputMoveFile = Thread.currentThread().getContextClassLoader().getResource("gameInputs/movesFor8x8ReversiGameVsCPU");
        assert logFile != null;
        String messages = Files.readString(Paths.get(logFile.toURI()));
        assert inputMoveFile != null;
        System.setIn(inputMoveFile.openStream());
        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));
        Game game = Game.GameBuilder.CLIGameBuilder("Bob").withCPUOpponent().withBoardSize(8).buildReversi();
        game.play();
        assertAll(() -> assertEquals("CPU", game.getWinner().getName()),
                () -> assertEquals(messages, fakeStandardOutput.toString()));
    }

}

