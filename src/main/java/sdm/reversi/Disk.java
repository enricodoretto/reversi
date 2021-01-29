package sdm.reversi;

import java.awt.*;

public class Disk {

    public enum Color{
        BLACK(java.awt.Color.BLACK), WHITE(java.awt.Color.WHITE);
        private java.awt.Color graphicalColor;

        Color(java.awt.Color graphicalColor) {
            this.graphicalColor = graphicalColor;
        }

        public java.awt.Color getGraphicalColor() {
            return graphicalColor;
        }
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
