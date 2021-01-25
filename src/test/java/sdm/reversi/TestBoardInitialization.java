package sdm.reversi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class TestBoardInitialization {

    @ParameterizedTest
    @CsvSource({"4", "8", "26"})
    public void succeedsWithEvenSizeBetween4And26(int boardSize){
        assertDoesNotThrow(() -> new Board(boardSize));
    }

    @Test
    public void succeedsWithNoSize(){
        assertDoesNotThrow(() -> new Board());
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
