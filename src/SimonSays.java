import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.swing.Timer;

import java.util.concurrent.CountDownLatch;

public class SimonSays {

    private CountDownLatch gameLatch = new CountDownLatch(1);

    int flag = 0;

    JFrame frame;
    JPanel textPanel;
    JLabel label1;
    JPanel boardPanel;
    JLabel label;
    JButton[] board;

    private static final List<Color> COLORS = Arrays.asList(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.WHITE, 
                                                            Color.ORANGE, Color.PINK, Color.CYAN, Color.MAGENTA);
    private static final List<Integer> pattern = new ArrayList<>();
    private int playerCurr = 0;
    private int curr = 0;
    private Boolean startGame = false;

    SimonSays(){

        frame = new JFrame();
        frame.setTitle("Simon Says...");
        frame.setSize(480, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        textPanel = new JPanel();
        textPanel.setLayout(new BorderLayout());

        label = new JLabel();
        label.setText("Simon Say...");
        label.setLayout(null);
        label.setFont(new Font("Roboto", Font.BOLD, 50));
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setOpaque(true);

        String description = "<html>Simon Says...<br>"
            + "At each level, a new block will be added.<br>"
            + "Your challenge is to remember the previous sequence<br>"
            + "and repeat the sequence with this newly added block.<br>"
            + "Your Goal is to Reach Level 7<html>";

        label1 = new JLabel();
        label1.setText(description);
        label1.setLayout(null);
        label1.setFont(new Font("Roboto", Font.BOLD, 25));
        label1.setHorizontalTextPosition(JLabel.CENTER);
        label1.setOpaque(true);

        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(3, 3));

        board = new JButton[9];
   
            for(int i=0; i<9; i++){
               JButton tile = new JButton();
               board[i] = tile;
               boardPanel.add(tile);
               tile.setBackground(COLORS.get(i));
               tile.setBorderPainted(false);
               tile.setBorder(BorderFactory.createLineBorder(Color.black, 4));
               tile.addActionListener(new ButtonClickListener());
            }

        board[4].setText("Level 1");
        board[4].setFocusable(false);
        board[4].setFont(new Font("ROBOTO", Font.BOLD, 25));
        
        frame.setVisible(true);
        textPanel.add(label);
        frame.add(label1);
    
        startGame();
        try {
            // Wait for the Minesweeper game to complete
            gameLatch.await();
        } catch (InterruptedException err) {
            err.printStackTrace();
        }
    }

    void startGame() {
        Timer timer = new Timer(8000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // System.out.print("WORKING");
                label.setText("Simon Says...");
                frame.remove(label1);
                frame.add(textPanel, BorderLayout.NORTH);
                frame.add(boardPanel);
                frame.revalidate(); 
                frame.repaint();
                addNextLevel();
                displayPattern();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    void addNextLevel(){
        Random random = new Random();
        int randomNum = random.nextInt(8);
        randomNum = randomNum==4?8:randomNum;
        pattern.add(randomNum);
    }

    void displayPattern() {
        // System.out.println(pattern);
        Timer timer = new Timer(500, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // System.out.println(curr+ " " + pattern.size());
                if (curr < pattern.size()) {
                    JButton tile = board[pattern.get(curr)];
                    tile.setBorderPainted(true);
                    // System.out.println(pattern.get(curr));
                    
                    Timer innerTimer = new Timer(500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent f) {
                            tile.setBorderPainted(false);
                            // System.out.println(curr+ " " + pattern.size());
                            if (++curr == pattern.size()) {
                                ((Timer) e.getSource()).stop();
                                startGame = true;
                            }
                            label.setText("Your Turn");
                        }
                    });
                    
                    innerTimer.setRepeats(false);
                    // innerTimer.setInitialDelay(250);
                    innerTimer.start();
                }
            }
        });

        timer.setRepeats(false);
        timer.start();
    }   
   
    void gameWon(){
        label.setText("You Won !!!");
        flag = 1;
        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the frame
                gameLatch.countDown();
            }
        });
            timer.setRepeats(false);
            timer.start();
    }

    void resetGame() {
        pattern.clear();
        playerCurr = 0;
        curr = 0;
        startGame = false;
        label.setText("Simon Says...");
        addNextLevel();
        displayPattern();
    }

    void gameLoss(){
        label.setText("You Lost !!!");
        board[4].setText("Game Over!");
        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the frame
                gameLatch.countDown();
            }
        });
            timer.setRepeats(false);
            timer.start();
    }

    class ButtonClickListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(!startGame) return;

            JButton clickedButton = (JButton) e.getSource();
            Color clickedButtonColor = clickedButton.getBackground();

            if(clickedButtonColor.equals(COLORS.get(pattern.get(playerCurr)))){
                playerCurr++;

                if(playerCurr==pattern.size()){
                    playerCurr = 0;
                    startGame = false;
                    label.setText("Simon Says");
                    board[4].setText("Level "+ (pattern.size()+1));
                    if(pattern.size()==7){
                        gameWon();
                    }
                    else if(!startGame){
                        addNextLevel();
                        displayPattern();
                    }
                }
            }
            else{
                gameLoss();
            }
        }
    }

    int win(){
        if (flag == 1){
            return(1);
        }
        return(0);
    }
}
