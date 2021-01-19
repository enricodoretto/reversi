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

    public static Coordinate parseCoordinate(String inputCoordinate){
        //int row = Character.getNumericValue(inputCoordinate.charAt(0));
        //char column = inputCoordinate.charAt(1);
        return new Coordinate(1, 'A');
    }

    @Override
    public boolean equals(Object obj) {
        return row == ((Coordinate)obj).getRow() && column == ((Coordinate)obj).getColumn();
    }
}
