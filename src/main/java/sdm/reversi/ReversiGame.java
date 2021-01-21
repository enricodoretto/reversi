package sdm.reversi;

public class ReversiGame extends Game{

    public ReversiGame(String player1Name, String player2Name) throws IllegalArgumentException {
        super(player1Name, player2Name);
        initializeBoard();
    }

    @Override
    protected void initializeBoard() {
        board = new Board();
        Disk blackDisk = new Disk(Disk.Color.BLACK);
        Disk whiteDisk = new Disk(Disk.Color.WHITE);
        board.putDisk(whiteDisk, 4, 'D');
        board.putDisk(whiteDisk, 5, 'E');
        board.putDisk(blackDisk, 4, 'E');
        board.putDisk(blackDisk, 5, 'D');
    }
}
