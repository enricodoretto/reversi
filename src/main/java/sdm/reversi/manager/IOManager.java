package sdm.reversi.manager;

import sdm.reversi.Coordinate;
import sdm.reversi.Player;
import sdm.reversi.game.Game;

import java.util.Collection;

public interface IOManager {

    void updateGame(Game game);
    Coordinate getMoveFromPlayer();
    void startTurn(Player currentPlayer);
    void skipTurn();
    void suggestMoves(Collection<Coordinate> moves);
    void illegalMove(String message);
    void initialize(Game game);

}
