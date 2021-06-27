package de.sth.minesweeper;

import de.sth.minesweeper.constants.ColorConstant;
import de.sth.minesweeper.updates.UpdatePanel;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {
    boolean revealFirstSelected = false;

    public MainMenu() {
        super("Minesweeper");

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
            this.dispose();
            MineSweeper.start(revealFirstSelected);
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
        JCheckBox revealFirstCheckBox = new JCheckBox("Eine zufällige 0 bereits aufdecken?", revealFirstSelected);
        revealFirstCheckBox.addActionListener(e -> revealFirstSelected = !revealFirstSelected);
        revealFirstCheckBox.setFocusable(false);
        revealFirstCheckBox.setBackground(ColorConstant.BG_Color);
        revealFirstCheckBox.setForeground(ColorConstant.FG_Color);
        // Updates
        UpdatePanel updatePanel = new UpdatePanel();
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
        updatePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsPanel.add(updatePanel);

        contentPanel.add(optionsPanel);

        this.setContentPane(contentPanel);
        this.setSize(500, 300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
