import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeFrame extends JFrame {

    private static final int ROW = 3;
    private static final int COL = 3;

    private String[][] gameBoard = new String[ROW][COL];
    private String player = "X";
    private int moveCount = 0;

    private TicTacToeTile[][] buttons = new TicTacToeTile[ROW][COL];

    public TicTacToeFrame() {

        setTitle("TicTacToe");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        clearBoard();

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(3, 3));
        ButtonListener listener = new ButtonListener();

        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {

                buttons[row][col] = new TicTacToeTile(row, col);
                buttons[row][col].setFont(new Font("Arial", Font.BOLD, 40));
                buttons[row][col].addActionListener(listener);

                boardPanel.add(buttons[row][col]);
            }
        }

        add(boardPanel, BorderLayout.CENTER);
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Game ended.");
            System.exit(0);
        });

        add(quitButton, BorderLayout.SOUTH);
        setVisible(true);
    }

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            TicTacToeTile tile = (TicTacToeTile) e.getSource();
            int row = tile.getRow();
            int col = tile.getCol();
            handleMove(row, col);
        }
    }

    private void handleMove(int row, int col) {
        if (!isValid(row, col)) {
            JOptionPane.showMessageDialog(this, "Illegal move! Try again.");
            return;
        }

        gameBoard[row][col] = player;
        buttons[row][col].setText(player);
        moveCount++;

        if (moveCount >= 5 && isWin(player)) {
            JOptionPane.showMessageDialog(this, "Player " + player + " wins!");
            askPlayAgain();
            return;
        }

        if (moveCount >= 7 && isTie()) {
            JOptionPane.showMessageDialog(this, "It's a Tie!");
            askPlayAgain();
            return;
        }
        switchPlayer();
    }

    private void switchPlayer() {
        if (player.equals("X"))
            player = "O";
        else
            player = "X";
    }

    private void askPlayAgain() {

        int choice = JOptionPane.showConfirmDialog(this, "Play again?","Game over!", JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            resetGame();
        } else {
            System.exit(0);
        }
    }

    private void resetGame() {
        clearBoard();
        moveCount = 0;
        player = "X";

        for (int r = 0; r < ROW; r++) {
            for (int c = 0; c < COL; c++) {
                buttons[r][c].setText("");
            }
        }
    }

    private void clearBoard() {
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                gameBoard[row][col] = " ";
            }
        }
    }

    private boolean isValid(int row, int col) {
        return gameBoard[row][col].equals(" ");
    }

    private boolean isWin(String player) {
        return isColWin(player) || isRowWin(player) || isDiagonalWin(player);
    }

    private boolean isColWin(String player) {
        for (int col = 0; col < COL; col++) {
            if (gameBoard[0][col].equals(player) && gameBoard[1][col].equals(player) && gameBoard[2][col].equals(player)) {
                return true;
            }
        }
        return false;
    }

    private boolean isRowWin(String player) {
        for (int row = 0; row < ROW; row++) {
            if (gameBoard[row][0].equals(player) && gameBoard[row][1].equals(player) && gameBoard[row][2].equals(player)) {
                return true;
            }
        }
        return false;
    }

    private boolean isDiagonalWin(String player) {
        if (gameBoard[0][0].equals(player) && gameBoard[1][1].equals(player) && gameBoard[2][2].equals(player)) {
            return true;
        }

        if (gameBoard[0][2].equals(player) && gameBoard[1][1].equals(player) && gameBoard[2][0].equals(player)) {
            return true;
        }
        return false;
    }

    private boolean isTie() {

        boolean xFlag = false;
        boolean oFlag = false;

        for (int row = 0; row < ROW; row++) {

            if (gameBoard[row][0].equals("X") || gameBoard[row][1].equals("X") || gameBoard[row][2].equals("X")) xFlag = true;

            if (gameBoard[row][0].equals("O") || gameBoard[row][1].equals("O") || gameBoard[row][2].equals("O")) oFlag = true;

            if (!(xFlag && oFlag))
                return false;

            xFlag = oFlag = false;
        }

        for (int col = 0; col < COL; col++) {

            if (gameBoard[0][col].equals("X") || gameBoard[1][col].equals("X") || gameBoard[2][col].equals("X")) xFlag = true;

            if (gameBoard[0][col].equals("O") || gameBoard[1][col].equals("O") || gameBoard[2][col].equals("O")) oFlag = true;

            if (!(xFlag && oFlag))
                return false;

            xFlag = oFlag = false;
        }

        if (!checkDiagonalTie(0, 0, 1, 1, 2, 2))
            return false;

        if (!checkDiagonalTie(0, 2, 1, 1, 2, 0))
            return false;

        return true;
    }

    private boolean checkDiagonalTie(int r1, int c1, int r2, int c2, int r3, int c3) {

        boolean xFlag = false;
        boolean oFlag = false;

        if (gameBoard[r1][c1].equals("X") || gameBoard[r2][c2].equals("X") || gameBoard[r3][c3].equals("X")) xFlag = true;

        if (gameBoard[r1][c1].equals("O") || gameBoard[r2][c2].equals("O") || gameBoard[r3][c3].equals("O")) oFlag = true;

        return (xFlag && oFlag);
    }
}