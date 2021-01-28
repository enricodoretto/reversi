package sdm.reversi.manager;

import sdm.reversi.Coordinate;
import sdm.reversi.game.Game;

public interface IOManager {

    void updateGame(Game game);
    Coordinate getMoveFromPlayer();
    void startTurn(String message);
    void illegalMove(String message);
    void initialize(Game game);

}
