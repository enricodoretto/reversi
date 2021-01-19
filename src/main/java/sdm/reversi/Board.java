package sdm.reversi;

public class Board {

    private final Disk[][] board;
    private static final int BOARD_SIZE = 8;

    public Board(){
        board = new Disk[BOARD_SIZE][BOARD_SIZE];
    }

    public boolean putDisk(Disk disk, int row, char column){
        if(board[row-1][column-'A'] == null) {
            board[row-1][column - 'A'] = disk;
            return true;
        }
        return false;
    }

}
