import java.awt.Color;
//import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

//import javax.swing.border.LineBorder;


public class DurgeshHangmanGUI extends JFrame implements ActionListener{
    private CountDownLatch gameLatch = new CountDownLatch(1);
    int flag = 0;
    static JLabel messaageLabel;
    static JLabel hintLabel;
    static JLabel wordStateLabel;
    static JLabel hangedManImage = new JLabel();
    static ImageIcon hangedManImageIcon;
    static JButton[] letterButtons = new JButton[26];
    static String word;
    static List<Character> wordState;
    static List<Character> wordLettersList;
    static int wrong = 0;
    static boolean gameOver = false;

        DurgeshHangmanGUI() throws FileNotFoundException {

        this.setTitle("Hangman Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(500, 500);
        this.getContentPane().setBackground(new Color(40, 40, 40));
        this.setResizable(false);
        this.isOpaque();

        // Reading the words from a file
        List<String> allWords = readWordsFromFile("src\\HangmanGame\\words.txt");

        // Message Label
        makeMessageLabel();
        
        // Hint Label
        makeHintLabel();

        // Hint to Guess the word
        word = getWordAndPrintHint(allWords);

        // Word to character list
        wordLettersList = getWordLettersList(word);

        // Initializing wordState
        wordState = initializeWordState(word);

        // Word State Label
        makeWordStateLabel();
        
        // The Hanged man game initial image
        hangedManImageIcon = new ImageIcon("src\\hangmanImages\\hangmanImages\\0.jpg");
        hangedManImage = new JLabel();
        hangedManImage.setBounds(330, 110, 150, 275);
        //hangedManImage.setBorder(BorderFactory.createLineBorder(Color.green, 2, true));
        hangedManImage.setHorizontalAlignment(JLabel.CENTER);
        hangedManImage.setIcon(hangedManImageIcon);

        // The Keyboard
        JPanel keyboardPanel = new JPanel();
        keyboardPanel.setLayout(new FlowLayout());
        keyboardPanel.setBounds(10, 225, 315, 220);
        //keyboardPanel.setBorder(BorderFactory.createLineBorder(Color.green, 2, false));
        //keyboardPanel.setBackground(Color.darkGray);
        keyboardPanel.setBackground(Color.darkGray);
        //hintLabel.setOpaque(true);
        
        for(int i = 0; i < 26; i++) {
            letterButtons[i] = new JButton("" + (char)('A' + i));
            
            // Set some formatting details
            letterButtons[i].setSize(10, 10);
            letterButtons[i].setFont(new Font("MV Boli", Font.PLAIN, 17));
            letterButtons[i].setFocusable(false);
            letterButtons[i].setBackground(Color.white);
            //letterButtons[i].setForeground(Color.white);
            //letterButtons[i].setBorder(BorderFactory.createLineBorder(new Color(30, 30, 30), 1, true));
            //letterButtons[i].setBorder(BorderFactory.createBevelBorder(0, null, null));

            // Add action listner
            letterButtons[i].addActionListener(this);
            
            // add the button to the panel
            keyboardPanel.add(letterButtons[i]);
        }

        this.add(messaageLabel);
        this.add(hintLabel);
        this.add(wordStateLabel);
        this.add(keyboardPanel);
        this.add(hangedManImage);
        setLocationRelativeTo(null);
        this.setVisible(true);
        try {
            // Wait for the Minesweeper game to complete
            gameLatch.await();
        } catch (InterruptedException err) {
            err.printStackTrace();
        }
    }

    private static List<String> readWordsFromFile(String filePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filePath));
        List <String> allWords = new ArrayList<>();

        //Make words upper case
        while (scanner.hasNext()) {
            allWords.add(scanner.nextLine().toUpperCase());
        }
        return allWords;
    }

    private static String getWordAndPrintHint(List<String> allWords) {
        
        Random random = new Random();
        int randomWordIndex = random.nextInt(allWords.size());
        String word = allWords.get(randomWordIndex);
        
        if (randomWordIndex < 15) 
            hintLabel.setText("Hint: Fruit");
        
        else if (randomWordIndex < 37)
            hintLabel.setText("Hint: Animal");
        
        else if (randomWordIndex < 50) 
            hintLabel.setText("Hint: Bird");
        
        else
            hintLabel.setText("Hint: Vegetable");

        return word;
    }

    private static List<Character> getWordLettersList(String word) {
        List <Character> wordCharacters = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            wordCharacters.add(word.charAt(i));
        }
        return wordCharacters;
    }

    private static List<Character> initializeWordState(String word) {
        List <Character> wordState = new ArrayList<>();
        
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) != ' ')
                wordState.add('-');
            else
                wordState.add(' ');
        }
        return wordState;
    }

    private static String wordStateToString(List<Character> wordState) {
        StringBuilder stringBuilder = new StringBuilder();
        // Appends characters one by one
        for (Character ch : wordState) {
            stringBuilder.append(ch);
        }

        // convert in string
        return stringBuilder.toString();
    }
    
    private static void makeMessageLabel() {
        messaageLabel = new JLabel();
        messaageLabel.setBounds(10,15,470, 50);
        messaageLabel.setHorizontalAlignment(JLabel.CENTER);
        messaageLabel.setVerticalAlignment(JLabel.CENTER);
        messaageLabel.setText("Messages will show here");
        //messaageLabel.setBorder(BorderFactory.createLineBorder(Color.green, 2, true));
        //messaageLabel.setBackground(new Color(52, 52, 52));
        //messaageLabel.setOpaque(true);
        messaageLabel.setFont(new Font("MV Boli", Font.PLAIN, 20));
        messaageLabel.setForeground(Color.white);
    }
    
    private static void makeHintLabel() {
        hintLabel = new JLabel();
        hintLabel.setBounds(10, 85, 300, 50);
        hintLabel.setHorizontalAlignment(JLabel.CENTER);
        hintLabel.setVerticalAlignment(JLabel.CENTER);
        //hintLabel.setBorder(BorderFactory.createLineBorder(Color.green, 2, true));
        //hintLabel.setBackground(new Color(55, 55, 55));
        //hintLabel.setOpaque(true);
        hintLabel.setFont(new Font("MV Boli", Font.PLAIN, 25));
        hintLabel.setForeground(Color.white);
    }

    private void makeWordStateLabel() {
        wordStateLabel = new JLabel();
        wordStateLabel.setBounds(10, 155, 300, 50);
        wordStateLabel.setHorizontalAlignment(JLabel.CENTER);
        wordStateLabel.setVerticalAlignment(JLabel.CENTER);
        //wordStateLabel.setBorder(BorderFactory.createLineBorder(Color.green, 2, true));
        //wordStateLabel.setBackground(new Color(55, 55, 55));
        //wordStateLabel.setOpaque(true);
        wordStateLabel.setFont(new Font("MV Boli", Font.PLAIN, 25));
        wordStateLabel.setForeground(Color.white);
        wordStateLabel.setText(wordStateToString(wordState));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!gameOver) {
            AbstractButton buttonClicked = (AbstractButton) e.getSource();
            char playerGuess = buttonClicked.getText().charAt(0);
            int buttonClickedIndex = playerGuess - 65;

            int correct = 0;
            // Update wordState
            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) == playerGuess) {
                    correct++;
                    wordState.set(i, playerGuess);
                }
            }
            
            // Update wordStateLabel
            wordStateLabel.setText(wordStateToString(wordState));

            // Update messageLabel, wrong and button
            if (correct > 0) {
                messaageLabel.setText(playerGuess + " is correct!");
                letterButtons[buttonClickedIndex].setEnabled(false);
                letterButtons[buttonClickedIndex].setBackground(Color.green);
            }

            else {
                wrong++;
                messaageLabel.setText(playerGuess + " is incorrect");
                letterButtons[buttonClickedIndex].setEnabled(false);
                letterButtons[buttonClickedIndex].setBackground(Color.red);
            }

            // Update hangmanImage
            String path = "src\\hangmanImages\\hangmanImages\\" + wrong + ".jpg";
            hangedManImageIcon = new ImageIcon(path);
            hangedManImage.setIcon(hangedManImageIcon);

            // Losing Condition
            if (wrong == 6) {
                messaageLabel.setText("RIP! The word was " + word);
                gameOver = true;
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

            // Winning Condition
            else if (wordStateToString(wordState).equals(word)) {
                messaageLabel.setText("Congratulations! You guessed the word");
                gameOver = true;
                flag = 1;
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
        }
    }
    int win(){
        if (flag == 1){
            return(1);
        }
        return(0);
    }
}
