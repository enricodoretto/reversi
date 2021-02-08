package sdm.reversi.game;

import sdm.reversi.*;
import sdm.reversi.board.Board;
import sdm.reversi.manager.*;
import sdm.reversi.player.ComputerPlayer;
import sdm.reversi.player.Player;
import sdm.reversi.player.RemotePlayer;

import javax.swing.*;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.*;

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

        public static GameBuilder CLIGameBuilder(String player1Name, Scanner scanner) {
            return new GameBuilder(player1Name, new CLIManager(scanner));
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
            if(board == null) throw new IllegalArgumentException("Board not specified");
            return new ReversiGame(this);
        }

        public Game buildOthello(){
            if(player2 == null) throw new IllegalArgumentException("Player 2 not specified");
            if(board == null) throw new IllegalArgumentException("Board not specified");
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

    protected void calculatePlayerPossibleMoves() {
        Map<Coordinate, Set<Coordinate>> validCoordinates = board.getValidMoves(currentPlayer.getColor());
        if (validCoordinates.size() == 0) {
            allowedMovesForCurrentPlayer = null;
            currentPlayer.setInStall(true);
        } else {
            allowedMovesForCurrentPlayer = validCoordinates;
            currentPlayer.setInStall(false);
        }
    }

    protected void makeMove(Coordinate coordinate) {
        if (!isValidMove(coordinate)) {
            throw new IllegalArgumentException("Illegal move");
        }
        board.makeMove(currentPlayer.getColor(), coordinate, allowedMovesForCurrentPlayer.get(coordinate));
        updatePlayersScore();
        numberOfMoves ++;
    }

    protected boolean isValidMove(Coordinate coordinate) {
        return allowedMovesForCurrentPlayer.containsKey(coordinate);
    }

    protected void changeTurn() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
        calculatePlayerPossibleMoves();
    }

    protected void updatePlayersScore(){
        player1.setScore(board.getNumberOfDisksForColor(player1.getColor()));
        player2.setScore(board.getNumberOfDisksForColor(player2.getColor()));
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
        if(player1.getNotificationsManager() instanceof GUIManager){
            SwingWorker<Void,Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() {
                    play(notificationsManager);
                    return null;
                }
            };
            worker.execute();
        }else{
            play(notificationsManager);
        }
    }

    private void play(NotificationsManager notificationsManager){
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
                        notificationsManager.updateGame(Game.this);
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
