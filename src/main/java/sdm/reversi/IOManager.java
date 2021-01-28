package sdm.reversi;

public interface IOManager {

    void updateBoard(Board board);
    Coordinate getMoveFromPlayer();
    void startTurn(String message);
    void illegalMove(String message);

}
