package sdm.reversi.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Objects;

public class GUIBoardConfiguration {
    private final static String[] boardSize = {"4x4", "6x6", "8x8", "10x10", "12x12", "14x14",
            "16x16", "18x18", "20x20", "22x22", "24x24", "26x26"};

    private final static String[] gameType = {"Othello", "Reversi"};

    private final JComboBox<String> boardSizeInput;
    private final JComboBox<String> gameTypeInput;

    private final JPanel boardConfigurationContainer;

    public GUIBoardConfiguration(){
        boardConfigurationContainer = new JPanel(new GridLayout(2, 2, 70, 7));
        boardConfigurationContainer.setBorder(new EmptyBorder(0, 0, 25, 0));
        JLabel boardSizeLabel = new JLabel("Board Size: ");
        boardSizeInput = new JComboBox<>(boardSize);
        JLabel typeOfGame = new JLabel("Select Game: ");
        gameTypeInput = new JComboBox<>(gameType);
        boardSizeInput.setSelectedIndex(2);
        boardConfigurationContainer.add(boardSizeLabel);
        boardConfigurationContainer.add(typeOfGame);
        boardConfigurationContainer.add(boardSizeInput);
        boardConfigurationContainer.add(gameTypeInput);
    }

    public JPanel getBoardConfiguration(){
        return boardConfigurationContainer;
    }

    public int getSelectedSize(){
        return Integer.parseInt(Objects.requireNonNull(boardSizeInput.getSelectedItem()).toString().split("x")[0]);
    }

    public int getSelectedGame(){
        return (Objects.equals(gameTypeInput.getSelectedItem(), "Othello")) ? 1 : 2;
    }

    public void disableInputs(){
        boardSizeInput.setEnabled(false);
        gameTypeInput.setEnabled(false);
    }

    public void enableInputs(){
        boardSizeInput.setEnabled(true);
        gameTypeInput.setEnabled(true);
    }
}
