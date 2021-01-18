package sdm.reversi;

public class Coordinate {
    private int row;
    private char column;

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
}
