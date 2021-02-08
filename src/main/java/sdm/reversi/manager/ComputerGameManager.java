package sdm.reversi.manager;

import sdm.reversi.board.Coordinate;

import java.util.Collection;

public class ComputerGameManager implements GameManager{
    private Coordinate move;

    @Override
    public void suggestMoves(Collection<Coordinate> moves) {
        move = moves.stream().parallel().sorted().findFirst().get();
    }

    @Override
    public Coordinate getMoveFromPlayer(){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return move;
    }

    @Override
    public void illegalMove() {

    }

}
