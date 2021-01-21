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
        if (!board.isCellEmpty(coordinate)) {
            return false;
        }
        return (checkIfAVerticalUpMoveIsValid(coordinate, currentPlayer.getColor()) ||
                checkIfAVerticalDownMoveIsValid(coordinate, currentPlayer.getColor()) ||
                cellRightHasDiskWithDifferentColor(coordinate, currentPlayer.getColor()) ||
                cellLeftHasDiskWithDifferentColor(coordinate, currentPlayer.getColor()));
    }

    private boolean cellBelowHasDiskWithDifferentColor(Coordinate coordinate, Disk.Color diskColor) {
        return !(board.isCellEmpty(coordinate.getBelowCoordinate()) ||
                board.getDiskColorFromCoordinate(coordinate.getBelowCoordinate()) == diskColor);
    }

    private boolean cellAboveHasDiskWithDifferentColor(Coordinate coordinate, Disk.Color diskColor) {
        return !(board.isCellEmpty(coordinate.getAboveCoordinate()) ||
                board.getDiskColorFromCoordinate(coordinate.getAboveCoordinate()) == diskColor);
    }

    private boolean cellRightHasDiskWithDifferentColor(Coordinate coordinate, Disk.Color diskColor) {
        return !(board.isCellEmpty(coordinate.getRightCoordinate()) ||
                board.getDiskColorFromCoordinate(coordinate.getRightCoordinate()) == diskColor);
    }

    private boolean cellLeftHasDiskWithDifferentColor(Coordinate coordinate, Disk.Color diskColor) {
        return !(board.isCellEmpty(coordinate.getLeftCoordinate()) ||
                board.getDiskColorFromCoordinate(coordinate.getLeftCoordinate()) == diskColor);
    }

    private boolean checkIfAVerticalUpMoveIsValid(Coordinate coordinate, Disk.Color diskColor) {
        if (!cellAboveHasDiskWithDifferentColor(coordinate, diskColor)) {
            return false;
        }
        while (true) {
            coordinate = coordinate.getAboveCoordinate();
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
            coordinate = coordinate.getBelowCoordinate();
            if (!board.isValidCell(coordinate) || board.isCellEmpty(coordinate)) {
                return false;
            }
            if (board.getDiskColorFromCoordinate(coordinate) == diskColor) {
                return true;
            }

        }
    }




}
