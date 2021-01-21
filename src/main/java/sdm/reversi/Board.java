package sdm.reversi;

import java.util.Arrays;

public class Board {

    private final Disk[][] board;
    private static final int BOARD_SIZE = 8;

    public Board() {
        board = new Disk[BOARD_SIZE][BOARD_SIZE];
    }

    public boolean putDisk(Disk disk, Coordinate coordinate){
        if (!isValidIndex(coordinate.getRow()) || !isValidIndex(coordinate.getColumn())) throw new IllegalArgumentException();
        if (isCellEmpty(coordinate.getRow(), coordinate.getColumn())) {
            board[coordinate.getRow()][coordinate.getColumn()] = disk;
            return true;
        }
        return false;
    }


    public boolean isCellEmpty(int boardRow, int boardColumn) {
        return board[boardRow][boardColumn] == null;
    }

    private static boolean isValidIndex(int index) {
        return index >= 0 && index <= BOARD_SIZE - 1;
    }


    @Override
    public String toString() {
        //to improve
        String representation = "";
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {
                representation += isCellEmpty(row, column) ? "-" : board[row][column].toString();
            }
            if (row != BOARD_SIZE - 1) {
                representation += "\n";
            }
        }
        return representation;
    }
}
