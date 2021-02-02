package sdm.reversi.launcher;

import sdm.reversi.Client;
import sdm.reversi.game.Game;
import sdm.reversi.manager.CLIManager;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

public class CLILauncher {
    public static void launch() throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Player1 name: ");
        String player1Name = scanner.nextLine();

        Game.GameBuilder gameBuilder = Game.GameBuilder.CLIGameBuilder(player1Name);
        System.out.println("What game do you want to play? \n" +
                "1 - 1v1\n" +
                "2 - 1vCPU\n" +
                "3 - Online Server\n" +
                "4 - Online Client");
        int gameMode = Integer.parseInt(scanner.nextLine());

        switch(gameMode){
            case 1:
                System.out.println("Player2 name: ");
                String player2Name = scanner.nextLine();
                gameBuilder.withOpponent(player2Name);
                break;
            case 2:
                gameBuilder.withCPUOpponent();
                break;
            case 3:
                System.out.println("Wait until client connection...");
                gameBuilder.withRemoteOpponent();
                break;
            case 4:
                System.out.println("IP Address Host: ");
                String serverIP = scanner.nextLine();
                Client.connectAndPlay(player1Name, new CLIManager(), InetAddress.getByName(serverIP));
                return;
            default:
                System.out.println("Illegal Argument");
                new CLILauncher();
                break;
        }
        
        System.out.println("Board size: ");
        int boardSize = Integer.parseInt(scanner.nextLine());
        
        gameBuilder.withBoardSize(boardSize);

        System.out.println("What game do you want to play? \n" +
                "1 - Reversi\n" +
                "2 - Othello");

        int typeOfGame = Integer.parseInt(scanner.nextLine());

        Game game;
        if (typeOfGame == 1) {
            game = gameBuilder.buildReversi();
        } else {
            game = gameBuilder.buildOthello();
        }

        System.out.println("Press q when you want to quit the game");

        game.play();
    }


}
