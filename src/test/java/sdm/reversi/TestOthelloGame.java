package sdm.reversi;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

public class TestOthelloGame {

    @Test
    void blackIsInStallWithCustomBoardWithOnlyOneBlackDiskIn3D() throws IOException {
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource("allWhiteAndOneBlackIn3D8x8Board");
        Game game = new OthelloGame("Bob", "Alice", boardFile);
        assertTrue(game.getPlayer1().isInStall());
    }

    @Test
    void gameWithFullWhiteBoardIsOver() throws IOException {
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource("allWhite8x8Board");
        Game game = new OthelloGame("Bob", "Alice", boardFile);
        assertTrue(game.isOver());
    }

    @Test
    void gameWithFullBlackBoardIsOver() throws IOException {
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource("allBlack8x8Board");
        Game game = new OthelloGame("Bob", "Alice", boardFile);
        assertTrue(game.isOver());
    }

    @Test
    void gameWithHalfBlackAndHalfWhiteBoardHasNoWinner() throws IOException {
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource("first4RowsWhiteAndLast4RowsBlack8x8Board");
        Game game = new OthelloGame("Bob", "Alice", boardFile);
        assertNull(game.getWinner());
    }

    @Test
    void gameWith2017FinalBoardIsOver() throws IOException {
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource("othello2017FinalBoard");
        Game game = new OthelloGame("Bob", "Alice", boardFile);
        assertTrue(game.isOver());
    }

    @Test
    void gameWith2017FinalBoardIsWonByAlice() throws IOException {
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource("othello2017FinalBoard");
        Game game = new OthelloGame("Bob", "Alice", boardFile);
        assertEquals("Alice", game.getWinner().getName());
    }

}
