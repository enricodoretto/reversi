package sdm.reversi;

import java.util.Objects;

public class Coordinate {
    private final int row;
    private final int column;

    public Coordinate(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public Coordinate(int row, char column) {
        column = Character.toUpperCase(column);
        this.row = row - 1;
        this.column = column - 'A';
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public static Coordinate parseCoordinate(String inputCoordinate) {
        int row = Character.getNumericValue(inputCoordinate.charAt(0));
        char column = inputCoordinate.charAt(1);
        return new Coordinate(row, column);
    }

    public Coordinate getAboveCoordinate() {
        return new Coordinate(row - 1, column);
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

    public Coordinate getBelowCoordinate() {
        return new Coordinate(row + 1, column);
    }

    public Coordinate getRightCoordinate() {
        return new Coordinate(row, column+1);
    }

    public Coordinate getLeftCoordinate(){
        return new Coordinate(row, column-1);
    }

    public Coordinate getUpLeftCoordinate() {
        return new Coordinate(row-1, column-1);
    }

    public Coordinate getUpRightCoordinate() {
        return new Coordinate(row-1, column+1);
    }

    public Coordinate getDownLeftCoordinate() {
        return new Coordinate(row+1, column-1);
    }

    public Coordinate getDownRightCoordinate() {
        return new Coordinate(row+1, column+1);
    }
}
