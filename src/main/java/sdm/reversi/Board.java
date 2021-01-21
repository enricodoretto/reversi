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
        if (isCellEmpty(coordinate)) {
            board[coordinate.getRow()][coordinate.getColumn()] = disk;
            return true;
        }
        return false;
    }
    public boolean putDisk(Disk.Color diskColor, Coordinate coordinate){
        Disk disk = new Disk(diskColor);
        return putDisk(disk, coordinate);
    }

    public Disk.Color getDiskColorFromCoordinate(Coordinate coordinate){
        Disk disk = board[coordinate.getRow()][coordinate.getColumn()];
        if(disk==null){
            return null;
        }
        return disk.getSideUp();
    }


    public boolean isCellEmpty(Coordinate coordinate) {
        return isCellEmpty(coordinate.getRow(),coordinate.getColumn());
    }

    public boolean isCellEmpty(int row, int column) {
        return board[row][column] == null;
    }

    private static boolean isValidIndex(int index) {
        return index >= 0 && index <= BOARD_SIZE - 1;
    }

    public boolean isValidCell(Coordinate coordinate){ return isValidIndex(coordinate.getRow()) && isValidIndex(coordinate.getColumn());}

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
