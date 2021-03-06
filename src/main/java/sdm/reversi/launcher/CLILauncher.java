package sdm.reversi.launcher;

import sdm.reversi.client.Client;
import sdm.reversi.game.Game;
import sdm.reversi.manager.CLIManager;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class CLILauncher {
    public static void launch() throws IOException {
        Scanner scanner = new Scanner(System.in);

        Game.GameBuilder gameBuilder;

        System.out.println("Player1 name:");
        String player1Name;
        while (true) {
            try {
                player1Name = scanner.nextLine();
                gameBuilder = Game.GameBuilder.CLIGameBuilder(player1Name, scanner);
                break;
            } catch (IllegalArgumentException e) {
                System.out.printf("Illegal input, %s, please retry%s", e.getMessage().toLowerCase(), System.lineSeparator());
            }
        }

        System.out.println("What game do you want to play?" + System.lineSeparator() +
                "1 - 1v1" + System.lineSeparator() +
                "2 - 1vCPU" + System.lineSeparator() +
                "3 - Online Server" + System.lineSeparator() +
                "4 - Online Client");
        int gameMode;
        while (true) {
            try {
                gameMode = Integer.parseInt(scanner.nextLine());
                if (gameMode < 1 || gameMode > 4) {
                    throw new IllegalArgumentException();
                }
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Illegal input, please type a number between 1 and 4");
            }
        }
        switch (gameMode) {
            case 1:
                System.out.println("Player2 name:");
                String player2Name;
                while (true) {
                    try {
                        player2Name = scanner.nextLine();
                        gameBuilder.withOpponent(player2Name);
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.printf("Illegal input, %s, please retry%s", e.getMessage().toLowerCase(), System.lineSeparator());
                    }
                }
                break;
            case 2:
                gameBuilder.withCPUOpponent();
                break;
            case 3:

                break;
            case 4:
                System.out.println("IP Address Host:");
                String serverIP;
                while (true) {
                    try {
                        serverIP = scanner.nextLine();
                        System.out.println("Wait until host connection...");
                        Client.connectAndPlay(player1Name, new CLIManager(scanner), InetAddress.getByName(serverIP));
                        return;
                    } catch (UnknownHostException e) {
                        System.out.println("Illegal IP address, please retry");
                    }
                }
            default:
                System.out.println("Unexpected error occurred, the launcher will restart...");
                launch();
                break;
        }

        System.out.println("Board size:");
        int boardSize;
        while (true) {
            try {
                boardSize = Integer.parseInt(scanner.nextLine());
                gameBuilder.withBoardSize(boardSize);
                break;
            } catch (IllegalArgumentException e) {
                System.out.printf("Illegal input, %s, please retry%s", e.getMessage().toLowerCase(), System.lineSeparator());
            }
        }

        System.out.println("What game do you want to play?" + System.lineSeparator() +
                "1 - Reversi" + System.lineSeparator() +
                "2 - Othello");
        int typeOfGame;
        while (true) {
            try {
                typeOfGame = Integer.parseInt(scanner.nextLine());
                if (typeOfGame != 1 && typeOfGame != 2) {
                    throw new IllegalArgumentException();
                }
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Illegal input, please type a number between 1 and 2");
            }
        }

        Game game;
        if (gameMode == 3) {
            System.out.println("Wait until client connection...");

            if (typeOfGame == 1) {
                game = gameBuilder.withRemoteOpponent().buildReversi();
            } else {
                game = gameBuilder.withRemoteOpponent().buildOthello();
            }
        } else {
            if (typeOfGame == 1) {
                game = gameBuilder.buildReversi();
            } else {
                game = gameBuilder.buildOthello();
            }
        }
        System.out.println("Press q when you want to quit the game");

        game.play();
    }


}
