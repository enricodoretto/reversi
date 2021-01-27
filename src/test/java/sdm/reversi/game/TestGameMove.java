package sdm.reversi.game;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import sdm.reversi.Coordinate;
import sdm.reversi.board.TestBoardIsRepresented;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TestGameMove {

    @ParameterizedTest
    @CsvSource("6E,3D,5F")
    void isValidIfCellIsAvailableAndHasNeighborOfDifferentColorAndCausesDisksToFlip(Coordinate coordinate) throws IOException {
        Game othelloGame = new OthelloGame("Bob", "Alice");
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource("othello8x8Board");
        Game reversiGame = new ReversiGame("Bob", "Alice", boardFile);
        assertAll(() -> assertTrue(othelloGame.isValidMove(coordinate)),
                () -> assertTrue(reversiGame.isValidMove(coordinate)));
    }

    @ParameterizedTest
    @CsvSource("5D,5F,4D,4E")
    void isInvalidIfCellIsAlreadyFull(Coordinate coordinate) throws IOException {
        Game othelloGame = new OthelloGame("Bob", "Alice");
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource("othello8x8Board");
        Game reversiGame = new ReversiGame("Bob", "Alice", boardFile);
        assertAll(() -> assertFalse(othelloGame.isValidMove(coordinate)),
                () -> assertFalse(reversiGame.isValidMove(coordinate)));
    }

    @ParameterizedTest
    @CsvSource("6C,1A,8H")
    void isInvalidIfCellDoesNotHaveNeighborOfDifferentColor(Coordinate coordinate) throws IOException {
        Game othelloGame = new OthelloGame("Bob", "Alice");
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource("othello8x8Board");
        Game reversiGame = new ReversiGame("Bob", "Alice", boardFile);
        assertAll(() -> assertFalse(othelloGame.isValidMove(coordinate)),
                () -> assertFalse(reversiGame.isValidMove(coordinate)));
    }

    @ParameterizedTest
    @CsvSource("3C,6F")
    void isInvalidIfDoesNotCauseDisksToFlip(Coordinate coordinate) throws IOException {
        Game othelloGame = new OthelloGame("Bob", "Alice");
        URL boardFile = TestBoardIsRepresented.class.getClassLoader().getResource("othello8x8Board");
        Game reversiGame = new ReversiGame("Bob", "Alice", boardFile);
        assertAll(() -> assertFalse(othelloGame.isValidMove(coordinate)),
                () -> assertFalse(reversiGame.isValidMove(coordinate)));
    }

    @ParameterizedTest
    @CsvSource({"6E,5E", "5F,5E", "4C,4D", "3D,4D"})
    void returnsCoordinatesToFlipIfValid(Coordinate blackDiskPosition, Coordinate coordinateToFlip) {
        Game game = new OthelloGame("Bob", "Alice");
        Set<Coordinate> coordinatesToFlip = Set.of(coordinateToFlip);
        assertEquals(coordinatesToFlip, game.getDisksToFlip(blackDiskPosition));
    }

    @ParameterizedTest
    @CsvSource("6F,6D, 3E, 3C ")
    void returnsNoCoordinatesToFlipIfInvalid(Coordinate blackDiskPosition) {
        Game game = new OthelloGame("Bob", "Alice");
        assertNull(game.getDisksToFlip(blackDiskPosition));
    }

    @ParameterizedTest
    @CsvSource({"othello8x8Board,4C,othello8x8BoardAfterBlackIn4C",
            "othello8x8Board,6E,othello8x8BoardAfterBlackIn6E",
            "othello8x8CustomBoard1,6F,othello8x8CustomBoard1AfterBlackIn6F"})
    void flipsDisksIfValid(String originalBoardFileName, Coordinate moveCoordinate, String expectedFinalBoardFileName) throws URISyntaxException, IOException {
        URL originalBoardFile = TestBoardIsRepresented.class.getClassLoader().getResource(originalBoardFileName);
        URL finalBoardFile = TestBoardIsRepresented.class.getClassLoader().getResource(expectedFinalBoardFileName);
        String finalOthelloBoard = Files.readString(Paths.get(finalBoardFile.toURI()));

        Game game = new OthelloGame("Bob", "Alice", originalBoardFile);
        game.makeMove(moveCoordinate);

        assertEquals(finalOthelloBoard, game.getBoardRepresentation());
    }

    @ParameterizedTest
    @CsvSource("1A, 10H, 3C")
    void cannotBeMadeIfNotValid(String blackDiskCoordinate) {
        Game game = new OthelloGame("Bob", "Alice");
        assertThrows(IllegalArgumentException.class, () -> game.makeMove(new Coordinate(blackDiskCoordinate)));
    }
}
