package sdm.reversi.launcher;

import sdm.reversi.game.Game;
import sdm.reversi.manager.GUIManager;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        CLILauncher.launch();
        /*if (args != null && args.length > 0 && args[0].equalsIgnoreCase("CLI")) {
            System.out.println("Welcome to the game!");
            CLILauncher.launch();
        } else {
            GUILauncher.launch();
        }*/
    }
}

