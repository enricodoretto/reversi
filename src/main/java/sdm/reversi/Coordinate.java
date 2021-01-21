package sdm.reversi;

import java.util.Objects;

public class Coordinate {
    private final int row;
    private final int column;

    public Coordinate(int row, char column) throws IllegalArgumentException {
        column = Character.toUpperCase(column);
        this.row = row-1;
        this.column = column-'A';
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
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
