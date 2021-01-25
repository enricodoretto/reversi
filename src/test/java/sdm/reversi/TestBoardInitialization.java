package sdm.reversi;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestBoardInitialization {

    @ParameterizedTest
    @CsvSource({"4", "6", "12"})
    public void canBuildBoardOfDifferentSize(int boardSize){
        assertDoesNotThrow(() -> {Board board = new Board(boardSize);});
    }

    @ParameterizedTest
    @CsvSource({"5,7,15"})
    public void failsWithOddSize(int boardSize){
        assertThrows(IllegalArgumentException.class, () -> new Board(boardSize));
    }

    @ParameterizedTest
    @CsvSource({"-4", "0", "2"})
    public void failsWithSizeSmallerThan4(int boardSize){
        assertThrows(IllegalArgumentException.class, () -> new Board(boardSize));
    }

    @ParameterizedTest
    @CsvSource({"28", "30", "64"})
    public void failsWithSizeGreaterThan26(int boardSize){
        assertThrows(IllegalArgumentException.class, () -> new Board(boardSize));
    }
}
