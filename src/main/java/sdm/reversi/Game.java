package sdm.reversi;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class Game {
    protected final Player player1;
    protected final Player player2;
    protected Board board;
    protected Player currentPlayer;
    protected Map<Coordinate, Set<Coordinate>> allowedMovesForCurrentPlayer;

    public Game(String player1Name, String player2Name, int boardSize) {
        this(player1Name, player2Name);
        board = new Board(boardSize);
    }

    public Game(String player1Name, String player2Name) {
        if (player1Name.equals(player2Name)) throw new IllegalArgumentException();
        player1 = new Player(player1Name, Disk.Color.BLACK);
        player2 = new Player(player2Name, Disk.Color.WHITE);
        currentPlayer = player1;
        board = new Board();
    }

    public Game(String player1Name, String player2Name, URL boardFileURL) throws IOException {
        this(player1Name, player2Name);
        board = new Board(boardFileURL);
        if(board.getNumberOfDisks()<4){
            throw new IllegalArgumentException();
        }
    }

    protected abstract void initializeBoard();

    public String getBoardRepresentation() {
        return board.toString();
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }
















    public boolean isValidMove(Coordinate coordinate) {
        return getDisksToFlip(coordinate)!=null ? true: false;
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

    private Set<Coordinate> getDisksToFlipInAValidDirection(Coordinate coordinate, Disk.Color diskColor, ShiftDirection shiftDirection) {
        if (!shiftedCellHasDiskWithDifferentColor(coordinate, diskColor, shiftDirection)) {
            return null;
        }
        Set<Coordinate> disksToFlipInADirection = new HashSet<>();
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

























    public Map<Coordinate, Set<Coordinate>> getPlayerPossibleMoves() {
        calculatePlayerPossibleMoves();
        return allowedMovesForCurrentPlayer;
    }

    private void calculatePlayerPossibleMoves() {
        Map<Coordinate, Set<Coordinate>> validCoordinates = new HashMap<>();
        for (Coordinate coordinate : board) {
            Set<Coordinate> disksToFlipForCoordinate = getDisksToFlip(coordinate);
            if (disksToFlipForCoordinate != null) {
                validCoordinates.put(coordinate, disksToFlipForCoordinate);
            }
        }
        if(validCoordinates.size() == 0){
            allowedMovesForCurrentPlayer = null;
            currentPlayer.setInStall(true);
        }
        else{
            allowedMovesForCurrentPlayer = validCoordinates;
            currentPlayer.setInStall(false);
        }
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

    protected void changeTurn() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
        calculatePlayerPossibleMoves();
    }

    public abstract boolean isOver();

    public Player getWinner(){
        if(!isOver()){
            return null;
        }
        Disk.Color winnerColor = board.getColorWithMoreDisks();
        if(winnerColor == null){
            return null;
        }
        if(winnerColor == Disk.Color.BLACK){
            return player1;
        }else{
            return player2;
        }
    }
}
