package sdm.reversi;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestOthelloPlay {

    @Test
    void isOverAfterTwoMoves() throws IOException {
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource("othello4x4BoardTwoMovesMissing");
        Game game = new OthelloGame("Bob", "Alice", boardFile);
        String moves = "4C" + System.lineSeparator() + "4D" + System.lineSeparator();
        String messages = String.format("%s's turn%n%s's turn%n", "Bob", "Alice");
        ByteArrayInputStream bais = new ByteArrayInputStream(moves.getBytes());
        System.setIn(bais);
        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));
        assertAll(() -> assertEquals("Bob", game.play().getName()),
                () -> assertEquals(messages, fakeStandardOutput.toString()));
    }

    @Test
    void requiresMoveRepetitionIfGivenMoveIsInvalid() throws IOException {
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource("othello4x4BoardTwoMovesMissing");
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

    /*@Test
    void makesSkipTurnIfThereAreNoAvailableMoves() throws IOException {
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource("othello4x4BoardBlackCantMove");
        Game game = new OthelloGame("Bob", "Alice", boardFile);
        String moves = "3B" + System.lineSeparator();
        String messages = String.format("%s's turn%nSorry you can make no moves!%n%s's turn%n", "Bob", "Alice");
        ByteArrayInputStream bais = new ByteArrayInputStream(moves.getBytes());
        System.setIn(bais);
        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));
        assertAll(() -> assertEquals("Alice", game.play().getName()),
                () -> assertEquals(messages, fakeStandardOutput.toString()));
    }*/
}
