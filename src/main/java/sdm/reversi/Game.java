package sdm.reversi;

public class Game {
    private Player player1;
    private Player player2;
    private Board board;

    public Game(String player1Name, String player2Name) {
        this.player1 = new Player(player1Name, Disk.Color.BLACK);
        this.player2 = new Player(player2Name, Disk.Color.WHITE);
        this.board = new Board();
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }


}
