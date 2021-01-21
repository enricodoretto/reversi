package sdm.reversi;

import java.util.Arrays;

public class Board {

    private final Disk[][] board;
    private static final int BOARD_SIZE = 8;

    public Board() {
        board = new Disk[BOARD_SIZE][BOARD_SIZE];
    }

    public boolean putDisk(Disk disk, String coordinate) {
        int row = coordinate.charAt(0) - '0';
        char column = coordinate.charAt(1);
        return putDisk(disk, row, column);
    }

    public boolean putDisk(Disk disk, int row, char column) {
        int boardRow = row - 1;
        int boardColumn = column - 'A';
        if (!isValidIndex(boardRow) || !isValidIndex(boardColumn)) throw new IllegalArgumentException();
        if (isCellEmpty(boardRow, boardColumn)) {
            board[boardRow][boardColumn] = disk;
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
