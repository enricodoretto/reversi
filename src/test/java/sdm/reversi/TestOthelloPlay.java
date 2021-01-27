package sdm.reversi;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestOthelloPlay {

    @Test
    void IsOverAfterTwoMoves() throws IOException {
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource("othello4x4BoardTwoMovesMissing");
        Game game = new OthelloGame("Bob", "Alice", boardFile);
        String str = "4C" + System.lineSeparator() + "4D" + System.lineSeparator();
        ByteArrayInputStream bais = new ByteArrayInputStream(str.getBytes());
        System.setIn(bais);
        assertEquals("Bob", game.play().getName());
    }
}
