package sdm.reversi;

import sdm.reversi.game.Game;

public interface IOManager {

    void updateBoard(Board board);
    Coordinate getMoveFromPlayer();
    void startTurn(String message);
    void illegalMove(String message);
    void initialize(Game game);

}
