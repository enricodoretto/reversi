package sdm.reversi.launcher;

import sdm.reversi.gui.*;

import javax.swing.*;
import java.awt.*;

public class GUILauncher{
    private static JFrame frame;
    
    public static void launch(){
        SwingUtilities.invokeLater(GUILauncher::createGUI);
    }

    private static void createGUI(){
        frame = new DraggableFrame();
        TitleBar titleBar = TitleBar.TitleBarBuilder.createTitleBar(frame).build();
        frame.add(titleBar.getTitleBar(), BorderLayout.NORTH);

        JPanel container = new JPanel(new GridBagLayout());
        frame.add(container, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 5, 10));
        JButton oneVsOne = new JButton("1vs1");
        JButton oneVsCPU = new JButton("vsComputer");
        JButton online = new JButton("Online");
        buttonPanel.add(oneVsOne);
        buttonPanel.add(oneVsCPU);
        buttonPanel.add(online);
        container.add(buttonPanel);
        oneVsOne.addActionListener(e -> {
            new OneVsOne();
            frame.dispose();
        });
        oneVsCPU.addActionListener(e -> {
            new OneVsCPU();
            frame.dispose();
        });
        online.addActionListener(e -> {
            new Online();
            frame.dispose();
        });

        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("src/icon/reversi_othello_icon.png"));
        frame.setVisible(true);
    }
}
