import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;

public class UnlockTreasure extends JFrame {
    private CountDownLatch gameLatch = new CountDownLatch(1);

    private JTextField passcodeField;
    int flag = 0;

    public UnlockTreasure(String[] hints) {

        JTextArea textArea = new JTextArea();
        textArea.append("\nYour Collected Hints:\n\n");
        for (String element : hints) {
            if(element != "")
                textArea.append(element + "\n");
        }

        // Set layout manager for the JFrame
        setLayout(new BorderLayout());
        
        setTitle("Treasure");
        setSize(480, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create components
        passcodeField = new JTextField(10);
        JButton submitButton = new JButton("Submit");

        // Set layout
        setLayout(new FlowLayout());

        // Add components to the frame
        add(new JLabel("Enter the passcode: "));
        add(passcodeField);
        add(submitButton);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        // Add action listener to the submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkPasscode();
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
    }

    private void checkPasscode() {
        String passcode = passcodeField.getText();

        // Replace this with your desired passcode
        String correctPasscode = "7391";

        // Check if the entered passcode is correct
        if (passcode.equals(correctPasscode)) {
            flag= 1;
        } 
    }
    // Method to make the frame visible
    public void showFrame() {
        setVisible(true);
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

