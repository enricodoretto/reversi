package sdm.reversi.gui;

import sdm.reversi.Coordinate;
import sdm.reversi.Disk;

import javax.swing.*;
import java.awt.*;

public class DiskPanel extends JPanel {

    private final int radius;
    private Color color;
    private final int x, y;
    private boolean show;

    public DiskPanel(int radius, Coordinate coordinate, Disk.Color diskColor, int cellCenter) {
        this.radius = radius;
        x = coordinate.getRow() + cellCenter / 2 - radius / 2;
        y = coordinate.getColumn() + cellCenter / 2 - radius / 2;
        setColor(diskColor);
    }

    /*public void suggest(){
        show = true;
        color = Color.RED;
    }*/

    public void setColor(Disk.Color diskColor) {
        if (diskColor == null){
            show=false;
            return;
        }
        show = true;
        if (diskColor == Disk.Color.BLACK) {
            color = Color.BLACK;
        } else {
            color = Color.WHITE;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (show) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(color);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.fillOval(x, y, radius, radius);
        }
    }
}