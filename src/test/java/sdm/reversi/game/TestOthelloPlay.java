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
    void isOverAfterTwoMoves() throws IOException, URISyntaxException {
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource("othello4x4BoardTwoMovesMissing");
        URL logFile = Thread.currentThread().getContextClassLoader().getResource("gameLog/logOfIsOverAfterTwoMoves");
        URL inputMoveFile = Thread.currentThread().getContextClassLoader().getResource("gameInputs/movesForIsOverAfterTwoMoves");
        String messages = Files.readString(Paths.get(logFile.toURI()));
        Game game = new OthelloGame("Bob", "Alice", boardFile);
        System.setIn(inputMoveFile.openStream());
        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));
        assertAll(() -> assertEquals("Bob", game.play().getName()),
                () -> assertEquals(messages, fakeStandardOutput.toString()));
    }

    @Test
    void requiresMoveRepetitionIfGivenMoveIsInvalid() throws IOException {
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource("othello4x4BoardTwoMovesMissing");
        Game game = new OthelloGame("Bob", "Alice", boardFile);
        String moves = "4C" + System.lineSeparator() + "4C" + System.lineSeparator() + "4D" + System.lineSeparator();
        String messages = String.format("%s's turn%n%s's turn%nInvalid move, please write another one%n", "Bob", "Alice");
        ByteArrayInputStream bais = new ByteArrayInputStream(moves.getBytes());
        System.setIn(bais);
        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));
        assertAll(() -> assertEquals("Bob", game.play().getName()),
                () -> assertEquals(messages, fakeStandardOutput.toString()));
    }

    @Test
    void makesSkipTurnIfThereAreNoAvailableMoves() throws IOException {
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource("othello4x4BoardBlackCantMove");
        Game game = new OthelloGame("Bob", "Alice", boardFile);
        String moves = "3B" + System.lineSeparator();
        String messages = String.format("%s's turn%nSorry you can make no moves!%n%s's turn%n%s's turn%nSorry you can make no moves!%n", "Bob", "Alice", "Bob");
        ByteArrayInputStream bais = new ByteArrayInputStream(moves.getBytes());
        System.setIn(bais);
        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));
        assertAll(() -> assertEquals("Alice", game.play().getName()),
                () -> assertEquals(messages, fakeStandardOutput.toString()));
    }

    @Test
    void isOverWhenPlayerQuits() throws IOException {
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource("othello4x4BoardTwoMovesMissing");
        Game game = new OthelloGame("Bob", "Alice", boardFile);
        String moves = "q" + System.lineSeparator();
        ByteArrayInputStream bais = new ByteArrayInputStream(moves.getBytes());
        System.setIn(bais);
        game.play();
        assertTrue(game.isOver());
    }

    /*@Test
    void full4x4GameWonByBob(){
        Game game = new OthelloGame("Bob", "Alice", 4);
        String moves = "1B" + System.lineSeparator() + "4A" + System.lineSeparator()
                + "3A" + System.lineSeparator() + "4B" + System.lineSeparator() + "1C" + System.lineSeparator()
                + "1D" + System.lineSeparator() + "q" + System.lineSeparator();
        String messages = String.format("%s's turn%n%s's turn%nInvalid move, please write another one%n%s's turn%n%s's turn%n%s's turn%n%s's turn%n",
                "Bob", "Alice", "Bob", "Alice", "Bob", "Alice", "Bob", "Alice", "Bob");
        ByteArrayInputStream bais = new ByteArrayInputStream(moves.getBytes());
        System.setIn(bais);
        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));
        assertAll(() -> assertEquals("Bob", game.play().getName()),
                () -> assertEquals(messages, fakeStandardOutput.toString()));
    }*/
}
