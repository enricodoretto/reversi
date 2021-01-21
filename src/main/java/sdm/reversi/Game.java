package sdm.reversi;

public abstract class Game {
    private Player player1;
    private Player player2;
    protected Board board;
    protected Player currentPlayer;

    public Game(String player1Name, String player2Name) throws IllegalArgumentException {
        if (player1Name.equals(player2Name)) throw new IllegalArgumentException();
        this.player1 = new Player(player1Name, Disk.Color.BLACK);
        this.player2 = new Player(player2Name, Disk.Color.WHITE);
        this.currentPlayer = player1;
    }

    public boolean makeMove(Coordinate coordinate) {
        if (isValidMove(coordinate)) {
            board.putDisk(new Disk(currentPlayer.getColor()), coordinate);
            currentPlayer = (currentPlayer == player1) ? player2 : player1;
            return true;
        } else return false;
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

    public boolean isValidMove(String stringCoordinate) {
        Coordinate coordinate = Coordinate.parseCoordinate(stringCoordinate);
        return isValidMove(coordinate);
    }

    public abstract boolean isValidMove(Coordinate coordinate);
}
