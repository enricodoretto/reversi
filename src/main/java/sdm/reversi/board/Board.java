package sdm.reversi.board;

import sdm.reversi.utils.LetterNumberConverter;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Board implements Serializable {
    private final Disk[][] board;

    public Board(int size) {
        if (size % 2 != 0 || size < 4 || size > 26)
            throw new IllegalArgumentException("Board size must be even and between 4 and 26");
        board = new Disk[size][size];
    }

    public Board(URL fileURL) throws IOException {
        if (fileURL == null) {
            throw new FileNotFoundException();
        }
        board = readBoard(new BufferedReader(new InputStreamReader(fileURL.openConnection().getInputStream())));
    }

    private static Disk[][] readBoard(BufferedReader bufferedReader) throws IOException {
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
                if (line.length() != size || row >= size) {
                    throw new IllegalArgumentException("Error in parsing board from file");
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
                            throw new IllegalArgumentException("Error in parsing board from file");
                    }
                }
                row++;
            }
            if (row != size) {
                throw new IllegalArgumentException("Error in parsing board from file");
            }
            return board;
        }
    }

    public int getSize() {
        return board.length;
    }

    private boolean isCellInsideBoard(Coordinate coordinate) {
        return isIndexValid(coordinate.getRow()) && isIndexValid(coordinate.getColumn());
    }

    private boolean isIndexValid(int index) {
        return index >= 0 && index <= board.length - 1;
    }

    public boolean isCellAvailable(Coordinate coordinate) {
        return isCellInsideBoard(coordinate) && board[coordinate.getRow()][coordinate.getColumn()] == null;
    }

    private boolean isCellOccupied(Coordinate coordinate) {
        return isCellInsideBoard(coordinate) && board[coordinate.getRow()][coordinate.getColumn()] != null;
    }

    public Disk.Color getDiskColorFromCoordinate(Coordinate coordinate) {
        if (!isCellInsideBoard(coordinate))
            throw new IllegalArgumentException("Invalid cell");
        Disk disk = board[coordinate.getRow()][coordinate.getColumn()];
        return disk == null ? null : disk.getSideUp();
    }

    private boolean shiftedCellHasDiskWithDifferentColor(Coordinate coordinate, Disk.Color diskColor, Coordinate.ShiftDirection shiftDirection) {
        Coordinate shiftedCellCoordinate = coordinate.getShiftedCoordinate(shiftDirection);
        return isCellOccupied(shiftedCellCoordinate) && !(getDiskColorFromCoordinate(shiftedCellCoordinate) == diskColor);
    }

    public Map<Coordinate, Set<Coordinate>> getValidMoves(Disk.Color diskColor) {
        return getAvailableCells().stream()
                .flatMap(coordinate -> {
                    Set<Coordinate> disksToFlip = getDisksToFlip(coordinate, diskColor);
                    return coordinate != null && disksToFlip != null ?
                            Stream.of(Map.entry(coordinate, disksToFlip)) : null;
                }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    protected Set<Coordinate> getDisksToFlip(Coordinate coordinate, Disk.Color diskColor) {
        Set<Coordinate> disksToFlip = Stream.of(Coordinate.ShiftDirection.values()).parallel()
                .map(direction -> getDisksToFlipInADirection(coordinate, diskColor, direction))
                .filter(Objects::nonNull).flatMap(Set::stream).collect(Collectors.toSet());
        return disksToFlip.size() == 0 ? null : disksToFlip;
    }

    private Set<Coordinate> getDisksToFlipInADirection(Coordinate coordinate, Disk.Color diskColor, Coordinate.ShiftDirection shiftDirection) {
        if (!shiftedCellHasDiskWithDifferentColor(coordinate, diskColor, shiftDirection)) {
            return null;
        }
        Set<Coordinate> disksToFlipInADirection = new HashSet<>();
        while (true) {
            coordinate = coordinate.getShiftedCoordinate(shiftDirection);
            if (!isCellOccupied(coordinate)) {
                return null;
            }
            if (getDiskColorFromCoordinate(coordinate) == diskColor) {
                return disksToFlipInADirection;
            }
            disksToFlipInADirection.add(coordinate);
        }
    }

    public void makeMove(Disk.Color diskColor, Coordinate coordinate, Collection<Coordinate> disksToFlip) {
        putDisk(diskColor, coordinate);
        disksToFlip.forEach(this::flipDisk);
    }

    public void putDisk(Disk.Color diskColor, Coordinate coordinate) {
        if (!isCellAvailable(coordinate)) {
            throw new IllegalArgumentException("Cell already full");
        }
        board[coordinate.getRow()][coordinate.getColumn()] = new Disk(diskColor);
    }

    protected void flipDisk(Coordinate coordinate) {
        if (!isCellOccupied(coordinate))
            throw new IllegalArgumentException("Invalid cell");
        board[coordinate.getRow()][coordinate.getColumn()].flip();
    }

    public boolean isFull() {
        return Arrays.stream(board).flatMap(Arrays::stream).noneMatch(Objects::isNull);
    }

    public Collection<Coordinate> getAvailableCells() {
        return IntStream.range(0, board.length).boxed()
                .flatMap(x -> IntStream.range(0, board.length)
                        .mapToObj(y -> new Coordinate(x, y))
                        .filter(this::isCellAvailable))
                .collect(Collectors.toSet());
    }

    public int getNumberOfDisks() {
        return (int) Arrays.stream(board).parallel().flatMap(Arrays::stream).filter(Objects::nonNull).count();
    }

    public int getNumberOfDisksForColor(Disk.Color color) {
        return (int) Arrays.stream(board).flatMap(Arrays::stream).filter(d -> d != null && d.getSideUp().equals(color)).count();
    }

    @Override
    public String toString() {
        int initialColumnSize = board.length < 9 ? 1 : 2;
        return String.format("%" + initialColumnSize + "s ", " ")
                + IntStream.range(0, board.length).mapToObj(LetterNumberConverter::convertNumberToLetter).collect(Collectors.joining())
                + System.lineSeparator()
                + IntStream.range(0, board.length).mapToObj(rowIndex ->
                String.format("%" + initialColumnSize + "d ", (rowIndex + 1)) +
                        Arrays.toString(board[rowIndex])
                                .replace("null", "-")
                                .replaceAll("[\\[\\],]", "")
                                .replace(" ", ""))
                .collect(Collectors.joining(System.lineSeparator()));
    }

}
