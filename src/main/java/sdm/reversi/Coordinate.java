package sdm.reversi;

import java.util.Objects;

public class Coordinate {
    private final int row;
    private final int column;

    public enum ShiftDirection{
        UP(-1,0),
        DOWN(1,0),
        LEFT(0, -1),
        RIGHT(0,1),
        UP_LEFT(-1, -1),
        UP_RIGHT(-1, 1),
        DOWN_LEFT(1, -1),
        DOWN_RIGHT(1,1);

        private int rowShift;
        private int columnShift;

        ShiftDirection(int rowShift, int columnShift) {
            this.rowShift = rowShift;
            this.columnShift = columnShift;
        }

        public int getRowShift() {
            return rowShift;
        }

        public int getColumnShift() {
            return columnShift;
        }
    }

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

    public Coordinate getShiftedCoordinate(Coordinate.ShiftDirection shiftDirection){
        return new Coordinate(row+shiftDirection.rowShift, column+shiftDirection.columnShift);
    }

    public Coordinate getAboveCoordinate() {
        return getShiftedCoordinate(ShiftDirection.UP);
    }

    public Coordinate getBelowCoordinate() {
        return getShiftedCoordinate(ShiftDirection.DOWN);
    }

    public Coordinate getRightCoordinate() {
        return getShiftedCoordinate(ShiftDirection.RIGHT);
    }

    public Coordinate getLeftCoordinate(){
        return getShiftedCoordinate(ShiftDirection.LEFT);
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
