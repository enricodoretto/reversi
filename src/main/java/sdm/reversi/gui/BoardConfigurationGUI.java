package sdm.reversi.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Objects;

public class BoardConfigurationGUI {
    private final static String[] dimensionOfBoard = {"4x4", "6x6", "8x8", "10x10", "12x12", "14x14",
            "16x16", "18x18", "20x20", "22x22", "24x24", "26x26"};

    private final static String[] gameType = {"Othello", "Reversi"};

    private static JComboBox<String> availableDimension, availableGameType;

    private final JPanel boardConfigurationContainer;

    public BoardConfigurationGUI(){
        boardConfigurationContainer = new JPanel(new GridLayout(2, 2, 70, 7));
        boardConfigurationContainer.setBorder(new EmptyBorder(0, 0, 25, 0));
        JLabel dimension = new JLabel("Board Size: ");
        availableDimension = new JComboBox<>(dimensionOfBoard);
        JLabel typeOfGame = new JLabel("Select Game: ");
        availableGameType = new JComboBox<>(gameType);
        availableDimension.setSelectedIndex(2);
        boardConfigurationContainer.add(dimension);
        boardConfigurationContainer.add(typeOfGame);
        boardConfigurationContainer.add(availableDimension);
        boardConfigurationContainer.add(availableGameType);
    }

    public JPanel getBoardConfiguration(){
        return boardConfigurationContainer;
    }

    public int getSelectedDimension(){
        return Integer.parseInt(Objects.requireNonNull(availableDimension.getSelectedItem()).toString().split("x")[0]);
    }

    public int getSelectedGame(){
        return (availableGameType.getSelectedItem().equals("Othello")) ? 1 : 2;
    }

    public static JComboBox<String> getAvailableDimension() {
        return availableDimension;
    }

    public static JComboBox<String> getAvailableGameType() {
        return availableGameType;
    }
}
