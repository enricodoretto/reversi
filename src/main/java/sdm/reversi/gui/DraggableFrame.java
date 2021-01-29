package sdm.reversi.gui;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class DraggableFrame implements MouseListener, MouseMotionListener {
    private int  pressedX, pressedY;
    private final JFrame frame;

    public DraggableFrame(final JFrame frame) {
        this.frame = frame;
        this.frame.addMouseListener(this);
        this.frame.addMouseMotionListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        pressedX = e.getX();
        pressedY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int x = e.getXOnScreen();
        int y = e.getYOnScreen();
        // Move frame by the mouse delta
        frame.setLocation(x  - pressedX ,
                y  - pressedY);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
}
