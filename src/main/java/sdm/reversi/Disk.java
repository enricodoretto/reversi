package sdm.reversi;

public class Disk {

    public enum Color{
        BLACK, WHITE
    }

    private Color sideUp;

    public Disk(Color sideUp) {
        this.sideUp = sideUp;
    }

    public Color getSideUp(){
        return sideUp;
    }

}
