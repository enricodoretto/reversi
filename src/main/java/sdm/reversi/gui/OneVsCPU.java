package sdm.reversi.gui;
import sdm.reversi.game.Game;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class OneVsCPU extends DraggableFrame  {
    private final BoardConfigurationGUI boardConfigurationGUI;
    private final JTextField namePlayer1;

    public OneVsCPU() {
        TitleBar titleBar = TitleBar.TitleBarBuilder.createTitleBar(this).withBackButton().build();
        add(titleBar.getTitleBar(), BorderLayout.NORTH);

        JPanel container = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;

        add(container, BorderLayout.CENTER);

        JPanel namePlayersContainer = new JPanel(new GridLayout(2, 1, 70, 15));
        namePlayersContainer.setBorder(new EmptyBorder(0, 0, 17, 0));
        container.add(namePlayersContainer, c);
        JLabel player1 = new JLabel("Name Player 1:");
        namePlayer1 = new JTextField();
        namePlayersContainer.add(player1);
        namePlayersContainer.add(namePlayer1);
        ++c.gridy;

        boardConfigurationGUI = new BoardConfigurationGUI();
        container.add(boardConfigurationGUI.getBoardConfiguration(), c);

        ++c.gridy;
        JPanel playButtonContainer = new JPanel();
        container.add(playButtonContainer, c);
        JButton playButton = new JButton("PLAY!");
        playButtonContainer.add(playButton);
        playButton.addActionListener(e -> {
            if (namePlayer1.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Insert both player name");
            } else if (namePlayer1.getText().length() > 8) {
                JOptionPane.showMessageDialog(this, "One or more player name is too long");
            } else {
                setVisible(false);
                int dimension = boardConfigurationGUI.getSelectedDimension();
                Game game = Game.GameBuilder.GUIGameBuilder(namePlayer1.getText()).withCPUOpponent()
                        .withBoardSize(dimension).buildReversi();
                game.play();
            }
        });

        setSize(500, 500);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setResizable(false);
        setVisible(true);
    }
}
