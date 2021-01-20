package sdm.reversi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class TestPlayer {

    @Test
    public void createPlayerBobWithBlackDisk(){

        Player player = new Player("Bob", Disk.Color.BLACK);
        assertAll(
                () -> assertEquals("Bob", player.getName()),
                () -> assertEquals(Disk.Color.BLACK, player.getColor())
        );
    }
}
