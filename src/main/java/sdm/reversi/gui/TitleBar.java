package sdm.reversi.gui;

import sdm.reversi.launcher.GUILauncher;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class TitleBar implements MouseListener {
    private JFrame frame;
    private JPanel titleBar;

    public static class TitleBarBuilder {
        private final JPanel titleBar;
        private final JFrame frame;
        private JPanel titleBarRight;

        private TitleBarBuilder(JFrame frame) {
            this.frame = frame;
            titleBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            titleBar.setBackground(Color.LIGHT_GRAY);
            titleBarRight = new TitleBar(frame).createTitleBarRight();
        }

        public static TitleBarBuilder createTitleBar(JFrame frame) {
            return new TitleBarBuilder(frame);
        }

        public TitleBarBuilder withBackButton() {
            titleBar.setLayout(new GridLayout(1, 2));
            JPanel titleBarLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
            titleBarLeft.setBackground(Color.LIGHT_GRAY);
            titleBar.add(titleBarLeft);
            titleBarLeft.setSize(frame.getWidth() / 2, 10);
            final JLabel back = new JLabel("\u2190 Back");
            back.addMouseListener(new TitleBar(frame));
            back.setSize(10, 5);
            back.setFont(new Font("Tahoma", Font.BOLD, 15));
            titleBarLeft.add(back);
            return this;
        }

        public TitleBar build() {
            titleBar.add(titleBarRight);
            return new TitleBar(this);
        }
    }

    public TitleBar(final JFrame frame) {
        this.frame = frame;
    }

    public TitleBar(TitleBarBuilder titleBarBuilder) {
        this.frame = titleBarBuilder.frame;
        this.titleBar = titleBarBuilder.titleBar;
    }

    public JPanel getTitleBar() {
        return titleBar;
    }

    private JPanel createTitleBarRight() {
        JPanel titleBarRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        titleBarRight.setBackground(Color.LIGHT_GRAY);
        final JLabel help = new JLabel("?");
        final JLabel exit = new JLabel("X");
        exit.addMouseListener(this);
        help.addMouseListener(this);
        exit.setBorder(new EmptyBorder(0, 10, 0, 10));
        exit.setSize(10, 5);
        exit.setFont(new Font("Tahoma", Font.BOLD, 15));
        help.setBorder(new EmptyBorder(0, 10, 0, 10));
        help.setSize(10, 5);
        help.setFont(new Font("Tahoma", Font.BOLD, 15));
        titleBarRight.add(help);
        titleBarRight.add(exit);
        return titleBarRight;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JLabel clickedLabel = (JLabel) e.getSource();
        switch (clickedLabel.getText()) {
            case "X":
                System.exit(0);
                break;
            case "?":
                try {
                    String url = "https://github.com/enricodoretto/reversi#reversi-";
                    Desktop dt = Desktop.getDesktop();
                    URI uri = new URI(url);
                    dt.browse(uri.resolve(uri));
                } catch (URISyntaxException | IOException ex) {
                    ex.printStackTrace();
                }
                break;
            case "\u2190 Back":
                GUILauncher.launch();
                frame.setVisible(false);
                break;
            default:
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        JLabel overLabel = (JLabel) e.getSource();
        overLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        if (overLabel.getText().equals("X")) {
            overLabel.setOpaque(true);
            overLabel.setBackground(Color.RED);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JLabel overLabel = (JLabel) e.getSource();
        if (overLabel.getText().equals("X")) {
            overLabel.setOpaque(true);
            overLabel.setBackground(Color.LIGHT_GRAY);
        }
    }
}