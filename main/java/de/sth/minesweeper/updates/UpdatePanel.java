package de.sth.minesweeper.updates;

import de.sth.minesweeper.constants.ColorConstant;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class UpdatePanel extends JPanel {
    LoadingCircle circle;
    JLabel headingLabel;
    JPanel bottomPanel;

    public UpdatePanel() {
        super();

        this.setSize(750, 150);
        this.setLayout(new GridLayout(0, 1));

        this.setBackground(ColorConstant.BG_Color);
        this.setForeground(ColorConstant.FG_Color);

        this.headingLabel = new JLabel("Update", SwingConstants.CENTER);
        this.headingLabel.setFont(new Font(this.headingLabel.getFont().getFontName(), Font.BOLD, 24));
        this.headingLabel.setForeground(ColorConstant.FG_Color);
        this.headingLabel.setBackground(ColorConstant.BG_Color);
        this.headingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(headingLabel);

        this.bottomPanel = new JPanel();
        this.bottomPanel.setForeground(ColorConstant.FG_Color);
        this.bottomPanel.setBackground(ColorConstant.BG_Color);

        this.circle = new LoadingCircle();
        this.bottomPanel.add(this.circle);
        this.add(bottomPanel);

        new Thread(() -> {
            Update update = Update.getUpdateOptions();
            if (update == null) this.showNoOptionGot();
            else this.resultFound(update);
        }).start();
    }

    public void resultFound(Update update) {
        System.out.println(update.option);
        this.showOption(update);
    }

    private void showNoOptionGot() {
        this.circle.setIcon(null);
        this.circle.setText("Bitte Internetverbindung prüfen!");
    }

    private void showOption(Update update) {
        String text = UpdateConstants.UpdateOptions.getTextFor(update.option);
        this.circle.setIcon(null);
        this.circle.setText(text);
        if (update.uri != null)
            this.bottomPanel.add(new LinkButton(update.ea ? "Early Access ausprobieren" : "Updaten", update.uri));
    }
}

class LoadingCircle extends JLabel {
    public LoadingCircle() {
        super("", CENTER);
        this.setForeground(ColorConstant.FG_Color);
        this.setBackground(ColorConstant.BG_Color);

        try {
            URL u = this.getClass().getResource("ajax-loader.gif");
            if (u == null) throw new NullPointerException("GIF not found");
            ImageIcon icon = new ImageIcon(u);
            this.setIcon(icon);
        } catch (NullPointerException e) {
            this.setText("Loading...");
        }
    }
}
