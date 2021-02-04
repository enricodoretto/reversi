package sdm.reversi.gui;
import sdm.reversi.game.Game;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class OneVsOne extends DraggableFrame {
    private final JTextField namePlayer1, namePlayer2;


    public OneVsOne() {
        TitleBar titleBar = TitleBar.TitleBarBuilder.createTitleBar(this).withBackButton().build();
        add(titleBar.getTitleBar(), BorderLayout.NORTH);
        JPanel container = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;

        add(container, BorderLayout.CENTER);

        JPanel namePlayersContainer = new JPanel(new GridLayout(2, 2, 70, 15));
        namePlayersContainer.setBorder(new EmptyBorder(0, 0, 17, 0));
        container.add(namePlayersContainer, c);
        JLabel player1 = new JLabel("Name Player 1:");
        namePlayer1 = new JTextField();
        JLabel player2 = new JLabel("Name Player 2:");
        namePlayer2 = new JTextField();
        namePlayersContainer.add(player1);
        namePlayersContainer.add(player2);
        namePlayersContainer.add(namePlayer1);
        namePlayersContainer.add(namePlayer2);
        ++c.gridy;

        BoardConfigurationGUI boardConfigurationGUI = new BoardConfigurationGUI();
        container.add(boardConfigurationGUI.getBoardConfiguration(), c);

        ++c.gridy;
        JPanel playButtonContainer = new JPanel();
        container.add(playButtonContainer, c);
        JButton playButton = new JButton("PLAY!");
        playButtonContainer.add(playButton);
        playButton.addActionListener(e -> {
            //invece di fare questo controllo ci sta chiamare il costruttore del "back-end" e vedere se lancia l'eccezione
            if (namePlayer1.getText().isEmpty() || namePlayer2.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Insert both player name");
            } else if (namePlayer1.getText().equals(namePlayer2.getText())) {
                JOptionPane.showMessageDialog(this, "Name must be different");
            } else if (namePlayer1.getText().length() > 8 || namePlayer2.getText().length() > 8) {
                JOptionPane.showMessageDialog(this, "One or more player name is too long");
            } else {
                setVisible(false);
                int dimension = boardConfigurationGUI.getSelectedDimension();
                Game game = Game.GameBuilder.GUIGameBuilder(namePlayer1.getText()).withOpponent(namePlayer2.getText())
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
