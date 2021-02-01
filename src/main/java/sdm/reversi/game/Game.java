package sdm.reversi.game;

import sdm.reversi.*;
import sdm.reversi.manager.CLIManager;
import sdm.reversi.manager.GameManager;
import sdm.reversi.manager.NotificationsManager;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Game {
    protected final Player player1;
    protected final Player player2;
    protected Board board;
    protected Player currentPlayer;
    protected Map<Coordinate, Set<Coordinate>> allowedMovesForCurrentPlayer;
    protected int numberOfMoves;
    protected boolean isQuit;
    private GameManager gameManager;

    public Game(String player1Name, String player2Name) {
        this(player1Name, player2Name, new CLIManager());
    }

    public Game(String player1Name, String player2Name, int boardSize) {
        this(player1Name, player2Name);
        board = new Board(boardSize);
    }

    public Game(String player1Name, String player2Name, URL boardFileURL) throws IOException {
        this(player1Name, player2Name);
        board = new Board(boardFileURL);
        if (board.getNumberOfDisks() < 4) {
            throw new IllegalArgumentException();
        }
        numberOfMoves = (int) board.getNumberOfDisks();
    }

    public Game(String player1Name, String player2Name, GameManager gameManager) {
        if (player1Name.equals(player2Name)) throw new IllegalArgumentException();
        player1 = new Player(player1Name, Disk.Color.BLACK, gameManager);
        player2 = new Player(player2Name, Disk.Color.WHITE, gameManager);
        currentPlayer = player1;
        board = new Board();
        this.gameManager = gameManager;
    }

    public Game(String player1Name, String player2Name, GameManager gameManager, int size) {
        this(player1Name, player2Name, gameManager);
        board = new Board(size);
    }

    public Board getBoard() {
        return board;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isValidMove(Coordinate coordinate) {
        return allowedMovesForCurrentPlayer.containsKey(coordinate);
    }

    public Set<Coordinate> getDisksToFlip(Coordinate coordinate) {
        if (!board.isCellAvailable(coordinate)) {
            return null;
        }
        Set<Coordinate> disksToFlip = Stream.of(ShiftDirection.values())
                .map(direction -> getDisksToFlipInADirection(coordinate, currentPlayer.getColor(), direction))
                .filter(Objects::nonNull).flatMap(Set::stream).collect(Collectors.toSet());
        return disksToFlip.size() == 0 ? null : disksToFlip;
    }


    private Set<Coordinate> getDisksToFlipInADirection(Coordinate coordinate, Disk.Color diskColor, ShiftDirection shiftDirection) {
        if (!board.shiftedCellHasDiskWithDifferentColor(coordinate, diskColor, shiftDirection)) {
            return null;
        }
        Set<Coordinate> disksToFlipInADirection = new HashSet<>();
        while (true) {
            coordinate = coordinate.getShiftedCoordinate(shiftDirection);
            if (!board.isCellOccupied(coordinate)) {
                return null;
            }
            if (board.getDiskColorFromCoordinate(coordinate) == diskColor) {
                return disksToFlipInADirection;
            }
            disksToFlipInADirection.add(coordinate);
        }
    }


    public Map<Coordinate, Set<Coordinate>> getPlayerPossibleMoves() {
        return allowedMovesForCurrentPlayer;
    }

    protected void calculatePlayerPossibleMoves() {
        Map<Coordinate, Set<Coordinate>> validCoordinates = board.getAvailableCells().stream()
                .flatMap(coordinate -> {
                    Set<Coordinate> disksToFlip = getDisksToFlip(coordinate);
                    return coordinate != null && disksToFlip != null ?
                            Stream.of(Map.entry(coordinate, disksToFlip)) : null;
                }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        if (validCoordinates.size() == 0) {
            allowedMovesForCurrentPlayer = null;
            currentPlayer.setInStall(true);
        } else {
            allowedMovesForCurrentPlayer = validCoordinates;
            currentPlayer.setInStall(false);
        }
    }

    public void makeMove(Coordinate coordinate) {
        if (!isValidMove(coordinate)) {
            throw new IllegalArgumentException();
        }
        board.putDisk(currentPlayer.getColor(), coordinate);
        allowedMovesForCurrentPlayer.get(coordinate).forEach(c -> board.flipDisk(c));
        updatePlayersScore();
        numberOfMoves ++;
    }

    protected void updatePlayersScore(){
        player1.setScore(board.getNumberOfDisksForColor(player1.getColor()));
        player2.setScore(board.getNumberOfDisksForColor(player2.getColor()));
    }

    protected void changeTurn() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
        calculatePlayerPossibleMoves();
    }

    public boolean isOver(){
        return  isQuit || board.isFull();
    }

    public Player getWinner() {
        if(!isOver() || player1.getScore() == player2.getScore()){
            return null;
        }
        return player1.getScore() > player2.getScore() ? player1 : player2;
    }

    public void play() {
        NotificationsManager notificationsManager = player1.gameManager.compose(player2.gameManager);
        notificationsManager.initialize(this);
        while (!isOver()) {
            notificationsManager.startTurn(currentPlayer);
            if (currentPlayer.isInStall()) {
                notificationsManager.skipTurn();
            } else {
                currentPlayer.gameManager.suggestMoves(allowedMovesForCurrentPlayer.keySet());
                while (true) {
                    try {
                        Coordinate coordinateOfDesiredMove = currentPlayer.gameManager.getMoveFromPlayer();
                        if (coordinateOfDesiredMove == null) {
                            isQuit = true;
                            break;
                        }
                        makeMove(coordinateOfDesiredMove);
                        notificationsManager.updateGame(this);
                        break;
                    } catch (IllegalArgumentException e) {
                        currentPlayer.gameManager.illegalMove("Invalid move, please choose another one");
                    }
                }
            }
            changeTurn();
        }
        notificationsManager.showWinner(getWinner());
    }
}
