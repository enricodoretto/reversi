package sdm.reversi.launcher;

import sdm.reversi.gui.*;

import javax.swing.*;
import java.awt.*;

public class GUILauncher{
    private static JFrame frame;
    
    public static void launch(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createGUI();
            }
        });
    }

    private static void createGUI(){
        frame = new DraggableFrame();
        TitleBar titleBar = TitleBar.TitleBarBuilder.createTitleBar(frame).build();
        frame.add(titleBar.getTitleBar(), BorderLayout.NORTH);

        JPanel container = new JPanel(new GridBagLayout());
        frame.add(container, BorderLayout.CENTER);
        JPanel buttonMenu = new JPanel(new GridLayout(1, 3, 5, 10));
        JButton oneVsOne = new JButton("1vs1");
        JButton oneVsCPU = new JButton("vsComputer");
        JButton online = new JButton("Online");
        buttonMenu.add(oneVsOne);
        buttonMenu.add(oneVsCPU);
        buttonMenu.add(online);
        container.add(buttonMenu);
        oneVsOne.addActionListener(e -> {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new OneVsOne();
                }
            });

            frame.setVisible(false);
        });
        oneVsCPU.addActionListener(e -> {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new OneVsCPU();
                }
            });

            frame.setVisible(false);
        });
        online.addActionListener(e -> {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new Online();
                }
            });
            frame.setVisible(false);
        });

        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
