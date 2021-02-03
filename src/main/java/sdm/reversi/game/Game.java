package sdm.reversi.game;

import sdm.reversi.*;
import sdm.reversi.manager.CLIManager;
import sdm.reversi.manager.GUIManager;
import sdm.reversi.manager.GameManager;
import sdm.reversi.manager.NotificationsManager;
import sdm.reversi.player.ComputerPlayer;
import sdm.reversi.player.Player;
import sdm.reversi.player.RemotePlayer;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Game implements Serializable {
    protected final Player player1;
    protected Player player2;
    protected Board board;
    protected Player currentPlayer;
    protected Map<Coordinate, Set<Coordinate>> allowedMovesForCurrentPlayer;
    protected int numberOfMoves;
    protected boolean isQuit;

    public static class GameBuilder {
        private final Player player1;
        private Player player2;
        private Board board;
        private final GameManager gameManager;

        private GameBuilder(String player1Name, GameManager gameManager){
            if(player1Name.isEmpty()) throw new IllegalArgumentException("Player 1 can't have empty name");
            player1 = new Player(player1Name, Disk.Color.BLACK, gameManager);
            this.gameManager = gameManager;
        }

        public static GameBuilder CLIGameBuilder(String player1Name) {
            return new GameBuilder(player1Name, new CLIManager());
        }

        public static GameBuilder GUIGameBuilder(String player1Name) {
            return new GameBuilder(player1Name, new GUIManager());
        }

        public GameBuilder withOpponent(String player2Name) {
            if(player2Name.isEmpty()) throw new IllegalArgumentException("Player 2 can't have empty name");
            if (player1.getName().equals(player2Name)) throw new IllegalArgumentException("The two players must have different names");
            if (player2 != null) throw new IllegalArgumentException("Player 2 already specified");
            player2 = new Player(player2Name, Disk.Color.WHITE, gameManager);
            return this;
        }

        public GameBuilder withCPUOpponent() {
            if (player2 != null) throw new IllegalArgumentException("Player 2 already specified");
            player2 = new ComputerPlayer(Disk.Color.WHITE);
            return this;
        }

        public GameBuilder withRemoteOpponent() throws IOException {
            if (player2 != null) throw new IllegalArgumentException("Player 2 already specified");
            player2 = new RemotePlayer(Disk.Color.WHITE);
            return this;
        }

        public GameBuilder withBoardSize(int boardSize) {
            if (board != null) throw new IllegalArgumentException("Board already specified");
            board = new Board(boardSize);
            return this;
        }

        public GameBuilder withCustomBoard(URL boardFileURL) throws IOException {
            if (board != null) throw new IllegalArgumentException("Board already specified");
            board = new Board(boardFileURL);
            if (board.getNumberOfDisks() < 4) {
                throw new IllegalArgumentException("Custom board needs to have at least four disks");
            }
            return this;
        }

        public Game buildReversi(){
            if(player2 == null) throw new IllegalArgumentException("Player 2 not specified");
            if(board == null) board = new Board();
            return new ReversiGame(this);
        }

        public Game buildOthello(){
            if(player2 == null) throw new IllegalArgumentException("Player 2 not specified");
            if(board == null) board = new Board();
            return new OthelloGame(this);
        }
    }

    protected Game(GameBuilder gameBuilder){
        player1 = gameBuilder.player1;
        player2 = gameBuilder.player2;
        currentPlayer = player1;
        board = gameBuilder.board;
        numberOfMoves = (int) board.getNumberOfDisks();
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
        Set<Coordinate> disksToFlip = Stream.of(ShiftDirection.values()).parallel()
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
            throw new IllegalArgumentException("Illegal move");
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
        NotificationsManager notificationsManager = player1.getNotificationsManager().compose(player2.getNotificationsManager());
        notificationsManager.initialize(this);
        while (!isOver()) {
            notificationsManager.startTurn(currentPlayer);
            if (currentPlayer.isInStall()) {
                notificationsManager.skipTurn();
            } else {
                currentPlayer.suggestMoves(allowedMovesForCurrentPlayer.keySet());
                while (true) {
                    try {
                        Coordinate coordinateOfDesiredMove = currentPlayer.getMoveFromPlayer();
                        if (coordinateOfDesiredMove == null) {
                            isQuit = true;
                            break;
                        }
                        makeMove(coordinateOfDesiredMove);
                        notificationsManager.updateGame(this);
                        break;
                    } catch (IllegalArgumentException e) {
                        currentPlayer.illegalMove();
                    }
                }
            }
            changeTurn();
        }
        notificationsManager.showWinner(getWinner());
    }
}
