package sdm.reversi;

import sdm.reversi.utils.LetterNumberConverter;

import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Coordinate implements Comparable<Coordinate>, Serializable {
    private final int row;
    private final int column;

    public enum ShiftDirection {
        UP(-1, 0),
        DOWN(1, 0),
        LEFT(0, -1),
        RIGHT(0, 1),
        UP_LEFT(-1, -1),
        UP_RIGHT(-1, 1),
        DOWN_LEFT(1, -1),
        DOWN_RIGHT(1, 1);

        private final int rowShift;
        private final int columnShift;

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
