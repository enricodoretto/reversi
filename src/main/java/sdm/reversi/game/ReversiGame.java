package sdm.reversi.game;

import sdm.reversi.Coordinate;
import sdm.reversi.Disk;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Override
    protected void calculatePlayerPossibleMoves(){
        if(numberOfMoves>=4){
            super.calculatePlayerPossibleMoves();
            return;
        }
        /*Map<Coordinate, Set<Coordinate>> validCoordinates = Arrays.stream(initialMoves)
                .filter(c -> board.isCellAvailable(c))
                .collect(Collectors.toMap(Function.identity(), new HashSet<>()));*/
        allowedMovesForCurrentPlayer = new HashMap<>();
        for(Coordinate coordinate : initialMoves){
            if(board.isCellAvailable(coordinate)){
                allowedMovesForCurrentPlayer.put(coordinate, new HashSet<>());
            }
        }
    }

    @Override
    public boolean isOver() {
        return board.isFull() || player1.isInStall() || player2.isInStall();
    }

}
