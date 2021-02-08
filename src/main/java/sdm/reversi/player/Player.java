package sdm.reversi.player;

import sdm.reversi.board.Coordinate;
import sdm.reversi.board.Disk;
import sdm.reversi.manager.ActionManager;
import sdm.reversi.manager.CLIManager;
import sdm.reversi.manager.GameManager;
import sdm.reversi.manager.NotificationsManager;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

public class Player implements Serializable, ActionManager {
    private final String name;
    private final Disk.Color color;
    private boolean inStall;
    private int score;
    private transient final GameManager gameManager;

    public Player(String name, Disk.Color color, GameManager gameManager) {
        this.name = name;
        this.color = color;
        this.gameManager = gameManager;
    }

    public Player(String name, Disk.Color color) {
        this(name, color, new CLIManager());
    }

    public String getName() {
        return name;
    }

    public Disk.Color getColor() {
        return color;
    }

    public boolean isInStall() {
        return inStall;
    }

    public void setInStall(boolean inStall) {
        this.inStall = inStall;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public NotificationsManager getNotificationsManager() {
        return gameManager;
    }

    @Override
    public void suggestMoves(Collection<Coordinate> moves) {
        gameManager.suggestMoves(moves);
    }

    @Override
    public Coordinate getMoveFromPlayer() {
        return gameManager.getMoveFromPlayer();
    }

    @Override
    public void illegalMove() {
        gameManager.illegalMove();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name) &&
                color == player.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, color);
    }
}
