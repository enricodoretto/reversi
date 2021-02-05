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

    public Coordinate(String inputCoordinate) {
        inputCoordinate = inputCoordinate.toUpperCase().trim();
        Matcher matcher = Pattern.compile("^([0-9]+)([A-Z]+)").matcher(inputCoordinate);
        if(!matcher.find()){
            throw new IllegalArgumentException("Malformed coordinate: must be of type number-letter");
        }
        this.row = Integer.parseInt(matcher.group(1)) - 1;
        this.column = LetterNumberConverter.convertLettersToNumber(matcher.group(2));
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
        return String.format("%d%s",row +1, LetterNumberConverter.convertNumberToLetter(column));
    }

}
