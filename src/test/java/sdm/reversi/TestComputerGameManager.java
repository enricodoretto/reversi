package sdm.reversi;

import org.junit.jupiter.api.Test;
import sdm.reversi.game.Game;
import sdm.reversi.game.ReversiGame;
import sdm.reversi.manager.CLIManager;
import sdm.reversi.manager.ComputerGameManager;
import sdm.reversi.manager.GameManager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestComputerGameManager {

    @Test
    void givesBackTopLeftCoordinateAsNextMove(){
        Set<Coordinate> suggestedMoves = Set.of(new Coordinate("2B"),
                new Coordinate("1A"), new Coordinate("4D"));
        GameManager gameManager = new ComputerGameManager();
        gameManager.suggestMoves(suggestedMoves);
        assertEquals(new Coordinate("1A"), gameManager.getMoveFromPlayer());
    }
}
