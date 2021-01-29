package sdm.reversi;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Coordinate {
    private final int row;
    private final int column;

    public Coordinate(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public Coordinate(int row, char column) {
        this(row - 1, Character.toUpperCase(column) - 'A');
    }

    public Coordinate(String inputCoordinate) {
        try {
            Matcher matcher = Pattern.compile("\\d+").matcher(inputCoordinate);
            matcher.find();
            this.row = Integer.parseInt(matcher.group()) - 1;
            this.column = inputCoordinate.toUpperCase().charAt(matcher.group().length()) - 'A';
        }
        catch (NullPointerException | IndexOutOfBoundsException |
                IllegalStateException | IllegalArgumentException e){
            throw new IllegalArgumentException();
        }

    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public Coordinate getShiftedCoordinate(ShiftDirection shiftDirection) {
        return new Coordinate(row + shiftDirection.getRowShift(), column + shiftDirection.getColumnShift());
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


    @Override
    public String toString() {
        return String.format("%d%c",row +1, column + 'A');
    }
}
