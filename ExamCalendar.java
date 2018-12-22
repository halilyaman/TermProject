package se115.master;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class ExamCalendar {

    private JFrame tempMainFrame; // This provides to turn back safely to the Main Panel frame
    private JFrame examCalendarFrame;
    private JFrame addExamDateFrame;
    private JTextArea examDates;
    private JTextField lectureInput;
    private JTextArea dateInput;
    private int counter;

    public void setTempMainFrame(JFrame tempMainFrame) {
        this.tempMainFrame = tempMainFrame;
    }

    public void build() {
        examCalendarFrame = new JFrame("Exam Calendar");
        examCalendarFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel content = new JPanel();
        content.setBorder(new TitledBorder(new LineBorder(Color.blue,5),
                "<html><h1>Exam Calendar</h1></html>"));
        content.setLayout(new BorderLayout());

        Font boldFont = new Font("sanserif", Font.BOLD, 20);
        examDates = new JTextArea(10, 20);
        examDates.setEditable(false);
        examDates.setLineWrap(true);
        examDates.setWrapStyleWord(true);
        examDates.setFont(boldFont);
        printExamDates();
        JScrollPane scrollPane = new JScrollPane(examDates);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3,1));

        JButton addBtn = new JButton("Add Exam Date");
        JButton deleteBtn = new JButton("Delete Exam Date");
        JButton mainPageBtn = new JButton("Main Page");
        addBtn.addActionListener(new AddExamDateListener());
        deleteBtn.addActionListener(new DeleteExamDateListener());
        mainPageBtn.addActionListener(new MainPageListener());

        buttonPanel.add(addBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(mainPageBtn);

        content.add(scrollPane, BorderLayout.NORTH);
        content.add(buttonPanel, BorderLayout.CENTER);
        examCalendarFrame.add(content);
        examCalendarFrame.setSize(500, 450);
        examCalendarFrame.setLocationRelativeTo(null);
        examCalendarFrame.setVisible(true);
    }

    private class AddExamDateListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            counter = counterSetter();
            openAddPage();
        }
    }

    private class DeleteExamDateListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            deleteExamDate();
        }
    }

    private class MainPageListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            examCalendarFrame.setVisible(false);
            tempMainFrame.setVisible(true);
            tempMainFrame = null; // for GarbageCollector
        }
    }

    private void openAddPage() {
        addExamDateFrame = new JFrame();
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

        JLabel date = new JLabel("Date:");
        date.setFont(sanserif);

        dateInput = new JTextArea(2, 20);
        dateInput.setFont(monospace);
        dateInput.setLineWrap(true);
        dateInput.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(dateInput);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JButton saveBtn = new JButton("Save");
        saveBtn.addActionListener(new SaveButtonListener());

        downPanel.add(date, BorderLayout.NORTH);
        downPanel.add(scrollPane, BorderLayout.CENTER);
        downPanel.add(saveBtn, BorderLayout.SOUTH);

        addExamDateFrame.add(upPanel, BorderLayout.NORTH);
        addExamDateFrame.add(downPanel, BorderLayout.CENTER);
        addExamDateFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        addExamDateFrame.pack();
        addExamDateFrame.setLocationRelativeTo(null);
        addExamDateFrame.setVisible(true);
    }

    private class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            addExamDate();
        }
    }

    private void addExamDate() {
        try {
            FileWriter fileWriter = new FileWriter("exam_dates.txt", true);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            String lectureInfo = lectureInput.getText();
            String dateInfo = dateInput.getText();

            if (!dateInfo.equals("") && !lectureInfo.equals("")) {
                String task = counter + "- " + lectureInfo + "&&" + dateInfo + "\n";
                writer.write(task);
                writer.close();
                counter++;
            } else {
                JOptionPane.showMessageDialog(examCalendarFrame, "You must fill both Lecture and Date",
                        "Warning", JOptionPane.WARNING_MESSAGE);
                addExamDateFrame.setVisible(false);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        printExamDates();
        addExamDateFrame.setVisible(false);
    }

    private void printExamDates() {
        try {
            FileReader fileReader = new FileReader("exam_dates.txt");
            BufferedReader reader = new BufferedReader(fileReader);
            String task;
            String finalPart = "";
            while((task = reader.readLine()) != null) {
                if(task.length() > 0) {
                    String[] splittedInfo = task.split("&&");
                    if (splittedInfo.length == 2 && !splittedInfo[0].equals("") && !splittedInfo[1].equals("")) {
                        String lecturePart = splittedInfo[0].toUpperCase();
                        String datePart = splittedInfo[1];
                        finalPart = finalPart + lecturePart + "\n" + datePart + "\n\n";
                    } else {
                        String errorMessage = "System Failure..\nProgram will be closed :(";
                        JOptionPane.showMessageDialog(examCalendarFrame, errorMessage,
                                "System Error!", JOptionPane.ERROR_MESSAGE);
                        System.exit(0);
                    }
                }
            }
            reader.close();
            examDates.setText(finalPart);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void deleteExamDate() {
        try {
            counter = 1;
            BufferedReader reader = new BufferedReader(new FileReader("exam_dates.txt"));
            String row = null;
            ArrayList<String> wholeText = new ArrayList<>();
            String key = JOptionPane.showInputDialog(examCalendarFrame, "Please enter lecture number:",
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
            BufferedWriter writer = new BufferedWriter(new FileWriter("exam_dates.txt"));
            writer.close();

            orderDateList(wholeText);

            reader.close();
            printExamDates();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void orderDateList(ArrayList<String> wholeText) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("exam_dates.txt", true));
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
            BufferedReader reader = new BufferedReader(new FileReader("exam_dates.txt"));
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
