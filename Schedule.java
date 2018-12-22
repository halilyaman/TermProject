package se115.master;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Schedule {

    final private JFrame frame = new JFrame("IUE");
    private JFrame tempMainFrame; // This provides to turn back safely to the Main Panel frame
    public String[][] data = new String[12][6];
    private final String[] columns = {"<html><h5>Lectures</h5></html>",
            "<html><h5>Monday</h5></html>",
            "<html><h5>Tuesday</h5></html>",
            "<html><h5>Wednesday</h5></html>",
            "<html><h5>Thursday</h5></html>",
            "<html><h5>Friday</h5></html>"};

    public void setTempMainFrame(JFrame tempMainFrame) {
        this.tempMainFrame = tempMainFrame;
    }

    /**
     * Creating table and scroll pane which holds table
     */
    private JScrollPane makeTable(String[][] data, String[] columns) {
        JTable table = new JTable(data,columns);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true); // size issues
        return scrollPane;
    } // end of constructor

    /**
     * Adding panel which holds Schedule table, New Course, Delete Course and Main Menu buttons.
     */
    private JPanel getContent() {

        JPanel content = new JPanel();
        content.setLayout(new GridLayout(2,1));
        content.setBorder(new TitledBorder(new LineBorder(Color.blue,5),
                "<html><h1>Schedule</h1></html>"));

        // Creating panel to hold buttons
        JPanel botContent = new JPanel();
        botContent.setLayout(new GridLayout(3,1));

        // Creating button objects
        JButton newCourseBtn = new JButton("New Course");
        JButton deleteCourseBtn = new JButton("Delete Course");
        JButton backToMenu = new JButton("Main Page");

        // Adding action to the buttons
        newCourseBtn.addActionListener(new NewCourseListener());
        deleteCourseBtn.addActionListener(new DeleteCourseListener());
        backToMenu.addActionListener(new BackToMenuListener());

        // Adding buttons into the botContent panel
        botContent.add(newCourseBtn);
        botContent.add(deleteCourseBtn);
        botContent.add(backToMenu);

        content.add(makeTable(data, columns));
        content.add(botContent);

        return content;
    } // end of setContent method

    /**
     * Adding frame that contains Schedule content
     */
    public void setFrame() {
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setSize(650, 500);
        frame.setLocationRelativeTo(null); // this sets window on the middle of the screen
        frame.setContentPane(getContent());
        frame.setVisible(true);
    }

    /**
     * We will want user to input time for adding lecture
     * This method checks for time was written properly
     */
    public static boolean isTimeCorrect(String time) {

        String invalidChars = " ABCÇDEFGĞHIİJKLMNOÖPQRSŞTUÜVWXYZabcçdefgğhıijklmnoöpqrsştuüvwxyz" +
                "!#$%&'*+()-,./;<=>?@[]^_`{|}~";
        String validChars = "0123456789:";

        for (int i = 0; i < invalidChars.length(); i++) {
            if (time.contains(Character.toString(invalidChars.charAt(i)))
                    || !time.substring(2,3).equals(":")
                    || time.length() != 5) {
                return false;
            }
        }

        // if and only if time contains one of valid character then method will return true
        for(int i = 0; i < validChars.length(); i++) {
            if(time.contains(Character.toString(validChars.charAt(i)))) {
                return true;
            }
        }

        return false;
    }

    /**
     * This method takes four information from user
     * which are lecture code, days, start time and finish time
     * and sets those information into the table
     * Only lecture code can be written freely, others have some typing rules and restrictions
     */
    private void addLecture() {
        // creating frame and label for new window
        JFrame lectureInfoFrame = new JFrame("Lecture Info");
        JPanel lectureContent = new JPanel();
        lectureContent.setSize(450, 250);
        lectureContent.setLayout(new GridLayout(6,2));

        // lecture number input
        JLabel lectureNumLbl = new JLabel("Lecture Number(1-12):");
        lectureNumLbl.setBounds(110,10,150,25);
        JTextField lectureNumInput = new JTextField(15);
        lectureNumInput.setBounds(200,10,150,25);
        lectureNumLbl.setLabelFor(lectureNumInput);

        // lecture code input
        JLabel lectureCodeLbl = new JLabel("Lecture Code:");
        lectureCodeLbl.setBounds(110,10,150,25);
        JTextField lectureCodeInput = new JTextField(15);
        lectureCodeInput.setBounds(200,10,150,25);
        lectureCodeLbl.setLabelFor(lectureCodeInput);

        // day input
        JLabel dayLbl = new JLabel("Day:");
        dayLbl.setBounds(170,50,150,25);
        JTextField dayInput = new JTextField(15);
        dayInput.setBounds(200,50,150,25);
        lectureCodeLbl.setLabelFor(dayInput);

        // start time input
        JLabel startTimeLbl = new JLabel("Start Time (hh:mm):");
        startTimeLbl.setBounds(80,90,150,25);
        JTextField startTimeInput = new JTextField(15);
        startTimeInput.setBounds(200,90,150,25);
        lectureCodeLbl.setLabelFor(startTimeInput);

        // finish time input
        JLabel finishTimeLbl = new JLabel("Finish Time (hh:mm):");
        finishTimeLbl.setBounds(70,130,150,25);
        JTextField finishTimeInput = new JTextField(15);
        finishTimeInput.setBounds(200,130,150,25);
        lectureCodeLbl.setLabelFor(finishTimeInput);

        // save button
        JButton saveBtn = new JButton("Save");
        saveBtn.setBounds(200,170,80,40);
        saveBtn.addActionListener(
            e -> { // that lambda expression provides transferring data to table from this window
                boolean control = true;
                boolean wrong = true;
                String[] validDays = {"monday", "tuesday", "wednesday", "thursday", "friday"};
                try {
                    // next six variables hold data which is written in input areas
                    int lectureNum = Integer.parseInt(lectureNumInput.getText());
                    String lectureCode = lectureCodeInput.getText().toUpperCase();
                    String day = dayInput.getText();
                    String startTime = startTimeInput.getText();
                    String finishTime = finishTimeInput.getText();
                    String time = "(" + startTime + ")" + "-" + "(" + finishTime + ")";

                    // loop for writing time information where it belongs on table
                    for(int i = 1; i <= validDays.length; i++) {
                        // check whether day and time are valid or invalid
                        if(day.equalsIgnoreCase(validDays[i - 1])
                                && isTimeCorrect(startTime)
                                && isTimeCorrect(finishTime)) {
                            if(data[lectureNum - 1][0] == null) {
                                // lecture code and numbers are written to the table
                                data[lectureNum - 1][0] = (lectureNum) + "- " + lectureCode;
                                data[lectureNum - 1][i] = time; // start and finish times are written to the table
                                wrong = false;
                                break;
                            } else if (data[lectureNum - 1][i] == null &&
                                    lectureCode.equalsIgnoreCase(data[lectureNum - 1][0].substring(3))) {
                                // lecture code and numbers are written to the table
                                data[lectureNum - 1][0] = (lectureNum) + "- " + lectureCode;
                                data[lectureNum - 1][i] = time; // start and finish times are written to the table
                                wrong = false;
                                break;
                            }
                        }
                    }
                    if(wrong) { // if day or start time or finish time contains invalid character, error message will be existed
                        control = false;
                        JOptionPane.showMessageDialog(null, "Invalid input!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    if (control) { // if everything is okay, lectureInfoFrame will close.
                        lectureInfoFrame.setVisible(false);
                    }
                    frame.repaint(); // in Windows(I use OSX), frames are not updated automatically therefore i used this method

                } catch (ArrayIndexOutOfBoundsException ex) { // if lecture number was written wrong, error message will be shown
                    JOptionPane.showMessageDialog(null, "Lecture Number must be (1-12)!",
                            "ERROR",JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) { // if there is any other exception, error message will be shown
                    JOptionPane.showMessageDialog(null, "Invalid Input!","ERROR",
                            JOptionPane.ERROR_MESSAGE);
                }
            }); // end of lambda

        // Adding labels, input areas and save button into panel
        lectureContent.add(lectureNumLbl);
        lectureContent.add(lectureNumInput);
        lectureContent.add(lectureCodeLbl);
        lectureContent.add(lectureCodeInput);
        lectureContent.add(dayLbl);
        lectureContent.add(dayInput);
        lectureContent.add(startTimeLbl);
        lectureContent.add(startTimeInput);
        lectureContent.add(finishTimeLbl);
        lectureContent.add(finishTimeInput);
        lectureContent.add(new JLabel()); // for locating button at the right side
        lectureContent.add(saveBtn);

        // setting frame
        lectureInfoFrame.add(lectureContent); // Adding panel into the frame
        lectureInfoFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        lectureInfoFrame.setSize(450, 250); // Setting size for frame
        lectureInfoFrame.setLocationRelativeTo(null);
        lectureInfoFrame.getContentPane().add(lectureContent);
        lectureInfoFrame.setVisible(true);
    } // end of addLecture method

    /**
     * This method delete the chosen lecture
     * if there is no saved lecture it warns you
     * if you write lecture number which is not on that interval (1-12), it warns you again
     */
    private void deleteLecture(String index) {
        try {
            int intIndex = Integer.parseInt(index);
            if (data[intIndex-1][0] != null) {
                data[intIndex - 1][0] = null; // delete lecture code if it is not empty
            } else {
                String warning = String.format("You have no lecture at %d", intIndex);
                JOptionPane.showMessageDialog(null, warning, "WARNİNG",
                        JOptionPane.WARNING_MESSAGE);
            }
            for (int i = 1; i < 6; i++) { // this for loop iterates on day columns and checks for 'times'
                if (data[intIndex - 1][i] != null && data[intIndex - 1][i].contains(":")) {
                    data[intIndex - 1][i] = null; // delete time if it is not empty
                }
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            JOptionPane.showMessageDialog(null, "Enter between 1-12","ERROR",
                    JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Wrong Input!","ERROR",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Inner class which provide action for New Course Button
     */
    class NewCourseListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            addLecture();
        }
    }

    /**
     * Inner class which provide action for Delete Course Button
     */
    class DeleteCourseListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // read lecture code which will be deleted
            String index = JOptionPane.showInputDialog(null,
                    "Enter number of lecture that will be deleted","DELETE", JOptionPane.QUESTION_MESSAGE);
            deleteLecture(index);
            frame.repaint(); // in Windows(I use OSX), frames are not updated automatically therefore i used this method
        }
    }

    /**
     * Inner class which provide action for Main Menu Button
     */
    class BackToMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.setVisible(false);
            tempMainFrame.setVisible(true);
            tempMainFrame = null; // It makes tempMainFrame eligible for Garbage Collector.
        }
    }
} // end of Schedule class
