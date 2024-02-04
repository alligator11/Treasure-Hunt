import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import javax.swing.*;

public class Minesweeper {

    private CountDownLatch gameLatch = new CountDownLatch(1);

    private class MineTile extends JButton {
        int r;
        int c;

        public MineTile(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    int boardWidth = 680;
    int boardHeight = 680;
    int numRows;
    int numCols;
    int mineCount;
    int tileSize;

    MineTile[][] board;
    MineTile treasure;
    ArrayList<MineTile> mineList;
    
    JFrame frame = new JFrame("Minesweeper");
    JLabel textLabel = new JLabel();
    JLabel life = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();

    int flag = 0;
    int lifeCount = 3;
    Random random = new Random();

    int tilesClicked = 0; //goal is to click all tiles except the ones containing mines
    boolean gameOver = false;

    Minesweeper(int level) {
        
        numRows = 4*level;
        numCols = numRows;
        tileSize = (int) boardHeight/level;

        mineCount = numRows;
        board = new MineTile[numRows][numCols];

        // frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setFont(new Font("Arial", Font.BOLD, 25));
        // textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Minesweeper: " + Integer.toString(mineCount));
        // textLabel.setOpaque(true);

        life.setFont(new Font("Segoe UI Symbol", Font.BOLD, 25));
        life.setText("          Lifes:  \u2764  \u2764  \u2764 ");
        // life.setHorizontalAlignment(JLabel.RIGHT);
        // life.setOpaque(true);

        // textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel, BorderLayout.CENTER);
        // textPanel.setLayout(new BorderLayout());
        textPanel.add(life, BorderLayout.EAST);
        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(numRows, numCols)); //8x8
        // boardPanel.setBackground(Color.green);
        frame.add(boardPanel);
    }

    void playGame(){
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                MineTile tile = new MineTile(r, c);
                board[r][c] = tile;

                tile.setFocusable(false);
                tile.setMargin(new Insets(0, 0, 0, 0));
                tile.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 20));
                // tile.setText("💣");
                tile.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (gameOver) {
                            return;
                        }
                        MineTile tile = (MineTile) e.getSource();
                        
                        //left click
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            if (tile.getText() == "") {
                                if (mineList.contains(tile)) {
                                    if (lifeCount == 0)
                                        revealMines();
                                    else {
                                        lifeCount--;
                                        tile.setText("\u2620");
                                        if(lifeCount == 2)
                                            life.setText("             Lifes:  \u2764  \u2764 ");
                                        else if(lifeCount == 1)
                                            life.setText("                Lifes:  \u2764 ");
                                        else
                                            life.setText("            Lifes:    ");
                                    }
                                }
                                else if(tile == treasure){
                                    gameOver = true;
                                    textLabel.setText("Success! Wild card revealed.");
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
                                else {
                                    checkMine(tile.r, tile.c);
                                }
                            }
                        }
                        //right click
                        else if (e.getButton() == MouseEvent.BUTTON3) {
                            if (tile.getText() == "" && tile.isEnabled()) {
                                tile.setText("\u2691"); 
                            }
                            else if (tile.getText() == "\u2691") {
                                tile.setText("");
                            }
                        }
                    } 
                });

                boardPanel.add(tile);
                
            }
        }
        frame.setVisible(true);

        setMines();
        setTreasure();
        try {
            // Wait for the Minesweeper game to complete
            gameLatch.await();
        } catch (InterruptedException err) {
            err.printStackTrace();
        }
    }

    void setTreasure() {
        while(true){
            int r = random.nextInt(numRows); //0-7
            int c = random.nextInt(numCols);
            if(mineList.contains(board[r][c]))
                continue;
            else{
                treasure = board[r][c];
                break;
            } 
        }
    }

    void setMines() {
        mineList = new ArrayList<MineTile>();

        // mineList.add(board[2][2]);
        // mineList.add(board[2][3]);
        // mineList.add(board[5][6]);
        // mineList.add(board[3][4]);
        // mineList.add(board[1][1]);
        int mineLeft = mineCount;
        while (mineLeft > 0) {
            int r = random.nextInt(numRows); //0-7
            int c = random.nextInt(numCols);

            MineTile tile = board[r][c]; 
            if (!mineList.contains(tile)) {
                mineList.add(tile);
                mineLeft -= 1;
            }
        }
    }

    void revealMines() {
        for (int i = 0; i < mineList.size(); i++) {
            MineTile tile = mineList.get(i);
            tile.setText("\u2620");
        }

        gameOver = true;
        textLabel.setText("Game Over!");
        life.setText("                Lifes:    ");

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

    void checkMine(int r, int c) {
        if (r < 0 || r >= numRows || c < 0 || c >= numCols) {
            return;
        }

        MineTile tile = board[r][c];
        if (!tile.isEnabled()) {
            return;
        }
        tile.setEnabled(false);
        tilesClicked += 1;

        int minesFound = 0;

        //top 3
        minesFound += countMine(r-1, c-1);  //top left
        minesFound += countMine(r-1, c);    //top
        minesFound += countMine(r-1, c+1);  //top right

        //left and right
        minesFound += countMine(r, c-1);    //left
        minesFound += countMine(r, c+1);    //right

        //bottom 3
        minesFound += countMine(r+1, c-1);  //bottom left
        minesFound += countMine(r+1, c);    //bottom
        minesFound += countMine(r+1, c+1);  //bottom right

        if (minesFound > 0) {
            tile.setText(Integer.toString(minesFound));
        }
        else {
            tile.setText("");
            
            //top 3
            checkMine(r-1, c-1);    //top left
            checkMine(r-1, c);      //top
            checkMine(r-1, c+1);    //top right

            //left and right
            checkMine(r, c-1);      //left
            checkMine(r, c+1);      //right

            //bottom 3
            checkMine(r+1, c-1);    //bottom left
            checkMine(r+1, c);      //bottom
            checkMine(r+1, c+1);    //bottom right
        }

        if (tilesClicked == numRows * numCols - mineList.size()) {
            gameOver = true;
            textLabel.setText("Mines Cleared!");
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
    }

    int countMine(int r, int c) {
        if (r < 0 || r >= numRows || c < 0 || c >= numCols) {
            return 0;
        }
        if (mineList.contains(board[r][c])) {
            return 1;
        }
        return 0;
    }

    int win(){
        if (flag == 1){
            return(1);
        }
        return(0);
    }
}

