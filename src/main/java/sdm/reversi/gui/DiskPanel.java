package sdm.reversi.gui;

import sdm.reversi.Coordinate;
import sdm.reversi.Disk;

import javax.swing.*;
import java.awt.*;

public class DiskPanel extends JPanel {

    private static int RADIUS;
    private Color color;
    private boolean show;

    public static void setRadius(int radius){
        RADIUS = radius;
    }

    public DiskPanel(Disk.Color diskColor) {
        setColor(diskColor);
    }

    public void suggest(Color color){
        show = true;
        this.color = new Color(color.getRed(), color.getGreen(), color.getBlue(), 70);
    }

    public void setColor(Disk.Color diskColor) {
        if (diskColor == null){
            show=false;
            return;
        }
        show = true;
        color = diskColor.getGraphicalColor();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (show) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(color);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.fillOval(RADIUS, RADIUS, RADIUS*2, RADIUS*2);
        }
    }
}