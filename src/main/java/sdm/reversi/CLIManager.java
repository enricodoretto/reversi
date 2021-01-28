package sdm.reversi;

import java.util.Scanner;

public class CLIManager implements IOManager{

    Scanner scanner = new Scanner(System.in);

    @Override
    public void updateBoard(Board board) {
        System.out.println(board);
    }

    @Override
    public String getMoveFromPlayer() {
        return scanner.nextLine();
    }

    @Override
    public void startTurn(String message) {
        System.out.println(message);
    }

    @Override
    public void illegalMove(String message) {
        System.out.println(message);
    }
}
