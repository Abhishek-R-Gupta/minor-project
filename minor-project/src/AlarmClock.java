import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AlarmClock extends JFrame {
    private JLabel timeLabel;
    private JTextField alarmField;
    private JButton setAlarmButton;

    public AlarmClock() {
        setTitle("Alarm Clock");
        setSize(400, 400);
        setLocation(570, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        timeLabel = new JLabel();
        timeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        updateTime();
        add(timeLabel, BorderLayout.CENTER);

        JPanel alarmPanel = new JPanel(new FlowLayout());
        alarmField = new JTextField(10);
        setAlarmButton = new JButton("Set Alarm");
        setAlarmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setAlarm();
            }
        });
        alarmPanel.add(alarmField);
        alarmPanel.add(setAlarmButton);
        add(alarmPanel, BorderLayout.SOUTH);

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTime();
                checkAlarm();
            }
        });
        timer.start();
        setVisible(true);
    }




    private void updateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String currentTime = sdf.format(new Date());
        timeLabel.setText(currentTime);
    }

    private void setAlarm() {
        String alarmTime = alarmField.getText();
        JOptionPane.showMessageDialog(this, "Alarm is set for " + alarmTime, "Alarm Set", JOptionPane.INFORMATION_MESSAGE);
    }

    private void checkAlarm() {
        String alarmTime = alarmField.getText();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String currentTime = sdf.format(new Date());
        if (currentTime.equals(alarmTime)) {
            playRingtone();
            // JDialog dialog = jop.createDialog(this, "Wake up! It's time!", "Alarm", JOptionPane.INFORMATION_MESSAGE);

        }
    }


    private void playRingtone() {
        try {
            File soundFile = new File("src/Alarm_Sound.wav"); // Replace "Alarm_Sound.wav" with your audio file path
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AlarmClock();//.setVisible(true);
            }
        });
    }*/


}
