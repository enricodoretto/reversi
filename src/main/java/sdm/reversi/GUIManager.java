package sdm.reversi;

import sdm.reversi.game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUIManager extends JFrame implements IOManager, ActionListener {
    private JLabel player1Name, player2Name, player1Score, player2Score, currentPlayerName;
    private JPanel[][] graphicBoard;
    private final static int FRAME_SIZE = 700;
    private int diskRadius;
    //private boolean[][] diskIsPresent;
    //private int numberOfMoves = 0;
    private ArrayList<Point> points;
    //private boolean currentColor; //check color of player


    @Override
    public void updateGame(Game game) {

    }

    @Override
    public Coordinate getMoveFromPlayer() {
        return null;
    }

    @Override
    public void startTurn(String message) {

    }

    @Override
    public void illegalMove(String message) {

    }

    @Override
    public void initialize(Game game) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
