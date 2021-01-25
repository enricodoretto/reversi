package sdm.reversi;

public class ReversiGame extends Game {

    public ReversiGame(String player1Name, String player2Name) throws IllegalArgumentException {
        super(player1Name, player2Name);
        initializeBoard();
        allowedMovesForCurrentPlayer=getPlayerPossibleMoves();
    }

    public ReversiGame(String player1Name, String player2Name, Board customBoard) {
        super(player1Name, player2Name, customBoard);
        allowedMovesForCurrentPlayer=getPlayerPossibleMoves();
    }

    @Override
    protected void initializeBoard() {
        board.putDisk(Disk.Color.WHITE, new Coordinate(board.getSize()/2-1, board.getSize()/2-1));
        board.putDisk(Disk.Color.WHITE, new Coordinate(board.getSize()/2,board.getSize()/2));
        board.putDisk(Disk.Color.BLACK, new Coordinate(board.getSize()/2-1,board.getSize()/2));
        board.putDisk(Disk.Color.BLACK, new Coordinate(board.getSize()/2, board.getSize()/2-1));
    }

}
