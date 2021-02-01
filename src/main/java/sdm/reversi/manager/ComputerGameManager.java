package sdm.reversi.manager;

import sdm.reversi.Coordinate;
import sdm.reversi.player.Player;
import sdm.reversi.game.Game;

import java.util.Collection;

public class ComputerGameManager implements GameManager{
    private Coordinate move;

    @Override
    public void suggestMoves(Collection<Coordinate> moves) {
        move = moves.stream().sorted().findFirst().get();
    }

    @Override
    public Coordinate getMoveFromPlayer() {
        return move;
    }

    @Override
    public void illegalMove(String message) {

    }

    @Override
    public void initialize(Game game) {

    }

    @Override
    public void startTurn(Player currentPlayer) {

    }

    @Override
    public void skipTurn() {

    }

    @Override
    public void updateGame(Game game) {

    }

    @Override
    public void showWinner(Player player) {

    }
}
