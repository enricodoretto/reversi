package sdm.reversi.manager;

import sdm.reversi.board.Coordinate;

import java.util.Collection;

public interface ActionManager {

    void suggestMoves(Collection<Coordinate> moves);
    Coordinate getMoveFromPlayer();
    void illegalMove();
}
