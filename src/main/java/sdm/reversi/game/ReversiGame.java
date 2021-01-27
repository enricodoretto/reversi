package sdm.reversi.game;

import java.io.IOException;
import java.net.URL;

public class ReversiGame extends Game {

    public ReversiGame(String player1Name, String player2Name) throws IllegalArgumentException {
        super(player1Name, player2Name);
        calculatePlayerPossibleMoves();
        updatePlayersScore();
    }

    public ReversiGame(String player1Name, String player2Name, URL boardFileURL) throws IOException {
        super(player1Name, player2Name, boardFileURL);
        calculatePlayerPossibleMoves();
        updatePlayersScore();
    }

    public ReversiGame(String player1Name, String player2Name, int boardSize) {
        super(player1Name, player2Name, boardSize);
        calculatePlayerPossibleMoves();
        updatePlayersScore();
    }

    @Override
    public boolean isOver() {
        return board.isFull() || player1.isInStall() || player2.isInStall();
    }

}
