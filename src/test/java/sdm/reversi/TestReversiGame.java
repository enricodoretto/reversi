package sdm.reversi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TestReversiGame {

    @Test
    void blackIsInStallWithCustomBoardWithOnlyOneBlackDiskIn3D() throws IOException {
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource("allWhiteAndOneBlackIn3D8x8Board");
        Game game = new ReversiGame("Bob", "Alice", boardFile);
        assertTrue(game.getPlayer1().isInStall());
    }

    @Test
    void gameWithFullWhiteBoardIsOver() throws IOException {
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource("allWhite8x8Board");
        Game game = new ReversiGame("Bob", "Alice", boardFile);
        assertTrue(game.isOver());
    }

    @Test
    void gameWithFullBlackBoardIsOver() throws IOException {
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource("allBlack8x8Board");
        Game game = new ReversiGame("Bob", "Alice", boardFile);
        assertTrue(game.isOver());
    }

    @Test
    void gameWithHalfBlackAndHalfWhiteBoardHasNoWinner() throws IOException {
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource("first4RowsWhiteAndLast4RowsBlack8x8Board");
        Game game = new ReversiGame("Bob", "Alice", boardFile);
        assertNull(game.getWinner());
    }

    @Test
    void gameWith2017FinalBoardIsOver() throws IOException {
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource("reversi2017FinalBoard");
        Game game = new ReversiGame("Bob", "Alice", boardFile);
        assertTrue(game.isOver());
    }

    @Test
    void gameWith2017FinalBoardIsWonByAlice() throws IOException {
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource("reversi2017FinalBoard");
        Game game = new ReversiGame("Bob", "Alice", boardFile);
        assertEquals("Alice", game.getWinner().getName());
    }

}
