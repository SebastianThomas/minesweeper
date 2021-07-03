package de.sth.minesweeper.updates;

import de.sth.minesweeper.constants.ColorConstant;

import javax.swing.*;
import java.net.URL;

public class LoadingCircle extends JLabel {
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
