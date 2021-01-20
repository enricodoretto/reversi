package sdm.reversi;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestGame {
    @Test
    void createGameWithPlayerBobWithBlackDiskAndPlayerAliceWithWhiteDisk() {
        Game game = new Game("Bob", "Alice");
        assertAll(
                () -> assertEquals(new Player("Bob", Disk.Color.BLACK), game.getPlayer1()),
                () -> assertEquals(new Player("Alice", Disk.Color.WHITE), game.getPlayer2())
        );
    }
}
