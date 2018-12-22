package se115.master;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class MainPanel {

    // give access to Schedule button for opening schedule window without loosing information
    public Schedule schedule = new Schedule();
    // give access to Assignments button for opening assignment window without loosing information
    public Assignments assignments = new Assignments();
    // give access to ExamCalendar button for opening exam calendar window without loosing information
    public ExamCalendar examCalendar = new ExamCalendar();
    final private JFrame frame = new JFrame("IUE");
    private JLabel nameLbl = new JLabel(), numberLbl = new JLabel(), departmentLbl = new JLabel();

    /**
     * This method transfer the user information
     * from Registration to MainPanel
     */
    public void setInfo(String name, String number, String department) {

        String space = "              ";

        this.nameLbl.setText(space + name);
        this.numberLbl.setText(space + number);
        this.departmentLbl.setText(space + department);
    }

    /**
     * Adding panel which holds schedule,
     * assignment and exam calendar buttons
     */
    private JPanel setContent() {

        JPanel content = new JPanel();
        content.setLayout(new GridLayout(7,1));
        content.setBorder(new TitledBorder(new LineBorder(Color.blue,5),
                "<html><h1>Main Panel</h1></html>"));

        JButton scheduleBtn = new JButton("Schedule");
        JButton assignmentBtn = new JButton("Assignments");
        JButton examCalendarBtn = new JButton("Exam Calendar");
        JButton exitBtn = new JButton("Exit");

        // Adding action to the buttons
        scheduleBtn.addActionListener(new ScheduleBtnListener());
        assignmentBtn.addActionListener(new AssignmentBtnListener());
        examCalendarBtn.addActionListener(new ExamCalendarBtnListener());
        exitBtn.addActionListener(new ExitBtnListener());

        // Adding information labels into content
        ArrayList<JLabel> labelHolder = new ArrayList<>(3);
        labelHolder.add(nameLbl);
        labelHolder.add(numberLbl);
        labelHolder.add(departmentLbl);
        for(JLabel i : labelHolder) {
            i.setFont(new Font("Serif", Font.BOLD, 30));
            content.add(i);
        }
        content.add(scheduleBtn);
        content.add(assignmentBtn);
        content.add(examCalendarBtn);
        content.add(exitBtn);

        return content;
    }

    /**
     * Adding frame that contains Main Panel content
     */
    public void setFrame() {
        frame.setName("IUE");
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setSize(500,490);
        frame.setLocationRelativeTo(null);
        frame.add(setContent());
        frame.setVisible(true);
    }

    /**
     * Inner class which provide action for Exit Button
     */
    private class ExitBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int option = JOptionPane.showOptionDialog(frame, "Are you sure?", "Message"
                    , JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if(option == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    /**
     * Inner class which provide action for Schedule Button
     */
    private class ScheduleBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.setVisible(false);
            schedule.setFrame();
            schedule.setTempMainFrame(frame);
        }
    }

    /**
     * Inner class which provide action for Exam Calendar Button
     */
    private class ExamCalendarBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.setVisible(false);
            examCalendar.build();
            examCalendar.setTempMainFrame(frame);
        }
    }

    /**
     * Inner class which provide action for Assignment Button
     */
    private class AssignmentBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.setVisible(false);
            assignments.build();
            assignments.setTempMainFrame(frame);
        }
    }
}
