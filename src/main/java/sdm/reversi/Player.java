package sdm.reversi;

import java.util.Objects;

public class Player {
    private String name;
    private Disk.Color color;

    public Player(String name, Disk.Color color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public Disk.Color getColor() {
        return color;
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
