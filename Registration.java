package se115.master;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Registration {

    private JFrame frame = new JFrame("IUE");
    private String nameSurname = "null";
    private String number = "00000000000";
    private String department = "null";
    private JPanel content;
    private boolean isFull = true;

    /**
     * Getter and Setter methods
     */
    public boolean isFull() {
        return isFull;
    }

    public void setFull(boolean full) {
        isFull = full;
    }

    public String getNameSurname() {
        return nameSurname;
    }

    public String getNumber() {
        return number;
    }

    public String getDepartment() {
        return department;
    }

    /**
     * Adding panel with next page button
     */
    public void getContent() {

        content = new JPanel();
        content.setLayout(null);
        content.setBorder(new TitledBorder(new LineBorder(Color.green,5), ""));
        JButton saveBtn = new JButton("Save Information");
        saveBtn.setBounds(50,35,200,100);
        saveBtn.addActionListener(new SaveBtnListener());
        content.add(saveBtn);

    }

    /**
     * Getting information from user
     */
    public void inputNameSurname() {
        nameSurname = JOptionPane.showInputDialog(null,"Name and Surname","Info"
                , JOptionPane.QUESTION_MESSAGE);
    }
    public void inputNumber() {
        number = JOptionPane.showInputDialog(null,"School Number","Info"
                , JOptionPane.QUESTION_MESSAGE);
    }
    public void inputDepartment() {
        department = JOptionPane.showInputDialog(null,"Department","Info"
                , JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Creating a frame that contains next button
     */
    public void setRegisterFrame() {

        frame.setTitle("IEU");
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setSize(300,200);
        frame.setLocationRelativeTo(null);
        frame.add(content);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    /**
     * Inner class which provides actionPerformed method for Save Information button
     */
    private class SaveBtnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            frame.setVisible(false);
            MainPanel main = new MainPanel();
            main.setFrame();
            main.setInfo(nameSurname, number, department);
        }
    }
}