import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import javax.swing.*;

public class TicTacToe {

    private CountDownLatch gameLatch = new CountDownLatch(1);

    int boardWidth = 480;
    int boardHeight = 480; //50px for the text panel on top

    JFrame frame = new JFrame("Tic-Tac-Toe");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();

    int flag = 0;
    JButton[][] board = new JButton[3][3];
    String playerX = "X";
    String playerO = "O";
    String currentPlayer = playerX;

    boolean gameOver = false;
    int turns = 0;

    TicTacToe() {
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setBackground(Color.darkGray);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font("Arial", Font.BOLD, 30));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Tic-Tac-Toe");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(3, 3));
        boardPanel.setBackground(Color.darkGray);
        frame.add(boardPanel);

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                JButton tile = new JButton();
                board[r][c] = tile;
                boardPanel.add(tile);

                tile.setBackground(Color.darkGray);
                tile.setForeground(Color.white);
                tile.setFont(new Font("Arial", Font.BOLD, 120));
                tile.setFocusable(false);
                // tile.setText(currentPlayer);

                // tile.addActionListener(new ActionListener() {
                //     public void actionPerformed(ActionEvent e) {
                //         if (gameOver) return;
                //         JButton tile = (JButton) e.getSource();
                //         if (tile.getText() == "") {
                //             tile.setText(currentPlayer);
                //             turns++;
                //             checkWinner();
                //             if (!gameOver) {
                //                 currentPlayer = currentPlayer == playerX ? playerO : playerX;
                //                 textLabel.setText(currentPlayer + "'s turn.");
                //             }
                //         }

                
                tile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (gameOver) return;
                        if (tile.getText().equals("")) {
                            tile.setText(playerX);
                            turns++;
                            checkWinner();

                            if (!gameOver) {
                                currentPlayer = playerO;
                                textLabel.setText("Computer's turn (O).");
                                makeComputerMove();
                                checkWinner();
                            }
                        }
                    }
                });
            }
        }
        try {
            // Wait for the Minesweeper game to complete
            gameLatch.await();
        } catch (InterruptedException err) {
            err.printStackTrace();
        }
    }


    void makeComputerMove() {
        if (gameOver) return;

        // Simulate computer's move (randomly choose an empty spot)
        Random random = new Random();
        int randomRow, randomCol;

        do {
            randomRow = random.nextInt(3);
            randomCol = random.nextInt(3);
        } while (!board[randomRow][randomCol].getText().equals(""));

        board[randomRow][randomCol].setText(playerO);
        currentPlayer = playerX;
        turns++;
    }
    
    void checkWinner() {
        //horizontal
        for (int r = 0; r < 3; r++) {
            if (board[r][0].getText() == "") continue;

            if (board[r][0].getText() == board[r][1].getText() &&
                board[r][1].getText() == board[r][2].getText()) {
                for (int i = 0; i < 3; i++) {
                    setWinner(board[r][i]);
                }
                gameOver = true;
                return;
            }
        }

        //vertical
        for (int c = 0; c < 3; c++) {
            if (board[0][c].getText() == "") continue;
            
            if (board[0][c].getText() == board[1][c].getText() &&
                board[1][c].getText() == board[2][c].getText()) {
                for (int i = 0; i < 3; i++) {
                    setWinner(board[i][c]);
                }
                gameOver = true;
                return;
            }
        }

        //diagonally
        if (board[0][0].getText() == board[1][1].getText() &&
            board[1][1].getText() == board[2][2].getText() &&
            board[0][0].getText() != "") {
            for (int i = 0; i < 3; i++) {
                setWinner(board[i][i]);
            }
            gameOver = true;
            return;
        }

        //anti-diagonally
        if (board[0][2].getText() == board[1][1].getText() &&
            board[1][1].getText() == board[2][0].getText() &&
            board[0][2].getText() != "") {
            setWinner(board[0][2]);
            setWinner(board[1][1]);
            setWinner(board[2][0]);
            gameOver = true;
            return;
        }

        if (turns == 9) {
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    setTie(board[r][c]);
                }
            }
            gameOver = true;
        }
    }

    void setWinner(JButton tile) {
        tile.setForeground(Color.green);
        tile.setBackground(Color.gray);
        textLabel.setText(currentPlayer + " is the winner!");
        if(currentPlayer == "X")
            flag = 1;
        // Schedule a task to close the frame after a delay (in milliseconds)
        int delay = 3000; // 3000 milliseconds = 3 seconds
        Timer timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the frame
                gameLatch.countDown();
            }
        });

        timer.setRepeats(false); // Set to false to execute the ActionListener only once
        timer.start();
    }

    void setTie(JButton tile) {
        tile.setForeground(Color.orange);
        tile.setBackground(Color.gray);
        textLabel.setText("Tie!");
        // Schedule a task to close the frame after a delay (in milliseconds)
        int delay = 3000; // 3000 milliseconds = 3 seconds
        Timer timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the frame
                gameLatch.countDown();
            }
        });

        timer.setRepeats(false); // Set to false to execute the ActionListener only once
        timer.start();
    }

    int win(){
        if (flag == 1){
            return(1);
        }
        return(0);
    }
}
