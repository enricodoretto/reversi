package sdm.reversi;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class Board {

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

    public boolean isFull(){
        return Arrays.stream(board).flatMap(Arrays::stream).filter(c -> c == null).count()==0;
    }

    public boolean isCellEmpty(Coordinate coordinate) {
        if (!isCellValid(coordinate)) {
            throw new IllegalArgumentException();
        }
        return board[coordinate.getRow()][coordinate.getColumn()] == null;
    }

    public boolean isCellAvailable(Coordinate coordinate){
        return isCellValid(coordinate) && isCellEmpty(coordinate);
    }

    public boolean isCellOccupied(Coordinate coordinate){
        return isCellValid(coordinate) && !isCellEmpty(coordinate);
    }

    public Collection<Coordinate> getAvailableCells(){
        Collection<Coordinate> coordinates = new HashSet<>();
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board[0].length; j++){
                Coordinate coordinate = new Coordinate(i, j);
                if(isCellAvailable(coordinate))
                    coordinates.add(coordinate);
            }
        }
        return coordinates;
    }

    private boolean isIndexValid(int index) {
        return index >= 0 && index <= board.length - 1;
    }

    public boolean isCellValid(Coordinate coordinate) {
        return isIndexValid(coordinate.getRow()) && isIndexValid(coordinate.getColumn());
    }

    public boolean putDisk(Disk.Color diskColor, Coordinate coordinate) {
        if (isCellEmpty(coordinate)) {
            board[coordinate.getRow()][coordinate.getColumn()] = new Disk(diskColor);
            return true;
        }
        return false;
    }

    public void flipDisk(Coordinate coordinate) {
        if (!isCellValid(coordinate))
            throw new IllegalArgumentException();
        board[coordinate.getRow()][coordinate.getColumn()].flip();
    }

    public long getNumberOfDisks(){
        return Arrays.stream(board).flatMap(Arrays::stream).filter(c -> c != null).count();
    }

    public Disk.Color getDiskColorFromCoordinate(Coordinate coordinate) {
        if (!isCellValid(coordinate))
            throw new IllegalArgumentException();
        Disk disk = board[coordinate.getRow()][coordinate.getColumn()];
        return disk == null ? null : disk.getSideUp();
    }

    public int getNumberOfDisksForColor(Disk.Color color){
        return (int)Arrays.stream(board).flatMap(Arrays::stream).filter(d -> d!=null && d.getSideUp().equals(color)).count();
    }

    public boolean shiftedCellHasDiskWithDifferentColor(Coordinate coordinate, Disk.Color diskColor, ShiftDirection shiftDirection) {
        return isCellOccupied(coordinate.getShiftedCoordinate(shiftDirection)) &&
                !(getDiskColorFromCoordinate(coordinate.getShiftedCoordinate(shiftDirection)) == diskColor);
    }

    @Override
    public String toString() {
        return Arrays.stream(board).map(row -> Arrays.toString(row)
                .replace("null", "-")
                .replaceAll("\\[|\\]|,", "")
                .replace(" ", "")).collect(Collectors.joining(System.lineSeparator()));
    }

}
