package sdm.reversi.gui;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.net.*;
import java.util.Arrays;

public class Online extends JFrame {
    private final JLabel IPAddressThisPC = new JLabel();
    private final JTextField IPAddressHostPC = new JTextField("Insert IP Address");
    private final JComboBox<String> availableDimension, availableGameType;
    private ButtonGroup group;
    private final String[] dimensionOfBoard = {"4x4", "6x6", "8x8", "10x10", "12x12", "14x14",
            "16x16", "18x18", "20x20", "22x22", "24x24", "26x26"};
    private JTextField playerName;
    private final String[] gameType = {"Othello", "Reversi"};

    public Online() {
        new DraggableFrame(this);
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

        JPanel boardConfigurationContainer = new JPanel(new GridLayout(4, 2, 70, 7));
        boardConfigurationContainer.setBorder(new EmptyBorder(0, 0, 25, 0));
        container.add(boardConfigurationContainer, c);
        JLabel dimension = new JLabel("Board Size: ", JLabel.CENTER);

        availableDimension = new JComboBox<>(dimensionOfBoard);
        JLabel typeOfGame = new JLabel("Select Game: ",JLabel.CENTER);
        availableGameType = new JComboBox<>(gameType);
        availableDimension.setSelectedIndex(2);
        boardConfigurationContainer.add(dimension);
        boardConfigurationContainer.add(typeOfGame);
        boardConfigurationContainer.add(availableDimension);
        boardConfigurationContainer.add(availableGameType);


        c.gridy++;

        JRadioButton chooseHost = new JRadioButton("Host");
        chooseHost.setActionCommand("host");
        JRadioButton chooseClient = new JRadioButton("Client");
        chooseClient.setActionCommand("client");
        group = new ButtonGroup();
        group.add(chooseHost);
        group.add(chooseClient);

        boardConfigurationContainer.add(chooseHost);
        boardConfigurationContainer.add(chooseClient);

        try {
            String hostIP = Inet4Address.getLocalHost().getHostAddress();
            IPAddressThisPC.setText(hostIP);
            boardConfigurationContainer.add(IPAddressThisPC);

        } catch (UnknownHostException uhe) {
            System.out.println("Cannot take own IP address");
        }
        boardConfigurationContainer.add(IPAddressHostPC);
        ++c.gridy;
        JPanel playButtonContainer = new JPanel();
        container.add(playButtonContainer, c);
        JButton playButton = new JButton("PLAY!");
        playButtonContainer.add(playButton);
        add(container, BorderLayout.CENTER);

        chooseHost.addActionListener(manageWhichElementMustBeEnabled);
        chooseClient.addActionListener(manageWhichElementMustBeEnabled);
        playButton.addActionListener(controlRegularityOfSelectionAndPlay);


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

    private final ActionListener manageWhichElementMustBeEnabled = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JRadioButton pressedRadioButton = (JRadioButton) e.getSource();
            if (pressedRadioButton.getText().equals("Host")) {
                IPAddressThisPC.setEnabled(true);
                IPAddressHostPC.setEnabled(false);
                availableDimension.setEnabled(true);
                availableGameType.setEnabled(true);
            } else {
                IPAddressHostPC.setEnabled(true);
                IPAddressThisPC.setEnabled(false);
                availableDimension.setEnabled(false);
                availableGameType.setEnabled(false);
            }
        }
    };

    private final ActionListener controlRegularityOfSelectionAndPlay = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (group.getSelection() == null) {
                JOptionPane.showMessageDialog(Online.this, "Please, make a selection",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
            } else if (group.getSelection().getActionCommand().equals("client")
                    && !isIp(IPAddressHostPC.getText())) {
                JOptionPane.showMessageDialog(Online.this , "Please, write a valid IP number",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
            } else if(playerName.getText().isEmpty()){
                JOptionPane.showMessageDialog(Online.this , "Please, insert your name",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    };

    public static boolean isIp(String string) {
        String[] parts = string.split("\\.", -1);
        return parts.length == 4 // 4 parts
                && Arrays.stream(parts)
                .map(Integer::parseInt)
                .filter(i -> i <= 255 && i >= 0) // Must be inside [0, 255]
                .count() == 4; // 4 numerical parts inside [0, 255]
    }
}
