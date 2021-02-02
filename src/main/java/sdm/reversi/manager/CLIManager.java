package sdm.reversi.manager;

import sdm.reversi.Coordinate;
import sdm.reversi.player.Player;
import sdm.reversi.game.Game;

import java.util.Collection;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CLIManager implements GameManager {

    private static final String QUIT_COMMAND = "q";
    private  final Scanner scanner = new Scanner(System.in);

    @Override
    public void updateGame(Game game) {
        System.out.println(game.getBoard());
    }

    @Override
    public void startTurn(Player currentPlayer) {
        System.out.printf("%s's turn:%s", currentPlayer.getName(), System.lineSeparator());
    }

    @Override
    public void skipTurn() {
        System.out.println("Sorry you can make no moves!");
    }

    @Override
    public void showWinner(Player player) {
        if (player == null){
            System.out.println("Tie!");
            return;
        }
        System.out.printf("The winner is %s%s", player.getName(), System.lineSeparator());
    }

    @Override
    public void suggestMoves(Collection<Coordinate> moves) {
        System.out.println(moves.stream().parallel().map(Coordinate::toString).sorted().collect(Collectors.joining(" ")));
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
        System.out.println(game.getBoard());
    }

    @Override
    public void illegalMove() {
        System.out.println("Invalid move, please choose another one");
    }

    @Override
    public void notifyError(String message){
        System.out.println(message);
    }
}
