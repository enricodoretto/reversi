package sdm.reversi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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

}
