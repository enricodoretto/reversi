package sdm.reversi;

public class ReversiGame extends Game {

    public ReversiGame(String player1Name, String player2Name) throws IllegalArgumentException {
        super(player1Name, player2Name);
        initializeBoard();
    }

    @Override
    protected void initializeBoard() {
        board = new Board();
        Disk blackDisk = new Disk(Disk.Color.BLACK);
        Disk whiteDisk = new Disk(Disk.Color.WHITE);
        board.putDisk(whiteDisk, Coordinate.parseCoordinate("4D"));
        board.putDisk(whiteDisk, Coordinate.parseCoordinate("5E"));
        board.putDisk(blackDisk, Coordinate.parseCoordinate("4E"));
        board.putDisk(blackDisk, Coordinate.parseCoordinate("5D"));
    }

    @Override
    public boolean isValidMove(Coordinate coordinate) {
        try {
            if (!board.isCellEmpty(coordinate)) {
                return false;
            }
            return (checkIfAVerticalUpMoveIsValid(coordinate, currentPlayer.getColor()) ||
                    checkIfAVerticalDownMoveIsValid(coordinate, currentPlayer.getColor()) ||
                    checkIfAHorizontalRightMoveIsValid(coordinate, currentPlayer.getColor()) ||
                    checkIfAHorizontalLeftMoveIsValid(coordinate, currentPlayer.getColor()) ||
                    checkIfAUpLeftMoveIsValid(coordinate, currentPlayer.getColor()) ||
                    checkIfAUpRightMoveIsValid(coordinate, currentPlayer.getColor())) ||
                    checkIfADownLeftMoveIsValid(coordinate, currentPlayer.getColor()) ||
                    checkIfADownRightMoveIsValid(coordinate, currentPlayer.getColor());
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private boolean shiftedCellHasDiskWithDifferentColor(Coordinate coordinate, Disk.Color diskColor, ShiftDirection shiftDirection) {
        return !(board.isCellEmpty(coordinate.getShiftedCoordinate(shiftDirection)) ||
                board.getDiskColorFromCoordinate(coordinate.getShiftedCoordinate(shiftDirection)) == diskColor);
    }

    private boolean cellBelowHasDiskWithDifferentColor(Coordinate coordinate, Disk.Color diskColor) {
        return shiftedCellHasDiskWithDifferentColor(coordinate, diskColor, ShiftDirection.DOWN);
    }

    private boolean cellAboveHasDiskWithDifferentColor(Coordinate coordinate, Disk.Color diskColor) {
        return shiftedCellHasDiskWithDifferentColor(coordinate, diskColor, ShiftDirection.UP);
    }

    private boolean cellRightHasDiskWithDifferentColor(Coordinate coordinate, Disk.Color diskColor) {
        return shiftedCellHasDiskWithDifferentColor(coordinate, diskColor, ShiftDirection.RIGHT);
    }

    private boolean cellLeftHasDiskWithDifferentColor(Coordinate coordinate, Disk.Color diskColor) {

        return shiftedCellHasDiskWithDifferentColor(coordinate, diskColor, ShiftDirection.LEFT);
    }

    private boolean cellUpLeftHasDiskWithDifferentColor(Coordinate coordinate, Disk.Color diskColor) {

        return shiftedCellHasDiskWithDifferentColor(coordinate, diskColor, ShiftDirection.UP_LEFT);
    }

    private boolean cellUpRightHasDiskWithDifferentColor(Coordinate coordinate, Disk.Color diskColor) {
        return shiftedCellHasDiskWithDifferentColor(coordinate, diskColor, ShiftDirection.UP_RIGHT);
    }

    private boolean cellDownLeftHasDiskWithDifferentColor(Coordinate coordinate, Disk.Color diskColor) {
        return shiftedCellHasDiskWithDifferentColor(coordinate, diskColor, ShiftDirection.DOWN_LEFT);
    }

    private boolean cellDownRightHasDiskWithDifferentColor(Coordinate coordinate, Disk.Color diskColor) {
        return shiftedCellHasDiskWithDifferentColor(coordinate, diskColor, ShiftDirection.DOWN_RIGHT);
    }

    private boolean checkIfAVerticalUpMoveIsValid(Coordinate coordinate, Disk.Color diskColor) {
        if (!cellAboveHasDiskWithDifferentColor(coordinate, diskColor)) {
            return false;
        }
        while (true) {
            coordinate = coordinate.getShiftedCoordinate(ShiftDirection.UP);
            if (!board.isValidCell(coordinate) || board.isCellEmpty(coordinate)) {
                return false;
            }
            if (board.getDiskColorFromCoordinate(coordinate) == diskColor) {
                return true;
            }

        }
    }

    private boolean checkIfAVerticalDownMoveIsValid(Coordinate coordinate, Disk.Color diskColor) {
        if (!cellBelowHasDiskWithDifferentColor(coordinate, diskColor)) {
            return false;
        }
        while (true) {
            coordinate = coordinate.getShiftedCoordinate(ShiftDirection.DOWN);
            if (!board.isValidCell(coordinate) || board.isCellEmpty(coordinate)) {
                return false;
            }
            if (board.getDiskColorFromCoordinate(coordinate) == diskColor) {
                return true;
            }

        }
    }

    private boolean checkIfAHorizontalRightMoveIsValid(Coordinate coordinate, Disk.Color diskColor) {
        if (!cellRightHasDiskWithDifferentColor(coordinate, diskColor)) {
            return false;
        }
        while (true) {
            coordinate = coordinate.getShiftedCoordinate(ShiftDirection.RIGHT);
            if (!board.isValidCell(coordinate) || board.isCellEmpty(coordinate)) {
                return false;
            }
            if (board.getDiskColorFromCoordinate(coordinate) == diskColor) {
                return true;
            }

        }
    }

    private boolean checkIfAHorizontalLeftMoveIsValid(Coordinate coordinate, Disk.Color diskColor) {
        if (!cellLeftHasDiskWithDifferentColor(coordinate, diskColor)) {
            return false;
        }
        while (true) {
            coordinate = coordinate.getShiftedCoordinate(ShiftDirection.LEFT);
            if (!board.isValidCell(coordinate) || board.isCellEmpty(coordinate)) {
                return false;
            }
            if (board.getDiskColorFromCoordinate(coordinate) == diskColor) {
                return true;
            }

        }
    }

    private boolean checkIfAUpLeftMoveIsValid(Coordinate coordinate, Disk.Color diskColor) {
        if (!cellUpLeftHasDiskWithDifferentColor(coordinate, diskColor)) {
            return false;
        }
        while (true) {
            coordinate = coordinate.getShiftedCoordinate(ShiftDirection.UP_LEFT);
            if (!board.isValidCell(coordinate) || board.isCellEmpty(coordinate)) {
                return false;
            }
            if (board.getDiskColorFromCoordinate(coordinate) == diskColor) {
                return true;
            }

        }
    }

    private boolean checkIfAUpRightMoveIsValid(Coordinate coordinate, Disk.Color diskColor) {
        if (!cellUpRightHasDiskWithDifferentColor(coordinate, diskColor)) {
            return false;
        }
        while (true) {
            coordinate = coordinate.getShiftedCoordinate(ShiftDirection.UP_RIGHT);
            if (!board.isValidCell(coordinate) || board.isCellEmpty(coordinate)) {
                return false;
            }
            if (board.getDiskColorFromCoordinate(coordinate) == diskColor) {
                return true;
            }

        }
    }

    private boolean checkIfADownLeftMoveIsValid(Coordinate coordinate, Disk.Color diskColor) {
        if (!cellDownLeftHasDiskWithDifferentColor(coordinate, diskColor)) {
            return false;
        }
        while (true) {
            coordinate = coordinate.getShiftedCoordinate(ShiftDirection.DOWN_LEFT);
            if (!board.isValidCell(coordinate) || board.isCellEmpty(coordinate)) {
                return false;
            }
            if (board.getDiskColorFromCoordinate(coordinate) == diskColor) {
                return true;
            }

        }
    }

    private boolean checkIfADownRightMoveIsValid(Coordinate coordinate, Disk.Color diskColor) {
        if (!cellDownRightHasDiskWithDifferentColor(coordinate, diskColor)) {
            return false;
        }
        while (true) {
            coordinate = coordinate.getShiftedCoordinate(ShiftDirection.DOWN_RIGHT);
            if (!board.isValidCell(coordinate) || board.isCellEmpty(coordinate)) {
                return false;
            }
            if (board.getDiskColorFromCoordinate(coordinate) == diskColor) {
                return true;
            }

        }
    }
}
