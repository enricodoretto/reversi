package sdm.reversi;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class Board implements Iterable<Coordinate> {

    private final Disk[][] board;
    private final static int DEFAULT_SIZE = 8;

    public Board() {
        this(DEFAULT_SIZE);
    }

    public Board(int size) {
        if (size % 2 != 0 || size < 4 || size > 26) throw new IllegalArgumentException();
        board = new Disk[size][size];
    }

    private static Disk[][] readBoard(BufferedReader bufferedReader) throws IOException{
        try (bufferedReader) {
            String line;
            int row = 0;
            int size = 0;
            Disk[][] board = null;
            while ((line = bufferedReader.readLine()) != null) {
                if (board == null) {
                    size = line.length();
                    board = new Disk[size][size];
                }
                if(line.length()!=size || row>=size){
                    throw new IllegalArgumentException();
                }
                for (int col = 0; col < size; col++) {
                    switch (line.charAt(col)) {
                        case 'B':
                            board[row][col] = new Disk(Disk.Color.BLACK);
                            break;
                        case 'W':
                            board[row][col] = new Disk(Disk.Color.WHITE);
                            break;
                        case '-':
                            break;
                        default:
                            throw new IllegalArgumentException();
                    }
                }
                row++;
            }
            if(row!=size){
                throw new IllegalArgumentException();
            }
            return board;
        }
    }

    public Board(URL fileURL) throws IOException {
        if(fileURL==null){
         throw new FileNotFoundException();
        }
        board = readBoard(new BufferedReader(new InputStreamReader(fileURL.openConnection().getInputStream())));
    }

    public int getSize() {
        return board.length;
    }

    public boolean isCellEmpty(Coordinate coordinate) {
        if (!isValidCell(coordinate)) {
            throw new IllegalArgumentException();
        }
        return board[coordinate.getRow()][coordinate.getColumn()] == null;
    }

    private boolean isValidIndex(int index) {
        return index >= 0 && index <= board.length - 1;
    }

    public boolean isValidCell(Coordinate coordinate) {
        return isValidIndex(coordinate.getRow()) && isValidIndex(coordinate.getColumn());
    }

    public boolean putDisk(Disk.Color diskColor, Coordinate coordinate) {
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
        Map<Disk.Color, Long> diskColorCounters = Arrays.stream(board).flatMap(Arrays::stream)
                .filter(Objects::nonNull).collect(Collectors.groupingBy(
                        Disk::getSideUp, Collectors.counting()
                ));
        if (diskColorCounters.get(Disk.Color.WHITE).equals(diskColorCounters.get(Disk.Color.BLACK))) {
            return null;
        }
        return Collections.max(diskColorCounters.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    @Override
    public String toString() {
        return Arrays.stream(board).map(row -> Arrays.toString(row)
                .replace("null", "-")
                .replaceAll("\\[|\\]|,", "")
                .replace(" ", "")).collect(Collectors.joining(System.lineSeparator()));
    }

    public Iterator<Coordinate> iterator() {
        return new Iterator<>() {

            private int currentRowIndex = 0;
            private int currentColumnIndex = 0;

            @Override
            public boolean hasNext() {
                return currentRowIndex < board.length && currentColumnIndex < board.length;
            }

            @Override
            public Coordinate next() {
                Coordinate nextCoordinate = new Coordinate(currentRowIndex, currentColumnIndex);
                if (currentColumnIndex == board.length - 1) {
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
