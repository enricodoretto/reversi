package sdm.reversi.board;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import sdm.reversi.Coordinate;
import sdm.reversi.Disk;
import sdm.reversi.game.Game;

import java.io.IOException;
import java.net.URL;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TestMoveOnBoard {

    @ParameterizedTest
    @CsvSource({"6E,5E", "5F,5E", "4C,4D", "3D,4D"})
    void returnsCoordinatesToFlipIfValid(Coordinate blackDiskPosition, Coordinate coordinateToFlip) throws IOException {
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource("initialBoards/othello8x8Board");
        Board board = new Board(boardFile);
        Set<Coordinate> coordinatesToFlip = Set.of(coordinateToFlip);
        assertEquals(coordinatesToFlip, board.getDisksToFlip(blackDiskPosition, Disk.Color.BLACK));
    }

    @ParameterizedTest
    @CsvSource({"6F", "6D", "3E", "3C"})
    void returnsNoCoordinatesToFlipIfInvalid(Coordinate blackDiskPosition) throws IOException {
        Game othelloGame = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withBoardSize(8).buildOthello();
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource("initialBoards/othello8x8Board");
        Game reversiGame = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withCustomBoard(boardFile).buildReversi();
        assertAll(() -> assertNull(othelloGame.getBoard().getDisksToFlip(blackDiskPosition, Disk.Color.BLACK)),
                () -> assertNull(reversiGame.getBoard().getDisksToFlip(blackDiskPosition, Disk.Color.BLACK)));
    }

}
