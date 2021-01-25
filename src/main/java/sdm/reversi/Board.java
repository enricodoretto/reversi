package sdm.reversi;

import java.util.*;
import java.util.stream.Collectors;

public class Board implements Iterable<Coordinate> {

    private final Disk[][] board;
    private final int size;
    private final static int DEFAULT_SIZE = 8;

    public Board() {
        this(DEFAULT_SIZE);
    }

    public Board(int size) {
        if (size % 2 != 0 || size < 4 || size > 26) throw new IllegalArgumentException();
        this.size = size;
        board = new Disk[size][size];
    }

    public int getSize() {
        return size;
    }

    public boolean isCellEmpty(Coordinate coordinate) {
        if (!isValidCell(coordinate)) {
            throw new IllegalArgumentException();
        }
        return board[coordinate.getRow()][coordinate.getColumn()] == null;
    }

    private boolean isValidIndex(int index) {
        return index >= 0 && index <= size - 1;
    }

    public boolean isValidCell(Coordinate coordinate) {
        return isValidIndex(coordinate.getRow()) && isValidIndex(coordinate.getColumn());
    }

    public boolean putDisk(Disk.Color diskColor, Coordinate coordinate){
        if (isCellEmpty(coordinate)) {
            board[coordinate.getRow()][coordinate.getColumn()] = new Disk(diskColor);
            return true;
        }
        return false;
    }

    public void flipDisk(Coordinate coordinate) {
        if (!isValidCell(coordinate))
            throw new IllegalArgumentException();
        board[coordinate.getRow()][coordinate.getColumn()].flip();
    }

    public Disk.Color getDiskColorFromCoordinate(Coordinate coordinate) {
        if (!isValidCell(coordinate))
            throw new IllegalArgumentException();
        Disk disk = board[coordinate.getRow()][coordinate.getColumn()];
        return disk == null ? null : disk.getSideUp();
    }

    public Disk.Color getColorWithMoreDisks() {
        Map<Disk.Color, Long> diskColorCounters = Arrays.stream(board).flatMap(c -> Arrays.stream(c))
                .filter(disk -> disk != null).collect(Collectors.groupingBy(
                disk -> disk.getSideUp(), Collectors.counting()
        ));
        if(diskColorCounters.get(Disk.Color.WHITE) == diskColorCounters.get(Disk.Color.BLACK)){
            return null;
        }
        return Collections.max(diskColorCounters.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    @Override
    public String toString() {
        return Arrays.stream(board).map(row -> Arrays.toString(row)
                .replace("null","-")
                .replaceAll("\\[|\\]|,", "")
                .replace(" ", "")).collect(Collectors.joining(System.lineSeparator()));
    }

    public Iterator<Coordinate> iterator() {
        return new Iterator<>() {

            private int currentRowIndex = 0;
            private int currentColumnIndex = 0;

            @Override
            public boolean hasNext() {
                return currentRowIndex < size && currentColumnIndex < size;
            }

            @Override
            public Coordinate next() {
                Coordinate nextCoordinate = new Coordinate(currentRowIndex, currentColumnIndex);
                if (currentColumnIndex == size - 1) {
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

}
