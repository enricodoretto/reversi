package sdm.reversi;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import sdm.reversi.board.Coordinate;
import sdm.reversi.manager.ComputerGameManager;
import sdm.reversi.manager.GameManager;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestComputerGameManager {
    GameManager gameManager = new ComputerGameManager();

    @ParameterizedTest
    @CsvSource({"2B-1A-4D, 1A", "4C-3G-8D, 3G"})
    void givesBackTopLeftCoordinateAsNextMove(String suggestedMovesString, Coordinate move){
        Set<Coordinate> suggestedMoves = Arrays.stream(suggestedMovesString.split("-"))
                .map(Coordinate::new).collect(Collectors.toSet());
        gameManager.suggestMoves(suggestedMoves);
        assertEquals(move, gameManager.getMoveFromPlayer());
    }
}
