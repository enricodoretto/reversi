package sdm.reversi;

public class Coordinate {
    private int row;
    private char column;

    public Coordinate(int row, char column) throws IllegalArgumentException {
        if(!isValidRow(row)) throw new IllegalArgumentException();
        this.row = row;
        this.column = column;
    }

    public boolean isValidRow(int row) {
        if (row < 1 || row > 8) {
            return false;
        }
        return true;
    }

    public int getRow() {
        return row;
    }

    public char getColumn() {
        return column;
    }
}
