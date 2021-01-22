package sdm.reversi;

import java.util.Set;

public abstract class Game {
    private final Player player1;
    private final Player player2;
    protected Board board;
    protected Player currentPlayer;

    public Game(String player1Name, String player2Name) throws IllegalArgumentException {
        if (player1Name.equals(player2Name)) throw new IllegalArgumentException();
        this.player1 = new Player(player1Name, Disk.Color.BLACK);
        this.player2 = new Player(player2Name, Disk.Color.WHITE);
        this.currentPlayer = player1;
    }

    /*public boolean makeMove(Coordinate coordinate) {
        Set<Coordinate> coordinatesOfDisksToFlip = getDisksToFlip(coordinate);
        if (coordinatesOfDisksToFlip!=null) {
            board.putDisk(currentPlayer.getColor(), coordinate);
            for(Coordinate coordinateOfDiskToFlip : coordinatesOfDisksToFlip){
                board.flipDisk(coordinateOfDiskToFlip);
            }
            currentPlayer = (currentPlayer == player1) ? player2 : player1;
            return true;
        } else return false;
    }*/

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

    public abstract boolean isValidMove(Coordinate coordinate);

    public abstract Set<Coordinate> getDisksToFlip(Coordinate coordinate);
}
