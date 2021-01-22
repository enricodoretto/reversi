package sdm.reversi;

import java.util.Collection;
import java.util.HashSet;

public class Board {

    private final Disk[][] board;
    private int boardSize;
    private final static int DEFAULT_BOARD_SIZE = 8;

    public Board() {
        this(DEFAULT_BOARD_SIZE);
    }

    public Board(int boardSize) {
        if(boardSize % 2 != 0 || boardSize < 4 || boardSize > 26) throw new IllegalArgumentException();
        this.boardSize = boardSize;
        board = new Disk[boardSize][boardSize];
    }

    public Collection<Coordinate> getBoardCoordinates() {
        Collection<Coordinate> boardCoordinates = new HashSet<>();
        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                boardCoordinates.add(new Coordinate(row, column));
            }
        }
        return boardCoordinates;
    }

    public boolean putDisk(Disk disk, Coordinate coordinate) {
        if (!isValidIndex(coordinate.getRow()) || !isValidIndex(coordinate.getColumn()))
            throw new IllegalArgumentException();
        if (isCellEmpty(coordinate)) {
            board[coordinate.getRow()][coordinate.getColumn()] = disk;
            return true;
        }
        return false;
    }

    public boolean putDisk(Disk.Color diskColor, Coordinate coordinate) {
        Disk disk = new Disk(diskColor);
        return putDisk(disk, coordinate);
    }

    public void flipDisk(Coordinate coordinate) {
        if (!isValidIndex(coordinate.getRow()) || !isValidIndex(coordinate.getColumn()))
            throw new IllegalArgumentException();
        board[coordinate.getRow()][coordinate.getColumn()].flip();
    }

    public Disk.Color getDiskColorFromCoordinate(Coordinate coordinate) {
        Disk disk = board[coordinate.getRow()][coordinate.getColumn()];
        if (disk == null) {
            return null;
        }
        return disk.getSideUp();
    }


    public boolean isCellEmpty(Coordinate coordinate) {
        return isCellEmpty(coordinate.getRow(), coordinate.getColumn());
    }

    public boolean isCellEmpty(int row, int column) {
        if (isValidIndex(row) && isValidIndex(column)) {
            return board[row][column] == null;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private boolean isValidIndex(int index) {
        return index >= 0 && index <= boardSize - 1;
    }

    public boolean isValidCell(Coordinate coordinate) {
        return !isValidIndex(coordinate.getRow()) || !isValidIndex(coordinate.getColumn());
    }

    @Override
    public String toString() {
        //to improve
        String representation = "";
        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                representation += isCellEmpty(row, column) ? "-" : board[row][column].toString();
            }
            if (row != boardSize - 1) {
                representation += "\n";
            }
        }
        return representation;
    }
}
