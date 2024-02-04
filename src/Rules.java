// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import java.util.concurrent.CountDownLatch;

// public class Rules extends JFrame {
//     private CountDownLatch gameLatch = new CountDownLatch(1);
//     public Rules() {
//         setTitle("Treasure Hunt Game Rules");
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         setSize(680, 680);
//         setLocationRelativeTo(null);

//         setLayout(new FlowLayout());
//         JTextArea rulesTextArea = new JTextArea();
//         rulesTextArea.setEditable(false);
//         rulesTextArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
//         rulesTextArea.setText(getGameRules());

//         JScrollPane scrollPane = new JScrollPane(rulesTextArea);
//         getContentPane().add(scrollPane);
//         JButton playButton = new JButton("Start game");
//         this.add(rulesTextArea);
//         this.add(playButton);
//         this.setVisible(true);
//         playButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 // Schedule a task to close the frame after a delay (in milliseconds)
//                 int delay = 3000; // 3000 milliseconds = 3 seconds
//                 Timer timer = new Timer(delay, new ActionListener() {
//                     @Override
//                     public void actionPerformed(ActionEvent e) {
//                         dispose(); // Close the frame
//                         gameLatch.countDown();
//                     }
//                 });

//                 timer.setRepeats(false); // Set to false to execute the ActionListener only once
//                 timer.start();
//             }
//         });
//         try {
//             // Wait for the Minesweeper game to complete
//             gameLatch.await();
//         } catch (InterruptedException err) {
//             err.printStackTrace();
//         }
//     }

//     private String getGameRules() {
//         return  ("Treasure Hunt Game Rules:\n\n" +

//         "1. Minesweeper Rules:\n\n" +
//         "a. Objective:\n" +
//         "- Successfully clear all levels of Minesweeper to progress and unlock the treasure.\n\n" +
        
//         "b. Grid and Mines:\n" +
//         "- Level 1: 4x4 grid with 4 mines\n" +
//         "- Level 2: 8x8 grid with 8 mines\n" +
//         "- Level 3: 12x12 grid with 12 mines\n" +
//         "- Level 4: 16x16 grid with 16 mines\n" +
//         "- Level 5: 20x20 grid with 20 mines\n\n" +
        
//         "c. Winning and Losing:\n" +
//         "- Win each level to advance.\n" +
//         "- Three lifelines: Reveal up to 3 mines per level.\n" +
//         "- If the 4th mine is clicked, you lose the game.\n" +
//         "- Hidden Wild card entry button available for immediate progression to the next level.\n\n" +
        
//         "d. Passcode and Unlocking Treasure:\n" +
//         "- Win all levels and enter the correct passcode to unlock the treasure.\n" +
//         "- If you fail to enter the right passcode, you lose the game.\n\n" +

//         "2. Hint Games:\n\n" +
//         "a. Objective:\n" +
//         "- Play after each Minesweeper level for a hint towards the passcode.\n\n" +
        
//         "b. Available Games:\n" +
//         "- Tic Tac Toe (Single player against the computer)\n" +
//         "- Whac-A-Mole (Earn 10 points per mole, reach 100 points to win)\n" +
//         "- Simon Says (Remember the order of colors for 8 levels)\n" +
//         "- Hangman (Guess the word before the man is hanged)\n\n" +
        
//         "c. Scoring:\n" +
//         "- Winning each Hint Game earns you 2 coins and a hint.\n" +
//         "- Tic Tac Toe: Beat the computer to win.\n" +
//         "- Simon Says: Remember the color sequence for 7 levels.\n" +
//         "- Whac-A-Mole: Click moles to earn 10 points on each click; avoid clicking plants before reaching 100 points.\n" +
//         "- Hangman: Guess the word before the hangman is complete.\n\n" +

//         "3. Scoring System:\n\n" +
//         "a. Minesweeper Levels:\n" +
//         "- Level 1: 2 coins\n" +
//         "- Level 2: 4 coins\n" +
//         "- Level 3: 8 coins\n" +
//         "- Level 4: 16 coins\n" +
//         "- Level 5: 32 coins\n\n" +

//         "b. Treasure Unlock:\n" +
//         "- 100 coins upon successfully unlocking the treasure.\n\n" +

//         "c. Hint Games:\n" +
//         "- Each Hint Game victory earns you 2 coins.\n\n" +

//         "4. Game Progression:\n\n" +
//         "Clear each level of Minesweeper, play the Hint Games, and gather coins.\n" +
//         "Use lifelines wisely, and wish on your luck for a wild card entry.\n" +
//         "Win all levels, gather hints, and enter the correct passcode to claim the treasure.\n\n" +

//         "Remember, strategy, memory, and a bit of luck are your keys to success in this challenging Treasure Hunt game! Good luck!\n\n");
//     }
// }

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;

public class Rules extends JFrame {
    private CountDownLatch gameLatch = new CountDownLatch(1);

    public Rules() {
        setTitle("Treasure Hunt Game Rules");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(680, 680);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout()); // Use BorderLayout to take advantage of CENTER for JScrollPane
        JTextArea rulesTextArea = new JTextArea();
        rulesTextArea.setEditable(false);
        rulesTextArea.setLineWrap(true); // Enable line wrapping
        rulesTextArea.setWrapStyleWord(true); // Wrap at word boundaries
        rulesTextArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        rulesTextArea.setText(getGameRules());

        JScrollPane scrollPane = new JScrollPane(rulesTextArea);
        getContentPane().add(scrollPane, BorderLayout.CENTER); // Add JScrollPane to the CENTER

        JButton playButton = new JButton("Start game");
        this.add(playButton, BorderLayout.SOUTH); // Add JButton to the SOUTH
        this.setVisible(true);

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Schedule a task to close the frame after a delay (in milliseconds)
                int delay = 3000; // 3000 milliseconds = 3 seconds
                Timer timer = new Timer(delay, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dispose(); // Close the frame
                        gameLatch.countDown();
                    }
                });

                timer.setRepeats(false); // Set to false to execute the ActionListener only once
                timer.start();
            }
        });

        try {
            // Wait for the Minesweeper game to complete
            gameLatch.await();
        } catch (InterruptedException err) {
            err.printStackTrace();
        }
    }

    private String getGameRules() {
        return(
        "Treasure Hunt Game Rules:\n\n" +

        "1. Minesweeper Rules:\n\n" +
        "a. Objective:\n" +
        "- Successfully clear all levels of Minesweeper to progress and unlock the treasure.\n\n" +
        
        "b. Grid and Mines:\n" +
        "- Level 1: 4x4 grid with 4 mines\n" +
        "- Level 2: 8x8 grid with 8 mines\n" +
        "- Level 3: 12x12 grid with 12 mines\n" +
        "- Level 4: 16x16 grid with 16 mines\n" +
        "- Level 5: 20x20 grid with 20 mines\n\n" +
        
        "c. Winning and Losing:\n" +
        "- Win each level to advance.\n" +
        "- Three lifelines: Reveal up to 3 mines per level.\n" +
        "- If the 4th mine is clicked, you lose the game.\n" +
        "- Hidden Wild card entry button available for immediate progression to the next level.\n\n" +
        
        "d. Passcode and Unlocking Treasure:\n" +
        "- Win all levels and enter the correct passcode to unlock the treasure.\n" +
        "- If you fail to enter the right passcode, you lose the game.\n\n" +

        "2. Hint Games:\n\n" +
        "a. Objective:\n" +
        "- Play after each Minesweeper level for a hint towards the passcode.\n\n" +
        
        "b. Available Games:\n" +
        "- Tic Tac Toe (Single player against the computer)\n" +
        "- Whac-A-Mole (Earn 10 points per mole, reach 100 points to win)\n" +
        "- Simon Says (Remember the order of colors for 8 levels)\n" +
        "- Hangman (Guess the word before the man is hanged)\n\n" +
        
        "c. Scoring:\n" +
        "- Winning each Hint Game earns you 2 coins and a hint.\n" +
        "- Tic Tac Toe: Beat the computer to win.\n" +
        "- Simon Says: Remember the color sequence for 7 levels.\n" +
        "- Whac-A-Mole: Click moles to earn 10 points on each click; avoid clicking plants before reaching 100 points.\n" +
        "- Hangman: Guess the word before the hangman is complete.\n\n" +

        "3. Scoring System:\n\n" +
        "a. Minesweeper Levels:\n" +
        "- Level 1: 2 coins\n" +
        "- Level 2: 4 coins\n" +
        "- Level 3: 8 coins\n" +
        "- Level 4: 16 coins\n" +
        "- Level 5: 32 coins\n\n" +

        "b. Treasure Unlock:\n" +
        "- 100 coins upon successfully unlocking the treasure.\n\n" +

        "c. Hint Games:\n" +
        "- Each Hint Game victory earns you 2 coins.\n\n" +

        "4. Game Progression:\n\n" +
        "Clear each level of Minesweeper, play the Hint Games, and gather coins.\n" +
        "Use lifelines wisely, and wish on your luck for a wild card entry.\n" +
        "Win all levels, gather hints, and enter the correct passcode to claim the treasure.\n\n" +

        "Remember, strategy, memory, and a bit of luck are your keys to success in this challenging Treasure Hunt game! Good luck!");
    }
}

