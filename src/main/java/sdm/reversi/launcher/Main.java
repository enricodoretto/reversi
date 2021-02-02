package sdm.reversi.launcher;

import sdm.reversi.game.Game;
import sdm.reversi.game.OthelloGame;
import sdm.reversi.game.ReversiGame;
import sdm.reversi.manager.CLIManager;
import sdm.reversi.manager.GUIManager;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to the game!");
        CLILauncher.launch();
        //Game game = Game.GameBuilder.CLIGameBuilder("Bob").withRemoteOpponent().buildReversi();
        //game.play();
    }
}
