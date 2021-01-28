package sdm.reversi;

import sdm.reversi.game.Game;
import sdm.reversi.game.OthelloGame;
import sdm.reversi.manager.GUIManager;

public class Main {
    public static void main(String[] args) {
        Game othelloGame = new OthelloGame("Bob", "Alice", new GUIManager());
        othelloGame.play();
        System.out.println("Hello World");
    }
}
