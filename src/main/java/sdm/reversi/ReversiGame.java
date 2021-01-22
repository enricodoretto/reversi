package sdm.reversi;

public class ReversiGame extends Game {

    public ReversiGame(String player1Name, String player2Name) throws IllegalArgumentException {
        super(player1Name, player2Name);
        initializeBoard();
        allowedMovesForCurrentPlayer=getPlayerPossibleMoves();
    }

    @Override
    protected void initializeBoard() {
        board = new Board();
        board.putDisk(new Disk(Disk.Color.WHITE), Coordinate.parseCoordinate("4D"));
        board.putDisk(new Disk(Disk.Color.WHITE), Coordinate.parseCoordinate("5E"));
        board.putDisk(new Disk(Disk.Color.BLACK), Coordinate.parseCoordinate("4E"));
        board.putDisk(new Disk(Disk.Color.BLACK), Coordinate.parseCoordinate("5D"));
    }

}
