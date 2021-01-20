package sdm.reversi;

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


}
