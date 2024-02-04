import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class LeaderBoard {
    JFrame frame;
    JTable table;
    JPanel topPanel;
    JPanel bottomPanel;
    String[] col;
    Object[][] data;
    JLabel lblabel;
    JLabel scorelabel;
    JButton closeButton;

    LeaderBoard(int win){

        frame = new JFrame("Leader Board");
        frame.setSize(680, 680);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        lblabel = new JLabel();
        lblabel.setText("LeaderBoard");
        lblabel.setHorizontalTextPosition(JLabel.CENTER);
        lblabel.setBackground(Color.WHITE);
        lblabel.setOpaque(true);
        lblabel.setFont(new Font("Roboto", Font.PLAIN, 30));

        scorelabel = new JLabel();
        String s;
        if(win==1){
            s= "You Won!!! \n";
        }
        else{
            s= "Better Luck Next Time!!! \n";
        }
        scorelabel.setText(s+ "Your Score: "+ App.score);
        scorelabel.setHorizontalTextPosition(JLabel.LEFT);
        scorelabel.setBackground(new Color(208, 240, 192));
        scorelabel.setOpaque(true);
        scorelabel.setFont(new Font("Roboto", Font.PLAIN, 20));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(scorelabel, BorderLayout.NORTH);
        topPanel.add(lblabel, BorderLayout.SOUTH);

        closeButton = new JButton("Close");
        closeButton.addActionListener(e -> System.exit(0));

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(closeButton);

        this.col = new String[]{"Rank", "Player Name", "Score"};
        this.data = getData();
        DefaultTableModel model = new DefaultTableModel(data, col){
            @Override
            public Class<?> getColumnClass(int column) {
                return getValueAt(0, column).getClass();
            }
        };

        // table = new JTable(model);
        // table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
        //     @Override
        //     public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        //         Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        //         int rank = (int) table.getValueAt(row, 0);

        //         switch (rank) {
        //             case 1:
        //                 comp.setBackground(new Color(255, 233, 147)); // Golden
        //                 break;
        //             case 2:
        //                 comp.setBackground(new Color(225, 225, 255)); // Silver
        //                 break;
        //             case 3:
        //                 comp.setBackground(new Color(218, 170, 94)); // Bronze
        //                 break;
        //             default:
        //                 comp.setBackground(table.getBackground());
        //                 break;
        //         }

        //         return comp;
        //     }
        // });

        table = new JTable(model) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component comp = super.prepareRenderer(renderer, row, column);

                int rank = (int) getValueAt(row, 0);

                switch (rank) {
                    case 1:
                        comp.setBackground(new Color(255, 233, 147)); // Golden
                        break;
                    case 2:
                        comp.setBackground(new Color(225, 225, 255)); // Silver
                        break;
                    case 3:
                        comp.setBackground(new Color(218, 170, 94)); // Bronze
                        break;
                    default:
                        comp.setBackground(getBackground());
                        break;
                }

                return comp;
            }
        };


        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    Object[][] getData(){
        try {
            DatabaseManager db = new DatabaseManager();
            ResultSet rs = db.readData();
            int count = 0;
            Object[][] data = new Object[5][3];
            
            while(rs.next() && count<5){
                data[count][0] = count+1;
                data[count][1] = rs.getString(1);
                data[count][2] = rs.getInt(2);
                count++;
            }
            return data;
        } catch (Exception e) {
            return null;
        }
    }
}
