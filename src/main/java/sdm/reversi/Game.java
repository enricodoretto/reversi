package sdm.reversi;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class Game {
    private final Player player1;
    private final Player player2;
    protected Board board;
    protected Player currentPlayer;
    protected Map<Coordinate, Set<Coordinate>> allowedMovesForCurrentPlayer;

    public Game(String player1Name, String player2Name) {
        if (player1Name.equals(player2Name)) throw new IllegalArgumentException();
        this.player1 = new Player(player1Name, Disk.Color.BLACK);
        this.player2 = new Player(player2Name, Disk.Color.WHITE);
        this.currentPlayer = player1;
    }

    /*public Game(String player1Name, String player2Name, String customBoard){
        this(player1Name,player2Name);
        this.board = Board.parseBoard(customBoard);
    }*/

    public Map<Coordinate, Set<Coordinate>> getPlayerPossibleMoves() {
        calculatePlayerPossibleMoves();
        return allowedMovesForCurrentPlayer;
    }

    private void calculatePlayerPossibleMoves() {
        Map<Coordinate, Set<Coordinate>> validCoordinates = new HashMap<>();
        for (Coordinate coordinate : board.getBoardCoordinates()) {
            Set<Coordinate> disksToFlipForCoordinate = getDisksToFlip(coordinate);
            if (disksToFlipForCoordinate != null) {
                validCoordinates.put(coordinate, disksToFlipForCoordinate);
            }
        }
        allowedMovesForCurrentPlayer = validCoordinates.size() == 0 ? null : validCoordinates;
    }

    public void makeMove(Coordinate coordinate) {
        if (!allowedMovesForCurrentPlayer.containsKey(coordinate)) {
            throw new IllegalArgumentException();
        }
        board.putDisk(currentPlayer.getColor(), coordinate);
        for (Coordinate coordinateOfDiskToFlip : allowedMovesForCurrentPlayer.get(coordinate)) {
            board.flipDisk(coordinateOfDiskToFlip);
        }
        changeTurn();
        // we will need to check if this is null
    }

    public boolean areThereAvailableMoves(){
        return allowedMovesForCurrentPlayer.size() == 0 ? false : true;
    }

    private void changeTurn() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
        calculatePlayerPossibleMoves();
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public String getBoardRepresentation() {
        return board.toString();
    }

    protected abstract void initializeBoard();

    public boolean isValidMove(String stringCoordinate) {
        Coordinate coordinate = Coordinate.parseCoordinate(stringCoordinate);
        return isValidMove(coordinate);
    }

    public Set<Coordinate> getDisksToFlip(String stringCoordinate) {
        Coordinate coordinate = Coordinate.parseCoordinate(stringCoordinate);
        return getDisksToFlip(coordinate);
    }

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
            if (board.isValidCell(coordinate) || board.isCellEmpty(coordinate)) {
                return false;
            }
            if (board.getDiskColorFromCoordinate(coordinate) == diskColor) {
                return true;
            }
        }
    }

    private Set<Coordinate> getDisksToFlipInAValidDirection(Coordinate coordinate, Disk.Color diskColor, ShiftDirection shiftDirection) {
        if (!shiftedCellHasDiskWithDifferentColor(coordinate, diskColor, shiftDirection)) {
            return null;
        }
        Set<Coordinate> disksToFlipInADirection = new HashSet<>();
        while (true) {
            coordinate = coordinate.getShiftedCoordinate(shiftDirection);
            if (board.isValidCell(coordinate) || board.isCellEmpty(coordinate)) {
                return null;
            }
            if (board.getDiskColorFromCoordinate(coordinate) == diskColor) {
                return disksToFlipInADirection;
            }
            disksToFlipInADirection.add(coordinate);
        }
    }

    public Set<Coordinate> getDisksToFlip(Coordinate coordinate) {
        try {
            if (!board.isCellEmpty(coordinate)) {
                return null;
            }
            Set<Coordinate> disksToFlip = new HashSet<>();
            for (ShiftDirection shiftDirection : ShiftDirection.values()) {
                Set<Coordinate> disksToFlipInAValidDirection = getDisksToFlipInAValidDirection(coordinate, currentPlayer.getColor(), shiftDirection);
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
