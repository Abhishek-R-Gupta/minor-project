import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.Calendar;
import java.util.TimeZone;

public class BeautifulAnalogClock extends JPanel {
    private int hour;
    private int minute;
    private int second;

    public BeautifulAnalogClock() {
        Timer timer = new Timer(1000, e -> {
            updateTime();
            repaint();
        });
        timer.start();
    }

    private void updateTime() {
        // Get the current time
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        second = calendar.get(Calendar.SECOND);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Set anti-aliasing for smoother graphics
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int clockRadius = Math.min(centerX, centerY) - 10;

        // Draw clock face
        g2d.setColor(Color.WHITE);
        g2d.fillOval(centerX - clockRadius, centerY - clockRadius, 2 * clockRadius, 2 * clockRadius);

        // Draw hour markers
        g2d.setColor(Color.BLACK);
        for (int i = 0; i < 12; i++) {
            double angle = Math.toRadians(i * 30 - 90);
            int x1 = (int) (centerX + (clockRadius - 10) * Math.cos(angle));
            int y1 = (int) (centerY + (clockRadius - 10) * Math.sin(angle));
            int x2 = (int) (centerX + (clockRadius - 20) * Math.cos(angle));
            int y2 = (int) (centerY + (clockRadius - 20) * Math.sin(angle));
            g2d.setStroke(new BasicStroke(3));
            g2d.drawLine(x1, y1, x2, y2);
        }

        // Draw hour hand
        drawHand(g2d, centerX, centerY, hour * 30 + minute / 2, clockRadius * 0.5, 8, Color.BLACK);

        // Draw minute hand
        drawHand(g2d, centerX, centerY, minute * 6, clockRadius * 0.8, 6, Color.BLACK);

        // Draw smooth seconds hand
        double angle = Math.toRadians(second * 6 - 90);
        int x = (int) (centerX + (clockRadius - 30) * Math.cos(angle));
        int y = (int) (centerY + (clockRadius - 30) * Math.sin(angle));
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(centerX, centerY, x, y);

        // Draw center dot
        g2d.setColor(Color.BLACK);
        g2d.fillOval(centerX - 5, centerY - 5, 10, 10);
    }

    private void drawHand(Graphics2D g2d, int x, int y, double angle, double length, int width, Color color) {
        angle = Math.toRadians(angle - 90);
        double x2 = x + length * Math.cos(angle);
        double y2 = y + length * Math.sin(angle);
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.draw(new Line2D.Double(x, y, x2, y2));
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(300, 300);
    }

    // public static void main(String[] args) {
    //     JFrame frame = new JFrame("Beautiful Analog Clock");
    //     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //     frame.getContentPane().add(new BeautifulAnalogClock());
    //     frame.pack();
    //     frame.setVisible(true);
    // }
}

