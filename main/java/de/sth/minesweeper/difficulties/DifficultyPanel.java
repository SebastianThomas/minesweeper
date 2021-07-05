package de.sth.minesweeper.difficulties;

import de.sth.minesweeper.constants.ColorConstant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public class DifficultyPanel extends JPanel {
    // Logic
    private Difficulties difficulty;
    private DifficultyChangeListener listener;

    // GUI
    private JLabel headingLabel;
    private JRadioButton[] options;

    private ButtonGroup difficultyGroup;

    public DifficultyPanel(Difficulties difficulty, DifficultyChangeListener listener) {
        super();

        this.listener = listener;
        this.difficulty = difficulty;

        BoxLayout l = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(l);
        this.setBackground(ColorConstant.BG_Color);
        this.setForeground(ColorConstant.FG_Color);

        this.headingLabel = new JLabel("Schwierigkeit");
        this.headingLabel.setAlignmentX(CENTER_ALIGNMENT);
        this.headingLabel.setBackground(ColorConstant.BG_Color);
        this.headingLabel.setForeground(ColorConstant.FG_Color);
        this.headingLabel.setFont(new Font(this.headingLabel.getFont().getFontName(), Font.BOLD, 26));

        JPanel optionsPanel = new JPanel();
        optionsPanel.setBackground(ColorConstant.BG_Color);
        optionsPanel.setForeground(ColorConstant.FG_Color);
        this.difficultyGroup = new ButtonGroup();
        this.options = new JRadioButton[Difficulties.values().length];
        for (int i = 0; i < Difficulties.values().length; i++) {
            String buttonLabel = Difficulties.values()[i].toString();
            this.options[i] = new JRadioButton(buttonLabel);
            this.options[i].addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    this.setDifficulty(buttonLabel);
                }
            });
            this.options[i].setBackground(ColorConstant.BG_Color);
            this.options[i].setForeground(ColorConstant.FG_Color);

            this.difficultyGroup.add(this.options[i]);
        }
        for (JRadioButton r : this.options) optionsPanel.add(r);

        this.add(this.headingLabel);
        this.add(optionsPanel);

        Difficulties[] difficulties = Difficulties.values();
        for (int i = 0; i < difficulties.length; i++) {
            if (difficulties[i] == this.difficulty) {
                this.options[i].setSelected(true);
                break;
            }
        }
    }

    private void setDifficulty(String newDifficulty) {
        this.difficulty = Difficulties.valueOf(newDifficulty);
        this.listener.difficultyChanged(this.difficulty);
    }
}
