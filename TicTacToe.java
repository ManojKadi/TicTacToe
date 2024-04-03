import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe extends JFrame implements ActionListener {
    private JButton[] buttons;
    private boolean player1Turn;
    private int[][] winningPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Horizontal
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Vertical
            {0, 4, 8}, {2, 4, 6}             // Diagonal
    };
    private boolean gameEnded;

    public TicTacToe() {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 350); // Increased height to accommodate the play again button
        setLayout(new BorderLayout());

        JLabel headingLabel = new JLabel("Tic Tac Toe", SwingConstants.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(headingLabel, BorderLayout.NORTH);

        JPanel gamePanel = new JPanel(new GridLayout(3, 3));
        buttons = new JButton[9];
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            buttons[i].setFont(new Font("Arial", Font.BOLD, 50));
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(this);
            gamePanel.add(buttons[i]);
        }
        add(gamePanel, BorderLayout.CENTER);

        player1Turn = true;
        gameEnded = false;

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        
        if (gameEnded || !clickedButton.getText().isEmpty()) {
            return;
        }

        if (player1Turn) {
            clickedButton.setText("X");
            clickedButton.setForeground(Color.BLUE);
        } else {
            clickedButton.setText("O");
            clickedButton.setForeground(Color.RED);
        }
        
        if (checkWin()) {
            String winner = player1Turn ? "Player 1 (X)" : "Player 2 (O)";
            int option = JOptionPane.showOptionDialog(this, "Congratulations! " + winner + " wins! Do you want to play again?", "Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon("trophy.png"), new String[]{"Play Again"}, "Play Again");
            if (option == JOptionPane.YES_OPTION) {
                resetGame();
            } else {
                gameEnded = true;
            }
        } else if (checkDraw()) {
            int option = JOptionPane.showOptionDialog(this, "It's a draw! Do you want to play again?", "Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon("draw.png"), new String[]{"Play Again"}, "Play Again");
            if (option == JOptionPane.YES_OPTION) {
                resetGame();
            } else {
                gameEnded = true;
            }
        } else {
            player1Turn = !player1Turn;
        }
    }

    private boolean checkWin() {
        for (int[] position : winningPositions) {
            if (!buttons[position[0]].getText().isEmpty() &&
                buttons[position[0]].getText().equals(buttons[position[1]].getText()) &&
                buttons[position[0]].getText().equals(buttons[position[2]].getText())) {
                for (int i = 0; i < 3; i++) {
                    buttons[position[i]].setBackground(Color.GREEN); // Highlight winning buttons
                }
                return true;
            }
        }
        return false;
    }

    private boolean checkDraw() {
        for (JButton button : buttons) {
            if (button.getText().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private void resetGame() {
        for (JButton button : buttons) {
            button.setText("");
            button.setBackground(null); // Reset button color
        }
        gameEnded = false;
        player1Turn = true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicTacToe::new);
    }
}
