package de.sth.minesweeper.stats;

import de.sth.minesweeper.constants.ColorConstant;
import de.sth.minesweeper.fs.JSONIOException;
import de.sth.minesweeper.timer.TimerPanel;
import de.sth.minesweeper.updates.LoadingCircle;
import org.json.JSONException;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import java.awt.*;
import java.util.Arrays;

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

                l.setIcon(null);
                l.setText(null);
                this.remove(l);
                this.displayStats();
            } catch (JSONException e) {
                l.setIcon(null);
                l.setText("Die Statistiken konnten nicht geladen werden. ");
            } catch (JSONIOException e) {
                l.setIcon(null);
                l.setText("Die Statistiken konnten nicht gefunden werden. ");
            }
        }).start();

        this.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
    }

    private void displayStats() {
        // Played games
        JLabel gamesLabel = new JLabel("Gespielt Spiele: " + this.gameStatistic.length, SwingConstants.CENTER);
        gamesLabel.setAlignmentX(CENTER_ALIGNMENT);
        gamesLabel.setForeground(ColorConstant.FG_Color);

        // Played time
        long playedTime = Arrays.stream(this.gameStatistic).mapToLong(g -> g.getEndingMillis() - g.getBeginningMillis()).sum();
        JLabel timeLabel = new JLabel(String.format("Gespielte Zeit: %s", TimerPanel.timerToString(System.currentTimeMillis(), System.currentTimeMillis() - playedTime)), SwingConstants.CENTER);
        timeLabel.setAlignmentX(CENTER_ALIGNMENT);
        timeLabel.setForeground(ColorConstant.FG_Color);

        // Moves
        long moves = Arrays.stream(this.gameStatistic).mapToLong(GameStatistic::getMoves).sum();
        // Revealed fields
        long revealedFields = Arrays.stream(this.gameStatistic).mapToLong(GameStatistic::getRevealedFields).sum();
        JLabel moveLabel = new JLabel(String.format("%d Felder in %d Zügen aufgedeckt", revealedFields, moves), SwingConstants.CENTER);
        moveLabel.setAlignmentX(CENTER_ALIGNMENT);
        moveLabel.setForeground(ColorConstant.FG_Color);

        this.add(gamesLabel);
        this.add(timeLabel);
        this.add(moveLabel);
    }

    /**
     * If the <code>preferredSize</code> has been set to a
     * non-<code>null</code> value just returns it.
     * If the UI delegate's <code>getPreferredSize</code>
     * method returns a non <code>null</code> value then return that;
     * otherwise defer to the component's layout manager.
     *
     * @return the value of the <code>preferredSize</code> property
     * @see #setPreferredSize
     * @see ComponentUI
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(super.getPreferredSize().width, 100);
    }
}
