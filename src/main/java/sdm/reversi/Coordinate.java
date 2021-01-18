package sdm.reversi;

public class Coordinate {
    private int row;
    private char column;

    public Coordinate(int row, char column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public char getColumn() {
        return column;
    }
}
