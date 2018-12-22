package se115.master;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class Assignments {

    private JFrame tempMainFrame; // This provides to turn back safely to the Main Panel frame
    private JFrame assignmentFrame;
    private JFrame addAssignmentFrame;
    private JTextArea targetBoard;
    private JTextField lectureInput;
    private JTextArea targetInput;
    private int counter;

    public void setTempMainFrame(JFrame tempMainFrame) {
        this.tempMainFrame = tempMainFrame;
    }

    public void build() {
        assignmentFrame = new JFrame("Assignment");
        assignmentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel content = new JPanel();
        content.setBorder(new TitledBorder(new LineBorder(Color.blue,5),
                "<html><h1>Assignments</h1></html>"));
        content.setLayout(new BorderLayout());

        Font boldFont = new Font("sanserif", Font.BOLD, 20);
        targetBoard = new JTextArea(10, 20);
        targetBoard.setEditable(false);
        targetBoard.setLineWrap(true);
        targetBoard.setWrapStyleWord(true);
        targetBoard.setFont(boldFont);
        printTasks();
        JScrollPane scrollPane = new JScrollPane(targetBoard);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3,1));

        JButton addBtn = new JButton("Add");
        JButton deleteBtn = new JButton("Delete");
        JButton mainPageBtn = new JButton("Main Page");
        addBtn.addActionListener(new AddAssignmentListener());
        deleteBtn.addActionListener(new DeleteAssignmentListener());
        mainPageBtn.addActionListener(new MainPageListener());

        buttonPanel.add(addBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(mainPageBtn);

        content.add(scrollPane, BorderLayout.NORTH);
        content.add(buttonPanel, BorderLayout.CENTER);
        assignmentFrame.add(content);
        assignmentFrame.setSize(500, 450);
        assignmentFrame.setLocationRelativeTo(null);
        assignmentFrame.setVisible(true);
    }

    private class AddAssignmentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            counter = counterSetter();
            openAddPage();
        }
    }

    private class DeleteAssignmentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            deleteTask();
        }
    }

    private class MainPageListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            assignmentFrame.setVisible(false);
            tempMainFrame.setVisible(true);
            tempMainFrame = null; // for GarbageCollector
        }
    }

    private void openAddPage() {
        addAssignmentFrame = new JFrame();
        JPanel upPanel = new JPanel();
        upPanel.setLayout(new BoxLayout(upPanel, BoxLayout.Y_AXIS));
        JPanel downPanel = new JPanel();
        downPanel.setLayout(new BorderLayout());

        Font sanserif = new Font("sanserif", Font.BOLD, 15);
        Font monospace = new Font("monospace", Font.BOLD, 15);
        JLabel lecture = new JLabel("Lecture:");
        lecture.setFont(sanserif);

        lectureInput = new JTextField();
        lectureInput.setFont(monospace);

        upPanel.add(lecture);
        upPanel.add(lectureInput);

        JLabel target = new JLabel("Target:");
        target.setFont(sanserif);

        targetInput = new JTextArea(10, 20);
        targetInput.setFont(monospace);
        targetInput.setLineWrap(true);
        targetInput.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(targetInput);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JButton saveBtn = new JButton("Save");
        saveBtn.addActionListener(new SaveButtonListener());

        downPanel.add(target, BorderLayout.NORTH);
        downPanel.add(scrollPane, BorderLayout.CENTER);
        downPanel.add(saveBtn, BorderLayout.SOUTH);

        addAssignmentFrame.add(upPanel, BorderLayout.NORTH);
        addAssignmentFrame.add(downPanel, BorderLayout.CENTER);
        addAssignmentFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        addAssignmentFrame.pack();
        addAssignmentFrame.setLocationRelativeTo(null);
        addAssignmentFrame.setVisible(true);
    }

    private class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            addTask();
        }
    }

    private void addTask() {
        try {
            FileWriter fileWriter = new FileWriter("assignments.txt", true);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            String lectureInfo = lectureInput.getText();
            String targetInfo = targetInput.getText();

            if (!targetInfo.equals("") && !lectureInfo.equals("")) {
                String task = counter + "- " + lectureInfo + "/" + targetInfo + "\n";
                writer.write(task);
                writer.close();
                counter++;
            } else {
                JOptionPane.showMessageDialog(assignmentFrame, "You must fill both Lecture and Target",
                        "Warning", JOptionPane.WARNING_MESSAGE);
                addAssignmentFrame.setVisible(false);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        printTasks();
        addAssignmentFrame.setVisible(false);
    }

    private void printTasks() {
        try {
            FileReader fileReader = new FileReader("assignments.txt");
            BufferedReader reader = new BufferedReader(fileReader);
            String task;
            String fullTask = "";
            while((task = reader.readLine()) != null) {
                if(task.length() > 0) {
                    String[] splittedTask = task.split("/");
                    if (splittedTask.length == 2 && !splittedTask[0].equals("") && !splittedTask[1].equals("")) {
                        String lecturePart = splittedTask[0].toUpperCase();
                        String definitionPart = splittedTask[1];
                        fullTask = fullTask + lecturePart + "\n" + definitionPart + "\n\n";
                    } else {
                        String errorMessage = "System Failure..\nProgram will be closed :(";
                        JOptionPane.showMessageDialog(assignmentFrame, errorMessage,
                                "System Error!", JOptionPane.ERROR_MESSAGE);
                        System.exit(0);
                    }
                }
            }
            reader.close();
            targetBoard.setText(fullTask);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void deleteTask() {
        try {
            counter = 1;
            BufferedReader reader = new BufferedReader(new FileReader("assignments.txt"));
            String row = null;
            ArrayList<String> wholeText = new ArrayList<>();
            String key = JOptionPane.showInputDialog(assignmentFrame, "Please enter assignment number",
                    "Delete", JOptionPane.INFORMATION_MESSAGE);
            while((row = reader.readLine()) != null) {
                if(row.length() > 1) {
                    if(!row.substring(0,1).equals(key)) {
                        row = counter + row.substring(1);
                        wholeText.add(row);
                        counter++;
                    }
                }
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter("assignments.txt"));
            writer.close();

            orderTaskList(wholeText);

            reader.close();
            printTasks();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void orderTaskList(ArrayList<String> wholeText) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("assignments.txt", true));
            for (String i : wholeText) {
                i = i + "\n";
                writer.write(i);
            }
            writer.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private int counterSetter() {
        int lastNum = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("assignments.txt"));
            String row = null;
            while((row = reader.readLine()) != null) {
                if(row.length() > 0) {
                    lastNum = Integer.parseInt(row.substring(0, 1));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lastNum + 1;
    }
}
