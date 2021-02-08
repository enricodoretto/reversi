package sdm.reversi.game;

import sdm.reversi.board.Coordinate;
import sdm.reversi.board.Disk;

public class OthelloGame extends Game {

    public OthelloGame(GameBuilder gameBuilder){
        super(gameBuilder);
        if(board.getNumberOfDisks() == 0) initializeBoard();
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
