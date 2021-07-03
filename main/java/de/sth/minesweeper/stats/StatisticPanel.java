package de.sth.minesweeper.stats;

import de.sth.minesweeper.constants.ColorConstant;
import de.sth.minesweeper.fs.JSONIOException;
import de.sth.minesweeper.updates.LoadingCircle;
import org.json.JSONException;

import javax.swing.*;
import java.awt.*;

public class StatisticPanel extends JPanel {
    private GameStatistic[] gameStatistic;

    public StatisticPanel() {
        super();

        BoxLayout x = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(x);

        this.setForeground(ColorConstant.FG_Color);
        this.setBackground(ColorConstant.BG_Color);

        JLabel headingLabel = new JLabel("Statistiken");
        headingLabel.setForeground(ColorConstant.FG_Color);
        headingLabel.setBackground(ColorConstant.BG_Color);
        headingLabel.setFont(new Font(headingLabel.getFont().getFontName(), Font.BOLD, 26));
        headingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(headingLabel);

        LoadingCircle l = new LoadingCircle();
        l.setForeground(ColorConstant.FG_Color);
        l.setBackground(ColorConstant.BG_Color);
        l.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(l);

        new Thread(() -> {
            try {
                this.gameStatistic = Statistics.getStats();

                // TODO: Do something with stats (Show, display)
            } catch (JSONException e) {
                l.setIcon(null);
                l.setText("Die Statistiken konnten nicht geladen werden. ");
            } catch (JSONIOException e) {
                l.setIcon(null);
                l.setText("Die Statistiken konnten nicht gefunden werden. ");
            }
        }).start();
    }
}
