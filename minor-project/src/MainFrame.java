import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Analog Clock Frame");
        setSize(600, 600); // Increased height to accommodate the buttons
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create an instance of BeautifulAnalogClock
        BeautifulAnalogClock analogClock = new BeautifulAnalogClock();

        // Create a panel for the clock
        JPanel clockPanel = new JPanel();
        clockPanel.setLayout(new BorderLayout());
        clockPanel.add(analogClock, BorderLayout.CENTER);

        // Create a label to display time in letters
        JLabel timeLabel = new JLabel("", SwingConstants.CENTER);
        updateLetterTime(timeLabel); // Update time in letters
        clockPanel.add(timeLabel, BorderLayout.SOUTH);

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 4));

        // Create buttons and add them to the panel
        JButton button1 = new JButton("STOPWATCH");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // dispose();
                Stopwatch stopwatch = new Stopwatch();
            }
        });
        buttonPanel.add(button1);

        JButton button2 = new JButton("TIMER");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // JOptionPane.showMessageDialog(MainFrame.this, "Button 2 clicked!");
                TimerClockApp timerClockApp = new TimerClockApp();
            }
        });
        buttonPanel.add(button2);

        JButton button3 = new JButton("ALARM");
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // JOptionPane.showMessageDialog(MainFrame.this, "Button 3 clicked!");
                 new AlarmClock();
            }
        });
        buttonPanel.add(button3);

        JButton button4 = new JButton("REMAINDER");
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RemainderClock2();
            }
        });
        buttonPanel.add(button4);



        // Add clock panel and button panel to the content pane
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(clockPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Center the frame on the screen
        setLocationRelativeTo(null);
    }

    private void updateLetterTime(JLabel label) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        String timeString = convertToLetters(hour,minute);
        label.setSize(200,50);
        label.setText(timeString);
    }

    private String convertToLetters(int hour, int minute) {
        String hr = "";
        String min = "";
        if(hour < 10){
            hr = "0"+hour;
        }else{
            hr = ""+hour;
        }
        if(minute < 10){
            min = "0"+minute;
        }else{
            min = ""+minute;
        }
        return hr+":"+minute;
    }

    public static void main(String[] args) {
        // Create a new instance of MainFrame
        MainFrame frame = new MainFrame();

        // Make the frame visible
        frame.setVisible(true);
    }
}

