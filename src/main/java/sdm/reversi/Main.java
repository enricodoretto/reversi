package sdm.reversi;

import sdm.reversi.game.Game;
import sdm.reversi.game.OthelloGame;
import sdm.reversi.game.ReversiGame;
import sdm.reversi.manager.GUIManager;

public class Main {
    public static void main(String[] args) {
        Game reversiGame = new ReversiGame("Bob", "Alice", new GUIManager(), 20);
        reversiGame.play();
        System.out.println("Hello World");
    }
}
