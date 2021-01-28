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
    void full4x4GameWonByBob() throws URISyntaxException, IOException {
        URL logFile = Thread.currentThread().getContextClassLoader().getResource("gameLog/expectedGameLogReversi4x4");
        URL inputMoveFile = Thread.currentThread().getContextClassLoader().getResource("gameInputs/movesFor4x4ReversiGameWonByBob");
        String messages = Files.readString(Paths.get(logFile.toURI()));
        System.setIn(inputMoveFile.openStream());
        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));
        Game game = new ReversiGame("Bob", "Alice", 4);
        assertAll(() -> assertEquals("Bob", game.play().getName()),
                () -> assertEquals(messages, fakeStandardOutput.toString()));
    }

}

