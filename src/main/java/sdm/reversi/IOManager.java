package sdm.reversi;

import sdm.reversi.game.Game;

public interface IOManager {

    void updateGame(Game game);
    Coordinate getMoveFromPlayer();
    void startTurn(String message);
    void illegalMove(String message);
    void initialize(Game game);

}
