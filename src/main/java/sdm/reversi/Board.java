package sdm.reversi;

public class Board {

    private final Disk[][] board;
    private static final int BOARD_SIZE = 8;

    public Board(){
        board = new Disk[BOARD_SIZE][BOARD_SIZE];
    }

    public boolean putDisk(Disk disk, int row, char column){
        int boardRow = row-1;
        int boardColumn = column -'A';
        if(!isValidIndex(boardRow) || !isValidIndex(boardColumn)) throw new IllegalArgumentException();
        if(isCellEmpty(boardRow, boardColumn)) {
            board[boardRow][boardColumn] = disk;
            return true;
        }
        return false;
    }

    private boolean isCellEmpty(int boardRow, int boardColumn) {
        return board[boardRow][boardColumn] == null;

    }

    private static boolean isValidIndex(int index) {
        return index >= 0 && index <= BOARD_SIZE-1;
    }

}
