package sdm.reversi.game;

import sdm.reversi.Coordinate;
import sdm.reversi.Disk;
import sdm.reversi.manager.IOManager;

import java.io.IOException;
import java.net.URL;

public class OthelloGame extends Game {

    public OthelloGame(String player1Name, String player2Name) throws IllegalArgumentException {
        super(player1Name, player2Name);
        initializeBoard();
        calculatePlayerPossibleMoves();
        updatePlayersScore();
    }

    public OthelloGame(String player1Name, String player2Name, URL boardFileURL) throws IOException {
        super(player1Name, player2Name, boardFileURL);
        calculatePlayerPossibleMoves();
        updatePlayersScore();
    }

    public OthelloGame(String player1Name, String player2Name, IOManager ioManager) {
        super(player1Name,player2Name, ioManager);
        initializeBoard();
        calculatePlayerPossibleMoves();
        updatePlayersScore();
    }

    public OthelloGame(String player1Name, String player2Name, IOManager ioManager, int boardSize) {
        super(player1Name,player2Name, ioManager, boardSize);
        calculatePlayerPossibleMoves();
        updatePlayersScore();
    }

    public OthelloGame(String player1Name, String player2Name, int boardSize) {
        super(player1Name, player2Name, boardSize);
        initializeBoard();
        calculatePlayerPossibleMoves();
        updatePlayersScore();
    }

    protected void initializeBoard() {
        board.putDisk(Disk.Color.WHITE, new Coordinate(board.getSize()/2-1, board.getSize()/2-1));
        board.putDisk(Disk.Color.WHITE, new Coordinate(board.getSize()/2,board.getSize()/2));
        board.putDisk(Disk.Color.BLACK, new Coordinate(board.getSize()/2-1,board.getSize()/2));
        board.putDisk(Disk.Color.BLACK, new Coordinate(board.getSize()/2, board.getSize()/2-1));
    }

    @Override
    public boolean isOver(){
        return super.isOver() || (player1.isInStall() && player2.isInStall());
    }

}
