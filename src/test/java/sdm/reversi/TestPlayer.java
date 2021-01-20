package sdm.reversi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class TestPlayer {

    @ParameterizedTest
    @CsvSource({"Bob, BLACK", "Alice, WHITE"})
    public void createPlayerWithACertainColor(String name, Disk.Color color){
        Player player = new Player(name, color);
        assertAll(
                () -> assertEquals(name, player.getName()),
                () -> assertEquals(color, player.getColor())
        );
    }

    @Test
    public void createPlayerBobWithBlackDisk(){
        Player player = new Player("Bob", Disk.Color.BLACK);
        assertAll(
                () -> assertEquals("Bob", player.getName()),
                () -> assertEquals(Disk.Color.BLACK, player.getColor())
        );
    }

    @Test
    public void createPlayerAliceWithWhiteDisk(){
        Player player = new Player("Alice", Disk.Color.WHITE);
        assertAll(
                () -> assertEquals("Alice", player.getName()),
                () -> assertEquals(Disk.Color.WHITE, player.getColor())
        );
    }

}
