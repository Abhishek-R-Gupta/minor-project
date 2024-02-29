import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

public class RemainderClock2 extends JFrame {

    private JLabel currentTimeLabel;
    private DefaultListModel<Reminder> reminderListModel;
    private JList<Reminder> reminderList;
    private Timer timer;

    public RemainderClock2() {
        setTitle("Reminder Clock");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        currentTimeLabel = new JLabel();
        topPanel.add(currentTimeLabel);

        JPanel centerPanel = new JPanel(new BorderLayout());
        reminderListModel = new DefaultListModel<>();
        reminderList = new JList<>(reminderListModel);
        centerPanel.add(new JScrollPane(reminderList), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addButton = new JButton("Add Reminder");
        bottomPanel.add(addButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addReminder();
            }
        });

        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(centerPanel, BorderLayout.CENTER);
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateTime();
            }
        }, 0, 1000); // Update time every second

        setVisible(true);
    }

    private void updateTime() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LocalTime currentTime = LocalTime.now();
                String timeString = currentTime.toString();
                currentTimeLabel.setText(trimString(timeString));
                checkReminders(currentTime);
            }
        });
    }
    private String trimString(String str){
        return str.substring(0,8);
    }

    private void addReminder() {
        String timeString = JOptionPane.showInputDialog("Enter reminder time (HH:mm:ss):");
        LocalTime reminderTime = LocalTime.parse(timeString);
        String reminderName = JOptionPane.showInputDialog("Enter reminder name:");
        reminderListModel.addElement(new Reminder(reminderTime, reminderName));
        scheduleReminder(reminderTime);
    }

    private void scheduleReminder(LocalTime reminderTime) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                showReminderPopup(reminderTime);
            }
        }, Reminder.calculateDelay(reminderTime));
    }

    private void showReminderPopup(LocalTime reminderTime) {
        Reminder reminder = findReminder(reminderTime);
        if (reminder != null) {
            JOptionPane.showMessageDialog(this, "Reminder: " + reminder.getName(), "Reminder", JOptionPane.INFORMATION_MESSAGE);
            Toolkit.getDefaultToolkit().beep();
            reminderListModel.removeElement(reminder);
        }
    }

    private Reminder findReminder(LocalTime reminderTime) {
        for (int i = 0; i < reminderListModel.size(); i++) {
            Reminder reminder = reminderListModel.get(i);
            if (reminder.getTime().equals(reminderTime)) {
                return reminder;
            }
        }
        return null;
    }

    private void checkReminders(LocalTime currentTime) {
        for (int i = 0; i < reminderListModel.size(); i++) {
            Reminder reminder = reminderListModel.get(i);
            if (currentTime.equals(reminder.getTime())) {
                showReminderPopup(reminder.getTime());
            }
        }
    }

    private static class Reminder {
        private LocalTime time;
        private String name;

        public Reminder(LocalTime time, String name) {
            this.time = time;
            this.name = name;
        }

        public LocalTime getTime() {
            return time;
        }

        public String getName() {
            return name;
        }

        public static long calculateDelay(LocalTime reminderTime) {
            LocalTime now = LocalTime.now();
            if (now.isAfter(reminderTime)) {
                return 0; // Reminder time has already passed
            } else {
                return now.until(reminderTime, java.time.temporal.ChronoUnit.MILLIS);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RemainderClock2();
            }
        });
    }
}
