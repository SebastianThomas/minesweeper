package de.sth.minesweeper.timer;

import de.sth.minesweeper.constants.ColorConstant;

import javax.swing.*;
import java.awt.*;

public class TimerPanel extends JPanel {
    private JLabel label;
    private JLabel timer;
    private long startTime;

    public TimerPanel() {
        super();

        this.setForeground(ColorConstant.FG_Color);
        this.setBackground(ColorConstant.BG_Color);

        this.startTime = System.currentTimeMillis();

        this.label = new JLabel("Zeit: ");
        this.label.setForeground(ColorConstant.FG_Color);
        this.label.setBackground(ColorConstant.BG_Color);
        this.label.setFont(new Font(this.label.getFont().getFontName(), Font.ITALIC, 22));
        this.timer = new JLabel("00:00");
        this.timer.setForeground(ColorConstant.FG_Color);
        this.timer.setBackground(ColorConstant.BG_Color);
        this.timer.setFont(new Font(this.timer.getFont().getFontName(), Font.ITALIC, 22));

        this.add(this.label);
        this.add(this.timer);

        Timer t = new Timer(95, e -> {
            this.timer.setText(timerToString(System.currentTimeMillis()));
        });
        t.setRepeats(true);
        t.start();
    }

    public String timerToString(long time) {
        long ms = time - startTime;
        long totalSeconds = (ms - (ms % 1000)) / 1000;
        long seconds = totalSeconds % 60;
        long minutes = (totalSeconds - (totalSeconds % 60)) / 60;

        return getTrailingZero(minutes) + ":" + getTrailingZero(seconds);
    }

    public String getTrailingZero(long nr) {
        return nr < 10 ? "0" + nr : String.valueOf(nr);
    }
}
