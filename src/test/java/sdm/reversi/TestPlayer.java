package sdm.reversi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import sdm.reversi.player.Player;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class TestPlayer {

    @ParameterizedTest
    @CsvSource({"Bob, BLACK", "Alice, WHITE"})
    public void keepsAssignedNameAndColor(String name, Disk.Color color){
        Player player = new Player(name, color);
        assertAll(
                () -> assertEquals(name, player.getName()),
                () -> assertEquals(color, player.getColor())
        );
    }

    @ParameterizedTest
    @CsvSource({"3A 5D 6H", "4D 6G 7H", "2A 4F 5C"})
    void withCLIManagerDisplaysSuggestedMovesOnCLI(String suggestedMovesOnCLI) {
        Player player = new Player("Bob", Disk.Color.BLACK);
        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));
        Set<Coordinate> suggestedMoves = Arrays.stream(suggestedMovesOnCLI.split(" "))
                .map(Coordinate::new).collect(Collectors.toSet());
        player.suggestMoves(suggestedMoves);
        assertEquals(suggestedMovesOnCLI+System.lineSeparator(), fakeStandardOutput.toString());
    }

    @Test
    void withCLIManagerDisplaysIllegalMoveMessageOnCLI() {
        Player player = new Player("Bob", Disk.Color.BLACK);
        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));
        player.illegalMove();
        assertEquals("Invalid move, please choose another one"+ System.lineSeparator(), fakeStandardOutput.toString());
    }

}
