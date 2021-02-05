package sdm.reversi.gui;
import sdm.reversi.game.Game;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class OneVsCPU extends DraggableFrame  {
    private final JTextField playerNameInput;

    public OneVsCPU() {
        TitleBar titleBar = TitleBar.TitleBarBuilder.createTitleBar(this).withBackButton().build();
        add(titleBar.getTitleBar(), BorderLayout.NORTH);

        JPanel container = new JPanel(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;

        add(container, BorderLayout.CENTER);

        JPanel playerNameContainer = new JPanel(new GridLayout(2, 1, 70, 15));
        playerNameContainer.setBorder(new EmptyBorder(0, 0, 17, 0));
        container.add(playerNameContainer, gridBagConstraints);
        JLabel playerNameLabel = new JLabel("Name Player 1:");
        playerNameInput = new JTextField();
        playerNameContainer.add(playerNameLabel);
        playerNameContainer.add(playerNameInput);
        gridBagConstraints.gridy++;

        GUIBoardConfiguration guiBoardConfiguration = new GUIBoardConfiguration();
        container.add(guiBoardConfiguration.getBoardConfiguration(), gridBagConstraints);

        gridBagConstraints.gridy++;
        JPanel playButtonContainer = new JPanel();
        container.add(playButtonContainer, gridBagConstraints);
        JButton playButton = new JButton("PLAY!");
        playButtonContainer.add(playButton);
        playButton.addActionListener(e -> {
            int boardSize = guiBoardConfiguration.getSelectedSize();
            int gameType = guiBoardConfiguration.getSelectedGame();
            Game.GameBuilder gameBuilder;
            try {
                gameBuilder = Game.GameBuilder.GUIGameBuilder(playerNameInput.getText()).withCPUOpponent().withBoardSize(boardSize);
            } catch (IllegalArgumentException exception) {
                JOptionPane.showMessageDialog(this, exception.getMessage());
                return;
            }
            if (gameType == 1) {
                gameBuilder.buildOthello().play();
            } else {
                gameBuilder.buildReversi().play();
            }
            dispose();
        });

        setSize(500, 500);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setResizable(false);
        setVisible(true);
    }
}
