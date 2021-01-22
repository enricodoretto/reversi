package sdm.reversi;

import java.util.ArrayList;
import java.util.List;

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
            for (ShiftDirection shiftDirection : ShiftDirection.values()) {
                if (checkIfMoveInADirectionIsValid(coordinate, currentPlayer.getColor(), shiftDirection)) {
                    return true;
                }
            }
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private boolean shiftedCellHasDiskWithDifferentColor(Coordinate coordinate, Disk.Color diskColor, ShiftDirection shiftDirection) {
        return !(board.isCellEmpty(coordinate.getShiftedCoordinate(shiftDirection)) ||
                board.getDiskColorFromCoordinate(coordinate.getShiftedCoordinate(shiftDirection)) == diskColor);
    }

    private boolean checkIfMoveInADirectionIsValid(Coordinate coordinate, Disk.Color diskColor, ShiftDirection shiftDirection) {
        if (!shiftedCellHasDiskWithDifferentColor(coordinate, diskColor, shiftDirection)) {
            return false;
        }
        while (true) {
            coordinate = coordinate.getShiftedCoordinate(shiftDirection);
            if (!board.isValidCell(coordinate) || board.isCellEmpty(coordinate)) {
                return false;
            }
            if (board.getDiskColorFromCoordinate(coordinate) == diskColor) {
                return true;
            }
        }
    }

    private List<Coordinate> getDisksToFlipInAValidDirection(Coordinate coordinate, Disk.Color diskColor, ShiftDirection shiftDirection) {
        if (!shiftedCellHasDiskWithDifferentColor(coordinate, diskColor, shiftDirection)) {
            return null;
        }
        List<Coordinate> disksToFlipInADirection = new ArrayList<>();
        while (true) {
            coordinate = coordinate.getShiftedCoordinate(shiftDirection);
            if (!board.isValidCell(coordinate) || board.isCellEmpty(coordinate)) {
                return null;
            }
            if (board.getDiskColorFromCoordinate(coordinate) == diskColor) {
                return disksToFlipInADirection;
            }
            disksToFlipInADirection.add(coordinate);
        }
    }

    public List<Coordinate> getDisksToFlip(Coordinate coordinate) {
        try {
            if (!board.isCellEmpty(coordinate)) {
                return null;
            }
            List<Coordinate> disksToFlip = new ArrayList<>();
            for (ShiftDirection shiftDirection : ShiftDirection.values()) {
                List<Coordinate> disksToFlipInAValidDirection = getDisksToFlipInAValidDirection(coordinate, currentPlayer.getColor(), shiftDirection);
                if (disksToFlipInAValidDirection != null) {
                    disksToFlip.addAll(disksToFlipInAValidDirection);
                }
            }
            return disksToFlip.size() == 0 ? null : disksToFlip;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
