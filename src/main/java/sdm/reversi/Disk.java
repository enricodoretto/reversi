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

    public void flip(){
        if(sideUp == Color.BLACK){
            sideUp = Color.WHITE;
        }
        else sideUp = Color.BLACK;
    }

    @Override
    public String toString() {
        return String.valueOf(sideUp.toString().charAt(0));
    }
}
