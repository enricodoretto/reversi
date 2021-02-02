package sdm.reversi.launcher;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args != null && args.length > 0 && args[0].equalsIgnoreCase("GUI")) {
            GUILauncher.launch();
        } else {
            System.out.println("Welcome to the game!");
            CLILauncher.launch();
        }
    }
}

