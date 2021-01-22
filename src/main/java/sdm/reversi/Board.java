package sdm.reversi;

import java.util.*;
import java.util.stream.Collectors;

public class Board implements Iterable<Coordinate> {

    private final Disk[][] board;
    private final int boardSize;
    private final static int DEFAULT_BOARD_SIZE = 8;

    public Board() {
        this(DEFAULT_BOARD_SIZE);
    }

    public Board(int boardSize) {
        if (boardSize % 2 != 0 || boardSize < 4 || boardSize > 26) throw new IllegalArgumentException();
        this.boardSize = boardSize;
        board = new Disk[boardSize][boardSize];
    }

    public Iterator<Coordinate> iterator() {
        return new Iterator<>() {

            private int currentRowIndex = 0;
            private int currentColumnIndex = 0;

            @Override
            public boolean hasNext() {
                return currentRowIndex < boardSize && currentColumnIndex < boardSize;
            }

            @Override
            public Coordinate next() {
                Coordinate nextCoordinate = new Coordinate(currentRowIndex, currentColumnIndex);
                if (currentColumnIndex == boardSize - 1) {
                    currentRowIndex++;
                    currentColumnIndex = 0;
                } else {
                    currentColumnIndex++;
                }
                return nextCoordinate;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public boolean putDisk(Disk.Color diskColor, Coordinate coordinate){
        if (!isValidIndex(coordinate.getRow()) || !isValidIndex(coordinate.getColumn()))
            throw new IllegalArgumentException();
        if (isCellEmpty(coordinate)) {
            board[coordinate.getRow()][coordinate.getColumn()] = new Disk(diskColor);
            return true;
        }
        return false;
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
        StringBuilder representation = new StringBuilder();
        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                representation.append(isCellEmpty(row, column) ? "-" : board[row][column].toString());
            }
            if (row != boardSize - 1) {
                representation.append("\n");
            }
        }
        return representation.toString();
    }

    public Disk.Color getColorWithMoreDisks() {
        Map<Disk.Color, Integer> diskColorCounters = new HashMap<>();
        diskColorCounters.put(Disk.Color.BLACK, 0);
        diskColorCounters.put(Disk.Color.WHITE, 0);
        for (Coordinate coordinate : this) {
            if (board[coordinate.getRow()][coordinate.getColumn()] == null) {
                continue;
            }
            diskColorCounters.put(board[coordinate.getRow()][coordinate.getColumn()].getSideUp(),
                    diskColorCounters.get(board[coordinate.getRow()][coordinate.getColumn()].getSideUp()) + 1);
        }
        return Collections.max(diskColorCounters.entrySet(), Map.Entry.comparingByValue()).getKey();
    }
}
