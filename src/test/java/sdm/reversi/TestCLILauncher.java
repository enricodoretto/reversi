package sdm.reversi;

import org.junit.jupiter.api.Test;
import sdm.reversi.launcher.CLILauncher;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCLILauncher {

    @Test
    void succeedsIn1v1() throws URISyntaxException, IOException {
        URL logFile = Thread.currentThread().getContextClassLoader().getResource("gameLog/fromCLILauncher1v1");
        URL inputMoveFile = Thread.currentThread().getContextClassLoader().getResource("gameInputs/movesForCLILauncher1v1");
        assert logFile != null;
        String messages = Files.readString(Paths.get(logFile.toURI()));
        assert inputMoveFile != null;
        System.setIn(inputMoveFile.openStream());
        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));

        CLILauncher.launch();
        assertEquals(messages, fakeStandardOutput.toString());
    }

    @Test
    void succeedsIn1vCPU() throws URISyntaxException, IOException {
        URL logFile = Thread.currentThread().getContextClassLoader().getResource("gameLog/fromCLILauncher1vCPU");
        URL inputMoveFile = Thread.currentThread().getContextClassLoader().getResource("gameInputs/movesForCLILauncher1vCPU");
        assert logFile != null;
        String messages = Files.readString(Paths.get(logFile.toURI()));
        assert inputMoveFile != null;
        System.setIn(inputMoveFile.openStream());
        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));

        CLILauncher.launch();
        assertEquals(messages, fakeStandardOutput.toString());
    }

    @Test
    void requiresInputRepetitionIfInvalid() throws URISyntaxException, IOException {
        URL logFile = Thread.currentThread().getContextClassLoader().getResource("gameLog/fromCLILauncherRequiresNewInputIfInvalid");
        URL inputMoveFile = Thread.currentThread().getContextClassLoader().getResource("gameInputs/movesForCLILauncherRequiresNewInputIfInvalid");
        assert logFile != null;
        String messages = Files.readString(Paths.get(logFile.toURI()));
        assert inputMoveFile != null;
        System.setIn(inputMoveFile.openStream());
        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));

        CLILauncher.launch();
        assertEquals(messages, fakeStandardOutput.toString());
    }
}
