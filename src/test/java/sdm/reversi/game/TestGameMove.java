package sdm.reversi.game;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import sdm.reversi.Coordinate;
import sdm.reversi.Disk;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TestGameMove {

    @ParameterizedTest
    @CsvSource({"6E","3D","5F"})
    void isValidIfCellIsAvailableAndHasNeighborOfDifferentColorAndCausesDisksToFlip(Coordinate coordinate) throws IOException {
        Game othelloGame = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withBoardSize(8).buildOthello();
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource("initialBoards/othello8x8Board");
        Game reversiGame = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withCustomBoard(boardFile).buildReversi();
        assertAll(() -> assertTrue(othelloGame.isValidMove(coordinate)),
                () -> assertTrue(reversiGame.isValidMove(coordinate)));
    }

    @ParameterizedTest
    @CsvSource({"5D","5E","4D","4E"})
    void isInvalidIfCellIsAlreadyFull(Coordinate coordinate) throws IOException {
        Game othelloGame = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withBoardSize(8).buildOthello();
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource("initialBoards/othello8x8Board");
        Game reversiGame = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withCustomBoard(boardFile).buildReversi();
        assertAll(() -> assertFalse(othelloGame.isValidMove(coordinate)),
                () -> assertFalse(reversiGame.isValidMove(coordinate)));
    }

    @ParameterizedTest
    @CsvSource({"6C","1A","8H"})
    void isInvalidIfCellDoesNotHaveNeighborOfDifferentColor(Coordinate coordinate) throws IOException {
        Game othelloGame = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withBoardSize(8).buildOthello();
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource("initialBoards/othello8x8Board");
        Game reversiGame = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withCustomBoard(boardFile).buildReversi();
        assertAll(() -> assertFalse(othelloGame.isValidMove(coordinate)),
                () -> assertFalse(reversiGame.isValidMove(coordinate)));
    }

    @ParameterizedTest
    @CsvSource({"3C","6F"})
    void isInvalidIfDoesNotCauseDisksToFlip(Coordinate coordinate) throws IOException {
        Game othelloGame = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withBoardSize(8).buildOthello();
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource("initialBoards/othello8x8Board");
        Game reversiGame = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withCustomBoard(boardFile).buildReversi();
        assertAll(() -> assertFalse(othelloGame.isValidMove(coordinate)),
                () -> assertFalse(reversiGame.isValidMove(coordinate)));
    }

    @ParameterizedTest
    @CsvSource({"6E,5E", "5F,5E", "4C,4D", "3D,4D"})
    void returnsCoordinatesToFlipIfValid(Coordinate blackDiskPosition, Coordinate coordinateToFlip) throws IOException {
        Game othelloGame = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withBoardSize(8).buildOthello();
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource("initialBoards/othello8x8Board");
        Game reversiGame = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withCustomBoard(boardFile).buildReversi();
        Set<Coordinate> coordinatesToFlip = Set.of(coordinateToFlip);
        assertAll(() -> assertEquals(coordinatesToFlip, othelloGame.getBoard().getDisksToFlip(blackDiskPosition, Disk.Color.BLACK)),
                () -> assertEquals(coordinatesToFlip, reversiGame.getBoard().getDisksToFlip(blackDiskPosition, Disk.Color.BLACK)));
    }

    @ParameterizedTest
    @CsvSource({"6F","6D","3E","3C"})
    void returnsNoCoordinatesToFlipIfInvalid(Coordinate blackDiskPosition) throws IOException {
        Game othelloGame = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withBoardSize(8).buildOthello();
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource("initialBoards/othello8x8Board");
        Game reversiGame = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withCustomBoard(boardFile).buildReversi();
        assertAll(() -> assertNull(othelloGame.getBoard().getDisksToFlip(blackDiskPosition, Disk.Color.BLACK)),
                () -> assertNull(reversiGame.getBoard().getDisksToFlip(blackDiskPosition, Disk.Color.BLACK)));
    }

    @ParameterizedTest
    @CsvSource({"initialBoards/othello8x8Board,4C,boardsWith5Disks/othello8x8BoardAfterBlackIn4C",
            "initialBoards/othello8x8Board,6E,boardsWith5Disks/othello8x8BoardAfterBlackIn6E",
            "othello8x8CustomBoard1,6F,othello8x8CustomBoard1AfterBlackIn6F"})
    void flipsDisksIfValid(String originalBoardFileName, Coordinate moveCoordinate, String expectedFinalBoardFileName) throws URISyntaxException, IOException {
        URL originalBoardFile = Thread.currentThread().getContextClassLoader().getResource(originalBoardFileName);
        URL finalBoardFile = Thread.currentThread().getContextClassLoader().getResource(expectedFinalBoardFileName);
        assert finalBoardFile != null;
        String finalBoard = Files.readString(Paths.get(finalBoardFile.toURI()));
        Game othelloGame = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withCustomBoard(originalBoardFile).buildOthello();
        othelloGame.makeMove(moveCoordinate);
        Game reversiGame = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withCustomBoard(originalBoardFile).buildReversi();
        reversiGame.makeMove(moveCoordinate);
        assertAll(() -> assertEquals(finalBoard, othelloGame.getBoard().toString()),
                () -> assertEquals(finalBoard, reversiGame.getBoard().toString()));
    }

    @ParameterizedTest
    @CsvSource({"1A","10H","3C"})
    void cannotBeMadeIfNotValid(String blackDiskCoordinate) throws IOException {
        Game othelloGame = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withBoardSize(8).buildOthello();
        URL boardFile = Thread.currentThread().getContextClassLoader().getResource("initialBoards/othello8x8Board");
        Game reversiGame = Game.GameBuilder.CLIGameBuilder("Bob").withOpponent("Alice").withCustomBoard(boardFile).buildReversi();
        assertAll(() -> assertThrows(IllegalArgumentException.class, () -> othelloGame.makeMove(new Coordinate(blackDiskCoordinate))),
                () -> assertThrows(IllegalArgumentException.class, () -> reversiGame.makeMove(new Coordinate(blackDiskCoordinate))));
    }
}
