package sdm.reversi.manager;

import sdm.reversi.Coordinate;
import sdm.reversi.board.Board;
import sdm.reversi.game.Game;
import sdm.reversi.gui.DiskPanel;
import sdm.reversi.gui.DraggableFrame;
import sdm.reversi.gui.TitleBar;
import sdm.reversi.launcher.GUILauncher;
import sdm.reversi.player.Player;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GUIManager extends DraggableFrame implements GameManager {

    private final static int FRAME_SIZE = 700;

    private Map<Coordinate, DiskPanel> graphicBoard;
    private JLabel player1Score;
    private JLabel player2Score;
    private JLabel currentPlayerName;
    private int boardSize;
    private Coordinate nextMove;
    private boolean quit;

    @Override
    public void startTurn(Player currentPlayer) {
        currentPlayerName.setText(String.format("%s's turn", currentPlayer.getName()));
        currentPlayerName.setForeground(currentPlayer.getColor().getGraphicalColor());
    }

    @Override
    public void skipTurn() {
        JOptionPane.showMessageDialog(this, "Sorry you can make no moves!");
    }

    @Override
    public void suggestMoves(Collection<Coordinate> moves) {
        moves.forEach(move -> {
            graphicBoard.get(move).suggest(currentPlayerName.getForeground());
            graphicBoard.get(move).repaint();
        });
    }

    @Override
    public void updateGame(Game game) {
        player1Score.setText(String.format("%d", game.getPlayer1().getScore()));
        player2Score.setText(String.format("%d", game.getPlayer2().getScore()));
        updateGridPanel(game.getBoard());

    }

    @Override
    public void showWinner(Player player) {
        if (player == null) {
            JOptionPane.showMessageDialog(this, "Tie!");
            dispose();
            GUILauncher.launch();
            return;
        }
        JOptionPane.showMessageDialog(this, "The winner is " + player.getName());
        dispose();
        GUILauncher.launch();
    }

    @Override
    public Coordinate getMoveFromPlayer() {
        while (nextMove == null && !quit) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (quit)
            return null;
        Coordinate move = new Coordinate(nextMove.getRow(), nextMove.getColumn());
        nextMove = null;
        return move;
    }

    @Override
    public void illegalMove() {
        JOptionPane.showMessageDialog(this, "Invalid move, please choose another one");
    }

    @Override
    public void notifyError(String message) {
        JOptionPane.showMessageDialog(this, String.format("Error: %s", message));
    }

    private void updateGridPanel(Board board) {
        graphicBoard.forEach((coordinate, panel) -> {
            panel.setColor(board.getDiskColorFromCoordinate(coordinate));
            panel.repaint();
        });

    }

    @Override
    public void initialize(Game game) {
        boardSize = game.getBoard().getSize();

        TitleBar titleBar = TitleBar.TitleBarBuilder.createTitleBar(this).build();
        add(titleBar.getTitleBar(), BorderLayout.NORTH);

        JPanel statisticsPanel = createStatisticsPanel(game);
        JPanel container = new JPanel(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridx = 0;
        container.add(statisticsPanel, gridBagConstraints);
        container.setBackground(Color.decode("#b0b0b0"));

        JPanel boardPanel = new JPanel(new BorderLayout());
        boardPanel.add(createGridPanel(game), BorderLayout.CENTER);

        JPanel yAxisPanel = new JPanel(new GridLayout(boardSize, 1));
        yAxisPanel.setBorder(new EmptyBorder(0, 10, 0, 10));
        yAxisPanel.setPreferredSize(new Dimension(50, FRAME_SIZE));
        yAxisPanel.setBackground(Color.decode("#b0b0b0"));
        JPanel xAxisPanel = new JPanel(new GridLayout(1, boardSize + 1));
        xAxisPanel.setBorder(new EmptyBorder(0, 50, 0, 0));
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

    private JPanel createGridPanel(Game game) {
        JPanel boardPanel = new JPanel(new GridLayout(boardSize, boardSize));
        DiskPanel.setRadius(FRAME_SIZE / boardSize / 4);

        graphicBoard =
                IntStream.range(0, boardSize).boxed()
                        .flatMap(x -> IntStream.range(0, boardSize)
                                .mapToObj(y -> new Coordinate(x, y))
                        )
                        .collect(Collectors.toMap(c -> c, c -> {
                            DiskPanel diskPanel = new DiskPanel(game.getBoard().getDiskColorFromCoordinate(c));
                            diskPanel.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    if (nextMove == null) {
                                        nextMove = c;
                                    }
                                }
                            });
                            boardPanel.add(diskPanel);
                            return diskPanel;
                        }));

        boardPanel.setPreferredSize(new Dimension(700, 700));
        boardPanel.setBackground(Color.BLACK);

        return boardPanel;
    }

    private JPanel createStatisticsPanel(Game game) {
        JPanel statisticsPanel = new JPanel(new BorderLayout());
        statisticsPanel.setPreferredSize(new Dimension(300, FRAME_SIZE));
        statisticsPanel.setBackground(Color.decode("#b0b0b0"));
        statisticsPanel.setBorder(new EmptyBorder(0, 25, 0, 25));

        JLabel player1Name = new JLabel(game.getPlayer1().getName(), JLabel.CENTER);
        JLabel player2Name = new JLabel(game.getPlayer2().getName(), JLabel.CENTER);
        player1Score = new JLabel(String.format("%d", game.getPlayer1().getScore()), JLabel.CENTER);
        player2Score = new JLabel(String.format("%d", game.getPlayer2().getScore()), JLabel.CENTER);
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

        JButton mainMenuButton = new JButton("Main Menu");
        mainMenuButton.setMaximumSize(new Dimension(30, 10));
        mainMenuButton.addActionListener(e -> {
            quit = true;

        });

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
}
