package sdm.reversi.gui;

import sdm.reversi.Client;
import sdm.reversi.game.Game;
import sdm.reversi.manager.CLIManager;
import sdm.reversi.manager.GUIManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class Online extends DraggableFrame {
    private final JTextField playerNameInput;

    public Online() {
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
        JLabel playerNameLabel = new JLabel("Player name:");
        playerNameInput = new JTextField();
        playerNameContainer.add(playerNameLabel);
        playerNameContainer.add(playerNameInput);
        gridBagConstraints.gridy++;

        GUIBoardConfiguration guiBoardConfiguration = new GUIBoardConfiguration();
        container.add(guiBoardConfiguration.getBoardConfiguration(), gridBagConstraints);

        gridBagConstraints.gridy++;
        JPanel radioPanel = new JPanel(new GridLayout(2, 2, 70, 7));
        JRadioButton hostRadioButton = new JRadioButton("Host");
        hostRadioButton.setActionCommand("host");
        JRadioButton clientRadioButton = new JRadioButton("Client");
        clientRadioButton.setActionCommand("client");
        ButtonGroup group = new ButtonGroup();
        group.add(hostRadioButton);
        group.add(clientRadioButton);

        radioPanel.add(hostRadioButton);
        radioPanel.add(clientRadioButton);
        container.add(radioPanel, gridBagConstraints);

        JLabel localIPAddressLabel = new JLabel();
        try {
            localIPAddressLabel.setText(Inet4Address.getLocalHost().getHostAddress());
            radioPanel.add(localIPAddressLabel);
        } catch (UnknownHostException uhe) {
            JOptionPane.showMessageDialog(this, "Cannot take own IP address");
        }

        JTextField serverIPAddressInput = new JTextField("Insert Server IP Address");
        radioPanel.add(serverIPAddressInput);

        gridBagConstraints.gridy++;
        JPanel playButtonContainer = new JPanel();
        container.add(playButtonContainer, gridBagConstraints);
        JButton playButton = new JButton("PLAY!");
        playButtonContainer.add(playButton);
        add(container, BorderLayout.CENTER);

        hostRadioButton.addActionListener(e -> {
            localIPAddressLabel.setEnabled(true);
            serverIPAddressInput.setEnabled(false);
            guiBoardConfiguration.enableInputs();
        });
        clientRadioButton.addActionListener(e -> {
            serverIPAddressInput.setEnabled(true);
            localIPAddressLabel.setEnabled(false);
            guiBoardConfiguration.disableInputs();
        });
        playButton.addActionListener(e -> {
            if (group.getSelection() == null) {
                JOptionPane.showMessageDialog(this, "Please, make a selection");
                return;
            }
            if (group.getSelection().getActionCommand().equals("client")) {
                try {
                    Client.connectAndPlay(playerNameInput.getText(), new GUIManager(), InetAddress.getByName(serverIPAddressInput.getText()));
                    dispose();
                } catch (IllegalArgumentException exception) {
                    JOptionPane.showMessageDialog(this, exception.getMessage());
                    return;
                } catch (UnknownHostException exception) {
                    JOptionPane.showMessageDialog(this, "Please, write a valid IP number");
                    return;
                }
            }
            if (group.getSelection().getActionCommand().equals("host")) {
                int boardSize = guiBoardConfiguration.getSelectedSize();
                int gameType = guiBoardConfiguration.getSelectedGame();
                Game.GameBuilder gameBuilder;
                try {
                    gameBuilder = Game.GameBuilder.GUIGameBuilder(playerNameInput.getText()).withRemoteOpponent().withBoardSize(boardSize);
                } catch (IllegalArgumentException | IOException exception) {
                    JOptionPane.showMessageDialog(this, exception.getMessage());
                    return;
                }
                if (gameType == 1) {
                    gameBuilder.buildOthello().play();
                } else {
                    gameBuilder.buildReversi().play();
                }
                dispose();
            }
        });


        serverIPAddressInput.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                serverIPAddressInput.setText("");
            }
            @Override
            public void focusLost(FocusEvent e) {
            }
        });

        setSize(500, 500);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setResizable(false);
        setVisible(true);
    }

}
