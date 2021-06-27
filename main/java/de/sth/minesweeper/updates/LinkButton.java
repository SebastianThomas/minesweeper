package de.sth.minesweeper.updates;

import de.sth.minesweeper.constants.ColorConstant;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class LinkButton extends JButton {
    public LinkButton(String title, URI uri) {
        super(title);
        this.setForeground(ColorConstant.FG_Color);
        this.setBackground(ColorConstant.BG_Color);

        this.addActionListener(e -> {
            try {
                Desktop.getDesktop().browse(uri);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Bitte öffnen Sie diesen Link manuell: " + uri);
            }
        });
    }
}
