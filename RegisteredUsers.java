package se115.master;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;

public class RegisteredUsers {

    private JFrame frame;
    private JPanel content;
    private JPanel newUsersPanel;
    private String previousUserName;

    public void build() {
        setName(readLine());
        frame = new JFrame("Registered User List");

        content = new JPanel();
        content.setLayout(new BorderLayout());
        content.setBorder(new TitledBorder(new LineBorder(Color.blue,5), "Registered Users"));

        newUsersPanel = new JPanel();

        JButton previousUserButton = new JButton("<html><h1>" + previousUserName + "</h1></html>");
        previousUserButton.addActionListener(new PreviousUserListener());

        JButton newUserButton = new JButton("New User");
        newUserButton.addActionListener(new NewUserListener());

        newUsersPanel.add(newUserButton);

        content.add(previousUserButton, BorderLayout.CENTER);
        content.add(newUsersPanel, BorderLayout.SOUTH);

        frame.add(content);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private class PreviousUserListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                frame.setVisible(false);
                MainPanel main = new MainPanel();
                String[] information = readLine();
                String nameSurname = information[0];
                String number = information[1];
                String department = information[2];
                main.setFrame();
                main.setInfo(nameSurname, number, department);
            } catch(ArrayIndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(frame, "System Error!\nPlease click New User"
                , "ERROR", JOptionPane.ERROR_MESSAGE);
                build();
            }
        }
    }

    private class NewUserListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            WelcomePanel.signUp(frame);
        }
    }

    private void setName(String[] seperatedLine) {
        previousUserName = seperatedLine[0];
    }

    private String[] readLine() {
        try {
            FileReader fileReader = new FileReader("users.txt");
            BufferedReader reader = new BufferedReader(fileReader);
            String[] seperatedLine;
            seperatedLine = reader.readLine().split("/");
            return seperatedLine;
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
