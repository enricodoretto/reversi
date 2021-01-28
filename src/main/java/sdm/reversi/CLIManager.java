package sdm.reversi;

import sdm.reversi.game.Game;

import java.util.Scanner;

public class CLIManager implements IOManager{

    private static final String QUIT_COMMAND = "q";
    private  final Scanner scanner = new Scanner(System.in);

    @Override
    public void updateGame(Game game) {
        System.out.println(game.getBoardRepresentation());
    }

    @Override
    public Coordinate getMoveFromPlayer() {
        String coordinateOfDesiredMove = scanner.nextLine();
        if(coordinateOfDesiredMove.equalsIgnoreCase(QUIT_COMMAND)){
            return null;
        }
        return new Coordinate(coordinateOfDesiredMove);
    }

    @Override
    public void initialize(Game game){
        System.out.println(game.getBoardRepresentation());
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
