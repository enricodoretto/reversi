package sdm.reversi;

public abstract class Game {
    private Player player1;
    private Player player2;
    protected Board board;

    public Game(String player1Name, String player2Name) throws IllegalArgumentException {
        if (player1Name.equals(player2Name)) throw new IllegalArgumentException();
        this.player1 = new Player(player1Name, Disk.Color.BLACK);
        this.player2 = new Player(player2Name, Disk.Color.WHITE);
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

    protected abstract void initializeBoard();
}
