package sdm.reversi.launcher;

import sdm.reversi.game.Game;
import sdm.reversi.manager.GUIManager;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args != null && args.length > 0 && args[0].equalsIgnoreCase("CLI")) {
            System.out.println("Welcome to the game!");
            CLILauncher.launch();
        } else {
            /*Runnable r = new Runnable() {
                @Override
                public void run() {
                    GUILauncher.launch();
                }
            };
            SwingUtilities.invokeLater(r);*/
            GUILauncher.launch();
        }
    }
}

