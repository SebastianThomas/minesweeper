package de.sth.minesweeper;

import de.sth.minesweeper.constants.ColorConstant;
import de.sth.minesweeper.difficulties.Difficulties;
import de.sth.minesweeper.difficulties.Difficulty;
import de.sth.minesweeper.difficulties.DifficultyChangeListener;
import de.sth.minesweeper.difficulties.DifficultyPanel;
import de.sth.minesweeper.fs.FileAccess;
import de.sth.minesweeper.logging.Logger;
import de.sth.minesweeper.settings.Settings;
import de.sth.minesweeper.settings.SettingsIO;
import de.sth.minesweeper.stats.StatisticPanel;
import de.sth.minesweeper.updates.UpdatePanel;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame implements DifficultyChangeListener {
    boolean revealFirstSelected;
    private Difficulty difficulty;
    private Difficulties selectedDifficulty;

    public MainMenu() {
        super("Minesweeper");

        FileAccess.init();
        Logger.getInstance().log("Creating Main Menu");

        Settings settings = SettingsIO.readSettings();
        this.revealFirstSelected = settings.isRevealFirstSelected();

        JPanel contentPanel = new JPanel();
        BoxLayout l = new BoxLayout(contentPanel, BoxLayout.Y_AXIS);
        contentPanel.setLayout(l);
        contentPanel.setBackground(ColorConstant.BG_Color);
        contentPanel.setForeground(ColorConstant.FG_Color);

        JLabel title = new JLabel("Minesweeper");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font(title.getFont().getFontName(), Font.BOLD, 32));
        title.setBackground(ColorConstant.BG_Color);
        title.setForeground(ColorConstant.FG_Color);
        contentPanel.add(title);

        JButton start = new JButton("START!");
        // Use the same font as in the title label
        start.setFont(new Font(title.getFont().getFontName(), Font.BOLD, 24));
        start.addActionListener(e -> {
            Logger.getInstance().log("Starting game; options: \n" +
                    "\t\t\tReveal first zero: " + this.revealFirstSelected + "; Bombs: " + this.difficulty.BOMBS);
            this.startGame();
        });
        start.setBackground(ColorConstant.BG_Color);
        start.setForeground(ColorConstant.FG_Color);
        start.setFocusable(false);

        JPanel startPanel = new JPanel();
        startPanel.add(start);
        startPanel.setBackground(ColorConstant.BG_Color);
        startPanel.setForeground(ColorConstant.FG_Color);
        contentPanel.add(startPanel);

        // OPTIONS
        // Heading
        JLabel optionsHeading = new JLabel("Optionen:", SwingConstants.CENTER);
        optionsHeading.setFont(new Font(title.getFont().getFontName(), Font.BOLD, 28));
        optionsHeading.setBackground(ColorConstant.BG_Color);
        optionsHeading.setForeground(ColorConstant.FG_Color);
        // Checkbox
        System.out.println(this.revealFirstSelected);
        JCheckBox revealFirstCheckBox = new JCheckBox("Eine zufällige 0 bereits aufdecken?", this.revealFirstSelected);
        revealFirstCheckBox.addActionListener(e -> this.revealFirstSelected = !this.revealFirstSelected);
        revealFirstCheckBox.setFocusable(false);
        revealFirstCheckBox.setBackground(ColorConstant.BG_Color);
        revealFirstCheckBox.setForeground(ColorConstant.FG_Color);
        // Difficulty
        DifficultyPanel diffPanel = new DifficultyPanel(settings.getSelectedDifficulty(), this);

        // Updates
        UpdatePanel updatePanel = new UpdatePanel();
        // Statics
        StatisticPanel statsPanel = new StatisticPanel();
        // Options Panel
        JPanel optionsPanel = new JPanel();
        optionsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.setBackground(ColorConstant.BG_Color);
        optionsPanel.setForeground(ColorConstant.FG_Color);
        // Add options
        optionsHeading.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsPanel.add(optionsHeading);
        revealFirstCheckBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsPanel.add(revealFirstCheckBox);
        diffPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsPanel.add(diffPanel);
        updatePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsPanel.add(updatePanel);
        statsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsPanel.add(statsPanel);

        contentPanel.add(optionsPanel);

        this.setContentPane(contentPanel);
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void startGame() {
        SettingsIO.writeSettings(this.revealFirstSelected, this.selectedDifficulty);
        this.dispose();
        MineSweeper.start(revealFirstSelected, this.difficulty);
    }

    @Override
    public void difficultyChanged(Difficulties newDifficulty) {
        Logger.getInstance().log("\t\t\tCurrently selected difficulty: " + newDifficulty.toString());
        this.selectedDifficulty = newDifficulty;
        this.difficulty = Difficulties.getDifficulty(newDifficulty);
    }
}
