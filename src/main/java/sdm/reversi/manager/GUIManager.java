package sdm.reversi.manager;

import sdm.reversi.Coordinate;
import sdm.reversi.Disk;
import sdm.reversi.Player;
import sdm.reversi.gui.TitleBar;
import sdm.reversi.game.Game;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.IntStream;

public class GUIManager extends JFrame implements IOManager, ActionListener {
    private JLabel player1Name, player2Name, player1Score, player2Score, currentPlayerName;
    private JPanel[][] graphicBoard;
    private final static int FRAME_SIZE = 700;
    private int diskRadius;
    private int boardSize;

    @Override
    public void startTurn(Player currentPlayer) {

    }

    @Override
    public void skipTurn() {

    }

    @Override
    public void suggestMoves(Collection<Coordinate> moves) {

    }

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
    public void illegalMove(String message) {
    }

    @Override
    public void initialize(Game game) {
        boardSize = game.getBoard().getSize();
        graphicBoard = new JPanel[boardSize][boardSize];
        points = new ArrayList<>();
        diskRadius = (FRAME_SIZE / boardSize) / 2;

        TitleBar titleBar = new TitleBar();
        add(titleBar.getTitleBar(), BorderLayout.NORTH);

        JPanel statisticsPanel = createStatisticsPanel(game);
        JPanel container = new JPanel(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridx = 0;
        container.add(statisticsPanel, gridBagConstraints);
        container.setBackground(Color.decode("#b0b0b0"));

        JPanel boardPanel = new JPanel(new BorderLayout());
        boardPanel.add(createGridPanel(game, this), BorderLayout.CENTER);

        JPanel yAxisPanel = new JPanel(new GridLayout(boardSize, 1));
        yAxisPanel.setBorder(new EmptyBorder(0, 10, 0, 10));
        yAxisPanel.setPreferredSize(new Dimension(30, FRAME_SIZE));
        yAxisPanel.setBackground(Color.decode("#b0b0b0"));
        JPanel xAxisPanel = new JPanel(new GridLayout(1, boardSize + 1));
        xAxisPanel.setBorder(new EmptyBorder(0, 30, 0, 0));
        xAxisPanel.setBackground(Color.decode("#b0b0b0"));
        IntStream.range(0, boardSize).forEach(index -> {
            xAxisPanel.add(new JLabel(String.format("%c", index + 'A'), JLabel.CENTER));
            yAxisPanel.add(new JLabel(String.format("%d", index + 1)));
        });
        boardPanel.add(xAxisPanel, BorderLayout.NORTH);
        boardPanel.add(yAxisPanel, BorderLayout.WEST);

        gridBagConstraints.gridx++;
        container.add(boardPanel, gridBagConstraints);
        add(container);
        setResizable(false);
        setUndecorated(true);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createGridPanel(Game game, final JFrame frame) {
        JPanel boardPanel = new JPanel(new GridLayout(boardSize, boardSize));
        for (int indexRow = 0; indexRow < boardSize; indexRow++) {
            for (int indexColumn = 0; indexColumn < boardSize; indexColumn++) {
                graphicBoard[indexRow][indexColumn] = new JPanel() {
                    @Override
                    public void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        Graphics2D g2 = (Graphics2D) g;
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        for (Point point : points) {
                            if (game.getCurrentPlayer().getColor() == Disk.Color.BLACK) {
                                g2.setColor(Color.BLACK); //print fill oval
                            } else {
                                g2.setColor(Color.WHITE); //print fill oval
                            }
                            g2.fillOval(point.x, point.y, diskRadius, diskRadius);
                        }
                    }
                };
                graphicBoard[indexRow][indexColumn].setBorder(new LineBorder(Color.BLACK, 2));
                graphicBoard[indexRow][indexColumn].setBackground(Color.decode("#0E6B0E"));
                final int indexR = indexRow, indexC = indexColumn;
                graphicBoard[indexRow][indexColumn].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
/*
                        if(nextMove == null){
                            nextMove = new Coordinate(indexR, indexC);
                        }

                        if(isValidMove(indexR, indexC, dimensionBoard, frame)){
                            doMove(indexR, indexC, dimensionBoard, frame);
                        }else{
                            JOptionPane.showMessageDialog(frame, "Invalid First Position");
                        }*/
                    }
                });
                boardPanel.add(graphicBoard[indexRow][indexColumn]);
            }
        }
        boardPanel.setPreferredSize(new Dimension(700, 700));
        boardPanel.setBackground(Color.BLACK);

        return boardPanel;
    }

    private JPanel createStatisticsPanel(Game game) {
        JPanel statisticsPanel = new JPanel(new BorderLayout());
        statisticsPanel.setPreferredSize(new Dimension(300, FRAME_SIZE));
        statisticsPanel.setBackground(Color.decode("#b0b0b0"));
        statisticsPanel.setBorder(new EmptyBorder(0, 25, 0, 25));

        player1Name = new JLabel(game.getPlayer1().getName(), JLabel.CENTER);
        player2Name = new JLabel(game.getPlayer2().getName(), JLabel.CENTER);
        player1Score = new JLabel(game.getPlayer1().getScore() + "", JLabel.CENTER); // conversione da int con un metodo che vede il numero di dischi
        player2Score = new JLabel(game.getPlayer2().getScore() + "", JLabel.CENTER);
        currentPlayerName = new JLabel(game.getCurrentPlayer().getName() + "'s Turn", JLabel.CENTER);

        player1Name.setFont(new Font("Tahoma", Font.BOLD, 30));
        player1Score.setFont(new Font("Tahoma", Font.BOLD, 60));
        player2Name.setFont(new Font("Tahoma", Font.BOLD, 30));
        player2Score.setFont(new Font("Tahoma", Font.BOLD, 60));
        currentPlayerName.setFont(new Font("Tahoma", Font.BOLD, 30));

        player1Name.setForeground(Color.BLACK);
        player1Score.setForeground(Color.BLACK);
        player2Name.setForeground(Color.WHITE);
        player2Score.setForeground(Color.WHITE);

        currentPlayerName.setBorder(new EmptyBorder(50, 0, 50, 0));

        // to move in class fields
        JButton mainMenuButton = new JButton("Main Menu");
        mainMenuButton.setMaximumSize(new Dimension(30, 10));
        mainMenuButton.addActionListener(this);

        statisticsPanel.add(currentPlayerName, BorderLayout.NORTH);
        JPanel playerStatisticsPanel = new JPanel(new GridLayout(4, 1));
        playerStatisticsPanel.setBackground(Color.decode("#b0b0b0"));
        playerStatisticsPanel.add(player1Name);
        playerStatisticsPanel.add(player1Score);
        playerStatisticsPanel.add(player2Name);
        playerStatisticsPanel.add(player2Score);
        statisticsPanel.add(playerStatisticsPanel, BorderLayout.CENTER);
        statisticsPanel.add(mainMenuButton, BorderLayout.SOUTH);

        return statisticsPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}