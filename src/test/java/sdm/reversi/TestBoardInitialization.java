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
    @CsvSource({"5", "2", "30"})
    public void cantBuildBoardOfIllegalSize(int boardSize){
        assertThrows(IllegalArgumentException.class, () -> {Board board = new Board(boardSize);});
    }
}
