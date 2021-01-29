package sdm.reversi;

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


