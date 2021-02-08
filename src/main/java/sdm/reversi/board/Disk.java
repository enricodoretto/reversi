package sdm.reversi.board;

import java.io.Serializable;

public class Disk implements Serializable {

    public enum Color {
        BLACK(java.awt.Color.BLACK), WHITE(java.awt.Color.WHITE);
        private final java.awt.Color graphicalColor;

        Color(java.awt.Color graphicalColor) {
            this.graphicalColor = graphicalColor;
        }

        public java.awt.Color getGraphicalColor() {
            return graphicalColor;
        }
    }

    public Disk(Color sideUp) {
        this.sideUp = sideUp;
    }

    private Color sideUp;

    public Color getSideUp() {
        return sideUp;
    }

    public void flip() {
        if (sideUp == Color.BLACK) {
            sideUp = Color.WHITE;
        } else sideUp = Color.BLACK;
    }

    @Override
    public String toString() {
        return String.valueOf(sideUp.toString().charAt(0));
    }
}
