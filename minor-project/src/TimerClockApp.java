//TIMER 
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimerClockApp extends JFrame {

    private JLabel clockLabel;
    private JLabel timerLabel;
    private Timer timer;
    private long timerStartTime; // Stores the timestamp when the timer started
    private long timerElapsed; // Stores the elapsed time when the timer was paused
    private boolean isTimerRunning = false;
    private boolean is24HourFormat = true; // Flag to track time format

    public TimerClockApp() {
        setTitle("Timer and Clock App");
        setLocation(450,100);
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1));
        addComponents();
        Timer clockTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateClock();
            }
        });
        clockTimer.start();
        setVisible(true);
    }

    private void addComponents() {
        // Add clock label
        clockLabel = new JLabel();
        clockLabel.setHorizontalAlignment(SwingConstants.CENTER);
        clockLabel.setFont(new Font("Arial", Font.BOLD, 36)); // Set font size here
        add(clockLabel);

        // Add timer label
        timerLabel = new JLabel("Timer: 00:00:00");
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 36)); // Set font size here
        add(timerLabel);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton startButton = new JButton("Start Timer");
        JButton stopButton = new JButton("Stop Timer");
        JButton resumeButton = new JButton("Resume Timer");
        JButton resetButton = new JButton("Reset Timer");
        JButton timeFormatButton = new JButton("Toggle Time Format");

        Font buttonFont = new Font("Arial", Font.PLAIN, 24); // Set font size for buttons
        startButton.setFont(buttonFont);
        stopButton.setFont(buttonFont);
        resumeButton.setFont(buttonFont);
        resetButton.setFont(buttonFont);
        timeFormatButton.setFont(buttonFont);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startTimer();
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopTimer();
            }
        });

        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resumeTimer();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetTimer();
            }
        });

        timeFormatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleTimeFormat();
            }
        });

        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(resumeButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(timeFormatButton);
        add(buttonPanel);
    }

    private void updateClock() {
        SimpleDateFormat sdf = new SimpleDateFormat(is24HourFormat ? "HH:mm:ss" : "hh:mm:ss a");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata")); // Indian time zone
        String currentTime = sdf.format(new Date());
        clockLabel.setText("Clock (Indian Time): " + currentTime);
    }

    private void startTimer() {
        String input = JOptionPane.showInputDialog("Enter duration (HH:MM:SS):");
        if (input != null) {
            String[] parts = input.split(":");
            if (parts.length == 3) {
                int hours = Integer.parseInt(parts[0]);
                int minutes = Integer.parseInt(parts[1]);
                int seconds = Integer.parseInt(parts[2]);

                long durationMillis = (hours * 3600 + minutes * 60 + seconds) * 1000;

                if (!isTimerRunning) {
                    isTimerRunning = true;
                    timerStartTime = System.currentTimeMillis() - timerElapsed; // Adjust start time for resume
                    timer = new Timer(1000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            long elapsedTime = System.currentTimeMillis() - timerStartTime;
                            long remainingTime = durationMillis - elapsedTime;
                            if (remainingTime > 0) {
                                long hours = (remainingTime / (1000 * 60 * 60)) % 24;
                                long minutes = (remainingTime / (1000 * 60)) % 60;
                                long seconds = (remainingTime / 1000) % 60;
                                String timerText = String.format("Timer: %02d:%02d:%02d", hours, minutes, seconds);
                                timerLabel.setText(timerText);
                            } else {
                                timer.stop();
                                timerElapsed = System.currentTimeMillis() - timerStartTime - durationMillis;
                                isTimerRunning = false;
                                JOptionPane.showMessageDialog(null, "Timer expired!");
                            }
                        }
                    });
                    timer.start();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid input! Please enter time in HH:MM:SS format.");
            }
        }
    }


    private void stopTimer() {
        if (isTimerRunning) {
            isTimerRunning = false;
            timer.stop();
            timerElapsed = System.currentTimeMillis() - timerStartTime;
        }
    }

    private void resumeTimer() {
        if (!isTimerRunning) {
            isTimerRunning = true;
            timerStartTime = System.currentTimeMillis() - timerElapsed; // Adjust start time for resume
            timer.start();
        }
    }

    private void resetTimer() {
        timerElapsed = 0;
        timerLabel.setText("Timer: 00:00:00");
    }

    private void toggleTimeFormat() {
        is24HourFormat = !is24HourFormat;
        updateClock(); // Update clock display with the new format
    }

    // public static void main(String[] args) {
    //     // SwingUtilities.invokeLater(new Runnable() {
    //     //     @Override
    //     //     public void run() {
    //     //         TimerClockApp app = new TimerClockApp();
    //     //         app.setVisible(true);
    //     //         Timer clockTimer = new Timer(1000, new ActionListener() {
    //     //             @Override
    //     //             public void actionPerformed(ActionEvent e) {
    //     //                 app.updateClock();
    //     //             }
    //     //         });
    //     //         clockTimer.start();
    //     //     }
    //     // });
    //     TimerClockApp timerClockApp = new TimerClockApp();
    // }
}