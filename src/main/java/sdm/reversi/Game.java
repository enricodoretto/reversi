package sdm.reversi;

public class Game {
    private Player player1;
    private Player player2;
    private Board board;

    public Game(String player1Name, String player2Name) throws IllegalArgumentException {
        if (player1Name.equals(player2Name)) throw new IllegalArgumentException();
        this.player1 = new Player(player1Name, Disk.Color.BLACK);
        this.player2 = new Player(player2Name, Disk.Color.WHITE);
        initializeBoard();
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public String getBoardRepresentation() {
        return board.toString();
    }

    private void initializeBoard() {
        this.board = new Board();
        Disk blackDisk = new Disk(Disk.Color.BLACK);
        Disk whiteDisk = new Disk(Disk.Color.WHITE);
        board.putDisk(whiteDisk, 4, 'D');
        board.putDisk(whiteDisk, 5, 'E');
        board.putDisk(blackDisk, 4, 'E');
        board.putDisk(blackDisk, 5, 'D');
    }

}
