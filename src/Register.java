import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.util.concurrent.CountDownLatch;

public class Register {
    private CountDownLatch gameLatch = new CountDownLatch(1);
    JFrame frame;
    JPanel panel;
    JLabel taglinelabel;
    JLabel joinlabel;
    JLabel namelabel;
    JTextField nameTextField;
    JButton registerButton;
    String name;

    Register(){
        frame = new JFrame();
        frame.setTitle("Registration Page");
        frame.setSize(640, 640);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setForeground(Color.WHITE);

        JLabel label = new JLabel(); //Create a Label
        ImageIcon image = new ImageIcon("src\\chest.gif");
        label.setIcon(image); //Add Icon to Label

        taglinelabel = new JLabel("Become a Treasure Hunter, Claim the prize");
        taglinelabel.setFont(new Font("Arial", Font.BOLD, 30));

        joinlabel = new JLabel("Join the Hunt!!!");
        joinlabel.setFont(new Font("Arial", Font.BOLD, 16));

        namelabel = new JLabel();
        namelabel.setText(" Enter Name: ");
        namelabel.setFont(new Font("Arial", Font.PLAIN, 14));

        nameTextField = new JTextField(20);

        registerButton = new JButton("Submit");
        registerButton.addActionListener(e -> registerButtonClicked());

       
        panel.add(taglinelabel);
        panel.add(joinlabel);
        panel.add(namelabel);
        panel.add(nameTextField);
        panel.add(registerButton);
        panel.add(label);
        

        frame.add(panel);
        frame.setVisible(true);
        try {
            // Wait for the User to register
            gameLatch.await();
        } catch (InterruptedException err) {
            err.printStackTrace();
        }
    }

    private void registerButtonClicked() {
        // This method will be called when the "Register" button is clicked
        this.name  = nameTextField.getText();
        if (!this.name.isEmpty()) {
            DatabaseManager db = new DatabaseManager();
            db.createUser(this.name);
            db.closeConnection();
            frame.dispose();
            gameLatch.countDown();
        } else {
        // Handle the case where the user didn't enter a name
        JOptionPane.showMessageDialog(frame, "Please enter a valid name.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
