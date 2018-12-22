package se115.master;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class WelcomePanel {

    private JFrame frame;
    private JPanel content;

    /**
     * Constructor is called automatically
     * when WelcomePanel object is initialized.
     * It contains content pane, information placed on pane
     * and register button.
     */
    public WelcomePanel() {

        // Outer panel
        content = new JPanel();
        content.setBorder(new TitledBorder(new LineBorder(Color.blue,5),
                "<html><h1>Student Control Panel</h1></html>"));
        content.setLayout(null);

        // Messages that will be printed on page
        String welcomeMessage1 = "          Welcome to Student Console\n";
        String welcomeMessage2 = "You can create things below:\n";
        String welcomeMessage3 = "Schedule, Assignments and Exam Dates\n";
        String welcomeMessage4 = "          Please click the Register button";

        // Creating new Panel for messages
        JPanel msgPanel = new JPanel();
        msgPanel.setBounds(25,50,550,300);
        msgPanel.setLayout(new GridLayout(3,1));
        String[] messageHolder = {welcomeMessage1, welcomeMessage2, welcomeMessage3, welcomeMessage4};
        JLabel row1 = new JLabel();
        JLabel row2 = new JLabel();
        JLabel row3 = new JLabel();
        JLabel row4 = new JLabel();
        JLabel[] labelHolder = {row1,row2,row3,row4};

        // Panel for providing gray area to seem better
        JPanel grayPanel = new JPanel();
        grayPanel.setBackground(Color.gray);
        grayPanel.setLayout(new GridLayout(2,1));


        // Adding message on the page
        int counter = 0;
        for (JLabel j : labelHolder) {
            if(counter == 1 || counter == 2) {
                j.setText(messageHolder[counter]);
                j.setFont(new Font("Serif", Font.BOLD, 30));
                grayPanel.add(j);
                counter++;
                continue;
            } else {
                j.setText(messageHolder[counter]);
                j.setFont(new Font("Serif", Font.BOLD, 30));
                msgPanel.add(j);
            }
            counter++;
            if(counter == 1) {
                msgPanel.add(grayPanel);
            }
        }
        content.add(msgPanel);

        // Adding register button
        JPanel btnPanel = new JPanel();
        btnPanel.setBounds(245, 400, 110, 50);
        btnPanel.setLayout(null);
        Color myColor = new Color(255,70, 0);
        btnPanel.setBackground(myColor);
        JButton registerBtn = new JButton("Register");
        registerBtn.setBounds(5,5,100,40);
        registerBtn.addActionListener(new RegisterBtnListener()); // Adding action when the button is clicked
        btnPanel.add(registerBtn);
        content.add(btnPanel);

    }

    /**
     * Creating a frame that contains content pane.
     */
    void getWelcomeFrame() {

        frame = new JFrame("IEU");
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setContentPane(content);
        frame.setVisible(true);
    }

    /**
     * Check string.
     * If there is number in string, return false;
     * if there is no number, return true.
     */
    public static boolean isStringCorrect(String word) {

        String invalidChars = "0123456789!#$%&'()*+,-./:;<=>?@[]^_`{|}~";
        String validChars = "ABCÇDEFGĞHIİJKLMNOÖPQRSŞTUÜVWXYZabcçdefgğhıijklmnoöpqrsştuüvwxyz";

        for (int i = 0; i < invalidChars.length(); i++) {

            if (word.contains(Character.toString(invalidChars.charAt(i)))) {
                return false;
            }
        }

        // if and only if word contains one of valid character then method will return true
        for(int i = 0; i < validChars.length(); i++) {

            if(word.contains(Character.toString(validChars.charAt(i)))) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checking number if it has a string.
     * If there is a string in numbers, return false;
     * if there is no string, return true.
     */
    public static boolean isNumberCorrect(String number) {

        String invalidChars = " !#$%&'()*+,-./:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[]^_`abcdefghijklmnopqrstuvwxyz{|}~";

        for (int i = 0; i < invalidChars.length(); i++) {

            if (number.contains(Character.toString(invalidChars.charAt(i)))) {
                return false;
            }
        }

        return true;
    }

    /**
     * Check registration inputs.
     * if there is empty input, return false;
     * if there is no empty input, return true.
     */
    public static boolean controlRegInfo(String data) {

        if(data.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Inner class which provide action for Register button
     */
    private class RegisterBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            frame.setVisible(false);
            Registration r = new Registration();

            // Checking whether there is any missing information or not
            int counter1 = 0;
            int counter2 = 0;
            int counter3 = 0;
            while (r.isFull()) {
                    if(counter1 == 0) {
                        r.inputNameSurname();
                        if (!controlRegInfo(r.getNameSurname())) {
                            JOptionPane.showMessageDialog(null, "Name Surname is Empty"
                                    , "Error Message", JOptionPane.WARNING_MESSAGE);
                            continue;
                        } else if (!isStringCorrect(r.getNameSurname())) {
                            JOptionPane.showMessageDialog(null, "Name Surname is Invalid"
                                    , "Error Message", JOptionPane.WARNING_MESSAGE);
                            continue;
                        }
                    }
                    counter1 = 1;
                    if(counter2 == 0) {
                        r.inputNumber();
                        if (!isNumberCorrect(r.getNumber())) {
                            JOptionPane.showMessageDialog(null, "Number is Invalid"
                                    , "Error Message", JOptionPane.WARNING_MESSAGE);
                            continue;
                        } else if (!controlRegInfo(r.getNumber())) {
                            JOptionPane.showMessageDialog(null, "Number is Empty"
                                    , "Error Message", JOptionPane.WARNING_MESSAGE);
                            continue;
                        }
                    }
                    counter2 = 1;
                    if(counter3 == 0) {
                        r.inputDepartment();
                        if (!controlRegInfo(r.getDepartment())) {
                            JOptionPane.showMessageDialog(null, "Department is Empty"
                                    , "Error Message", JOptionPane.WARNING_MESSAGE);
                            continue;
                        } else if (!isStringCorrect(r.getDepartment())) {
                            JOptionPane.showMessageDialog(null, "Department is Invalid"
                                    , "Error Message", JOptionPane.WARNING_MESSAGE);
                            continue;
                        }
                    }
                counter3 = 1;
                    r.setFull(false); // Stop while loop
            }
            r.getContent();
            r.setRegisterFrame();
        }
    }
}