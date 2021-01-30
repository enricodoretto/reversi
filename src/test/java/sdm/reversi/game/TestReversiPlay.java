package sdm.reversi.game;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestReversiPlay {

    @Test
    void isOverAfterTwoMoves() throws IOException, URISyntaxException {
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource("othello4x4BoardTwoMovesMissing");
        URL logFile = Thread.currentThread().getContextClassLoader().getResource("gameLog/logOfIsOverAfterTwoMoves");
        URL inputMoveFile = Thread.currentThread().getContextClassLoader().getResource("gameInputs/movesForIsOverAfterTwoMoves");
        String messages = Files.readString(Paths.get(logFile.toURI()));
        System.setIn(inputMoveFile.openStream());
        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));
        Game game = new ReversiGame("Bob", "Alice", boardFile);
        game.play();
        assertAll(() -> assertEquals("Bob", game.getWinner().getName()),
                () -> assertEquals(messages, fakeStandardOutput.toString()));
    }

    @Test
    void requiresMoveRepetitionIfGivenMoveIsInvalid() throws IOException, URISyntaxException {
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource("othello4x4BoardTwoMovesMissing");
        URL logFile = Thread.currentThread().getContextClassLoader().getResource("gameLog/logOfRequiresMoveRepetitionIfGivenMoveIsInvalid");
        URL inputMoveFile = Thread.currentThread().getContextClassLoader().getResource("gameInputs/movesForRequiresMoveRepetitionIfGivenMoveIsInvalid");
        String messages = Files.readString(Paths.get(logFile.toURI()));
        System.setIn(inputMoveFile.openStream());
        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));
        Game game = new ReversiGame("Bob", "Alice", boardFile);
        game.play();
        assertAll(() -> assertEquals("Bob", game.getWinner().getName()),
                () -> assertEquals(messages, fakeStandardOutput.toString()));
    }

    @Test
    void full4x4GameWonByBob() throws URISyntaxException, IOException {
        URL logFile = Thread.currentThread().getContextClassLoader().getResource("gameLog/expectedGameLogReversi4x4");
        URL inputMoveFile = Thread.currentThread().getContextClassLoader().getResource("gameInputs/movesFor4x4ReversiGameWonByBob");
        String messages = Files.readString(Paths.get(logFile.toURI()));
        System.setIn(inputMoveFile.openStream());
        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));
        Game game = new ReversiGame("Bob", "Alice", 4);
        game.play();
        assertAll(() -> assertEquals("Bob", game.getWinner().getName()),
                () -> assertEquals(messages, fakeStandardOutput.toString()));
    }

}

