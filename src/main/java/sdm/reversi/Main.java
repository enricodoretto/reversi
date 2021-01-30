package sdm.reversi;

import sdm.reversi.game.Game;
import sdm.reversi.game.OthelloGame;
import sdm.reversi.game.ReversiGame;
import sdm.reversi.manager.CLIManager;
import sdm.reversi.manager.GUIManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the game!");
        System.out.println("What game do you want to play? \n" +
                "1 - Reversi\n" +
                "2 - Othello");

        int typeOfGame = Integer.parseInt(scanner.nextLine());

        System.out.println("Player1 name: ");
        String player1Name = scanner.nextLine();
        System.out.println("Player2 name: ");
        String player2Name = scanner.nextLine();

        System.out.println("Board size: ");
        int boardSize = scanner.nextInt();

        Game game;
        if (typeOfGame == 1) {
            game = new ReversiGame(player1Name, player2Name, new CLIManager(), boardSize);
        } else {
            game = new OthelloGame(player1Name, player2Name, new CLIManager(), boardSize);
        }
        game.play();

    }
}
