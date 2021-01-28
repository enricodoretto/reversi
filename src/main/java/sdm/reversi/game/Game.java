package sdm.reversi.game;

import sdm.reversi.*;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Game implements Playable {
    protected final Player player1;
    protected final Player player2;
    protected Board board;
    protected Player currentPlayer;
    protected Map<Coordinate, Set<Coordinate>> allowedMovesForCurrentPlayer;
    protected int numberOfMoves;
    protected boolean isQuit;

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
        if (board.getNumberOfDisks() < 4) {
            throw new IllegalArgumentException();
        }
        numberOfMoves = (int)board.getNumberOfDisks();
    }

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
    };

    public Player getWinner() {
        if(!isOver() || player1.getScore() == player2.getScore()){
            return null;
        }
        return player1.getScore() > player2.getScore() ? player1 : player2;
    }

    @Override
    public Player play() {
        Scanner scanner = new Scanner(System.in);
        while (!isOver()) {
            System.out.printf("%s's turn: ", currentPlayer.getName());
            if (currentPlayer.isInStall()) {
                System.out.println("sorry you can make no moves!");
            } else {
                System.out.println(String.join(" ", allowedMovesForCurrentPlayer.keySet().stream().map(x -> x.toString()).sorted().collect(Collectors.toList())));
                while (true) {
                    try {
                        String coordinateOfDesiredMove = scanner.nextLine();
                        if(coordinateOfDesiredMove.equalsIgnoreCase("q")){
                            isQuit = true;
                            break;
                        }
                        makeMove(new Coordinate(coordinateOfDesiredMove));
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid move, please write another one");
                    }
                }
            }
            changeTurn();
        }
        return getWinner();
    }
}
