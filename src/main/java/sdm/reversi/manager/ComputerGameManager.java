package sdm.reversi.manager;

import sdm.reversi.Coordinate;

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

}
