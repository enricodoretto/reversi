package sdm.reversi;

import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Coordinate implements Comparable<Coordinate>, Serializable {
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
        if(!Pattern.compile("^[0-9]{1,2}[A-Za-z]{1}").matcher(inputCoordinate).find()){
            throw new IllegalArgumentException();
        }
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
    public int compareTo(Coordinate o) {
        int compareRow = Integer.compare(this.row, o.row);
        if(compareRow != 0) return compareRow;
        return Integer.compare(this.column, o.column);
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
