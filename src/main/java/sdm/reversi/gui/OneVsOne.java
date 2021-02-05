package sdm.reversi.gui;

import sdm.reversi.game.Game;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class OneVsOne extends DraggableFrame {
    private final JTextField player1NameInput, player2NameInput;

    public OneVsOne() {
        TitleBar titleBar = TitleBar.TitleBarBuilder.createTitleBar(this).withBackButton().build();
        add(titleBar.getTitleBar(), BorderLayout.NORTH);

        JPanel container = new JPanel(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;

        add(container, BorderLayout.CENTER);

        JPanel playersNamesContainer = new JPanel(new GridLayout(2, 2, 70, 15));
        playersNamesContainer.setBorder(new EmptyBorder(0, 0, 17, 0));
        container.add(playersNamesContainer, gridBagConstraints);
        JLabel player1NameLabel = new JLabel("Name Player 1:");
        player1NameInput = new JTextField();
        JLabel player2NameLabel = new JLabel("Name Player 2:");
        player2NameInput = new JTextField();
        playersNamesContainer.add(player1NameLabel);
        playersNamesContainer.add(player2NameLabel);
        playersNamesContainer.add(player1NameInput);
        playersNamesContainer.add(player2NameInput);
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
                gameBuilder = Game.GameBuilder.GUIGameBuilder(player1NameInput.getText()).withOpponent(player2NameInput.getText()).withBoardSize(boardSize);
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
