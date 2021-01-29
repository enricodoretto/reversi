package sdm.reversi.game;

import sdm.reversi.Coordinate;
import sdm.reversi.manager.IOManager;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class ReversiGame extends Game {

    Coordinate[] initialMoves = new Coordinate[]{
            new Coordinate(board.getSize() / 2 - 1, board.getSize() / 2 - 1),
            new Coordinate(board.getSize() / 2 , board.getSize() / 2 - 1),
            new Coordinate(board.getSize() / 2 - 1, board.getSize() / 2 ),
            new Coordinate(board.getSize() / 2 , board.getSize() / 2)};

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

    public ReversiGame(String player1Name, String player2Name, IOManager ioManager) {
        super(player1Name,player2Name, ioManager);
        calculatePlayerPossibleMoves();
        updatePlayersScore();
    }

    public ReversiGame(String player1Name, String player2Name, IOManager ioManager, int boardSize) {
        super(player1Name,player2Name, ioManager, boardSize);
        calculatePlayerPossibleMoves();
        updatePlayersScore();
    }

    @Override
    protected void calculatePlayerPossibleMoves(){
        if(numberOfMoves>=4){
            super.calculatePlayerPossibleMoves();
            return;
        }
        allowedMovesForCurrentPlayer = Arrays.stream(initialMoves)
                .filter(c -> board.isCellAvailable(c))
                .collect(Collectors.toMap(
                        coordinate -> coordinate,
                        coordinate -> new HashSet<>()
                ));
    }

    @Override
    public boolean isOver() {
        return super.isOver() || player1.isInStall() || player2.isInStall();
    }

}
