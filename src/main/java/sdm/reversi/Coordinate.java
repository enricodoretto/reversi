package sdm.reversi;

import java.util.Objects;

public class Coordinate {
    private final int row;
    private final char column;

    public Coordinate(int row, char column) throws IllegalArgumentException {
        column = Character.toUpperCase(column);
        if(!isValidRow(row) || !isValidColumn(column)) throw new IllegalArgumentException();
        this.row = row;
        this.column = column;
    }

    private static boolean isValidRow(int row) {
        return row >= 1 && row <= 8;
    }

    private static boolean isValidColumn(char column){
        return column >= 'A' && column <= 'H';
    }

    public int getRow() {
        return row;
    }

    public char getColumn() {
        return column;
    }

    public static Coordinate parseCoordinate(String inputCoordinate){
        int row = Character.getNumericValue(inputCoordinate.charAt(0));
        char column = inputCoordinate.charAt(1);
        return new Coordinate(row, column);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return row == that.row && column == that.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

}
