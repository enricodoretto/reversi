package sdm.reversi.gui;

import sdm.reversi.Client;
import sdm.reversi.game.Game;
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
    private final JLabel IPAddressThisPC = new JLabel();
    private final JTextField IPAddressHostPC = new JTextField("Insert IP Address");
    private final ButtonGroup group;
    private final JTextField playerName;

    public Online() {
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
        JLabel playerNameLabel = new JLabel("Player name:");
        playerName = new JTextField();
        namePlayersContainer.add(playerNameLabel);
        namePlayersContainer.add(playerName);
        ++c.gridy;

        BoardConfigurationGUI boardConfigurationGUI = new BoardConfigurationGUI();
        container.add(boardConfigurationGUI.getBoardConfiguration(), c);

        c.gridy += 2;
        JPanel radioPanel = new JPanel(new GridLayout(2, 2, 70, 7));
        JRadioButton chooseHost = new JRadioButton("Host");
        chooseHost.setActionCommand("host");
        JRadioButton chooseClient = new JRadioButton("Client");
        chooseClient.setActionCommand("client");
        group = new ButtonGroup();
        group.add(chooseHost);
        group.add(chooseClient);

        radioPanel.add(chooseHost);
        radioPanel.add(chooseClient);
        container.add(radioPanel, c);

        try {
            String hostIP = Inet4Address.getLocalHost().getHostAddress();
            IPAddressThisPC.setText(hostIP);
            radioPanel.add(IPAddressThisPC);

        } catch (UnknownHostException uhe) {
            System.out.println("Cannot take own IP address");
        }
        radioPanel.add(IPAddressHostPC);

        ++c.gridy;
        JPanel playButtonContainer = new JPanel();
        container.add(playButtonContainer, c);
        JButton playButton = new JButton("PLAY!");
        playButtonContainer.add(playButton);
        add(container, BorderLayout.CENTER);

        chooseHost.addActionListener(e -> {
            IPAddressThisPC.setEnabled(true);
            IPAddressHostPC.setEnabled(false);
            BoardConfigurationGUI.getAvailableDimension().setEnabled(true);
            BoardConfigurationGUI.getAvailableGameType().setEnabled(true);
        });
        chooseClient.addActionListener(e -> {
            IPAddressHostPC.setEnabled(true);
            IPAddressThisPC.setEnabled(false);
            BoardConfigurationGUI.getAvailableDimension().setEnabled(false);
            BoardConfigurationGUI.getAvailableGameType().setEnabled(false);
        });
        playButton.addActionListener(e -> {
            if (group.getSelection() == null) {
                JOptionPane.showMessageDialog(Online.this, "Please, make a selection",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
            } else if (group.getSelection().getActionCommand().equals("client")
                    && !isIp(IPAddressHostPC.getText())) {
                JOptionPane.showMessageDialog(Online.this, "Please, write a valid IP number",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
            } else if (playerName.getText().isEmpty()) {
                JOptionPane.showMessageDialog(Online.this, "Please, insert your name",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                if (group.getSelection().getActionCommand().equals("host")) {
                    setVisible(false);
                    int dimension = boardConfigurationGUI.getSelectedDimension();
                    int gameType = boardConfigurationGUI.getSelectedGame();
                    Thread thread;
                    if (gameType == 1) {
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                Game game = null;
                                try {
                                    game = Game.GameBuilder.GUIGameBuilder(playerName.getText()).withRemoteOpponent().withBoardSize(dimension).buildOthello();
                                } catch (IOException exception) {
                                    exception.printStackTrace();
                                }
                                game.play();
                            }
                        });
                    }
                } else {

                }
            }
        });


        IPAddressHostPC.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                IPAddressHostPC.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {

            }

        });

        setSize(500, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //center of the screen
        setUndecorated(true);
        setResizable(false);
        setVisible(true);
    }

    public static boolean isIp(String string) {
        String[] parts = string.split("\\.", -1);
        return parts.length == 4
                && Arrays.stream(parts)
                .map(Integer::parseInt)
                .filter(i -> i <= 255 && i >= 0)
                .count() == 4;
    }
}
