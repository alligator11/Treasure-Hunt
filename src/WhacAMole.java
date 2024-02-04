import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import javax.swing.*;

public class WhacAMole {

    private CountDownLatch gameLatch = new CountDownLatch(1);

    int flag = 0;
    int boardWidth = 480;
    int boardHeight = 480; //50 for the text panel on top

    JFrame frame = new JFrame("Mario: Whac A Mole");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel(); 
	
    JButton[] board = new JButton[9];
    ImageIcon moleIcon;
    ImageIcon plantIcon;

    JButton currMoleTile;
    JButton currPlantTile;

    Random random = new Random();
    Timer setMoleTimer;
    Timer setPlantTimer;
    int score = 0;

    WhacAMole() {
        // frame.setVisible(true);
	    frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Score: " + Integer.toString(score));
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);		
        frame.add(textPanel, BorderLayout.NORTH);

	    boardPanel.setLayout(new GridLayout(3, 3));
        // boardPanel.setBackground(Color.black);
        frame.add(boardPanel);

        // plantIcon = new ImageIcon(getClass().getResource("./piranha.png"));
        Image plantImg = new ImageIcon(getClass().getResource("./piranha.png")).getImage();
        plantIcon = new ImageIcon(plantImg.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH));

        Image moleImg = new ImageIcon(getClass().getResource("./monty.png")).getImage();
        moleIcon = new ImageIcon(moleImg.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH));
		
	    for (int i = 0; i < 9; i++) {
            JButton tile = new JButton();
            board[i] = tile;
            boardPanel.add(tile);
            tile.setFocusable(false);
            // tile.setIcon(plantIcon);

            tile.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton tile = (JButton) e.getSource();
                    if (tile == currMoleTile) {
                        score += 10;
                        textLabel.setText("Score: " + Integer.toString(score));
                    }
                    else if (tile == currPlantTile) {
                        if(score >= 100){
		                    textLabel.setText("You Won! Game Over: " + Integer.toString(score));
                            flag = 1;
                        }
                        else
                            textLabel.setText("You Lose! Game Over: " + Integer.toString(score));
                        setMoleTimer.stop();
                        setPlantTimer.stop();
                        for (int i = 0; i < 9; i++) {
                            board[i].setEnabled(false);
                        }
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
            });
	}

        setMoleTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //remove icon from current tile
                if (currMoleTile != null) {
                    currMoleTile.setIcon(null);
                    currMoleTile = null;
                }

                //randomly select another tile
                int num = random.nextInt(9); //0-8
                JButton tile = board[num];

                //if tile is occupied by plant, skip tile for this turn
                if (currPlantTile == tile) return;

                //set tile to mole
                currMoleTile = tile;
                currMoleTile.setIcon(moleIcon);
            }
        });

        setPlantTimer = new Timer(1500, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //remove icon from current tile
                if (currPlantTile != null) {
                    currPlantTile.setIcon(null);
                    currPlantTile = null;
                }

                //randomly select another tile
                int num = random.nextInt(9); //0-8
                JButton tile = board[num];

                //if tile is occupied by mole, skip tile for this turn
                if (currMoleTile == tile) return;

                //set tile to mole
                currPlantTile = tile;
                currPlantTile.setIcon(plantIcon);
            }
        });

        setMoleTimer.start();
        setPlantTimer.start();
        frame.setVisible(true);
        try {
            // Wait for the Minesweeper game to complete
            gameLatch.await();
        } catch (InterruptedException err) {
            err.printStackTrace();
        }
    }
    int win(){
        if (flag == 1){
            return(1);
        }
        return(0);
    }
}


/*
 * Homework
 * - Add Multiple Piranha plants and store them in an Array
 * - Add Button on the bottom to restart game
 * - Keep track of high score and display it
 */