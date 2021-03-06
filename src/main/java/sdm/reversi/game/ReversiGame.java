package sdm.reversi.game;

import sdm.reversi.board.Coordinate;
import java.util.*;
import java.util.stream.Collectors;

public class ReversiGame extends Game {

    Coordinate[] initialMoves = new Coordinate[]{
            new Coordinate(board.getSize() / 2 - 1, board.getSize() / 2 - 1),
            new Coordinate(board.getSize() / 2 , board.getSize() / 2 - 1),
            new Coordinate(board.getSize() / 2 - 1, board.getSize() / 2 ),
            new Coordinate(board.getSize() / 2 , board.getSize() / 2)};

    public ReversiGame(GameBuilder gameBuilder){
        super(gameBuilder);
        calculatePlayerPossibleMoves();
        updatePlayersScore();
    }

    @Override
    protected void calculatePlayerPossibleMoves(){
        if(numberOfMoves>=4){
            super.calculatePlayerPossibleMoves();
            return;
        }
        allowedMovesForCurrentPlayer = Arrays.stream(initialMoves).parallel()
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
