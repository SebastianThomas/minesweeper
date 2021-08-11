package de.sth.minesweeper.solving;

import de.sth.minesweeper.BombException;
import de.sth.minesweeper.buttons.SweeperButton;
import de.sth.minesweeper.logging.Logger;

import javax.swing.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class PossibleReveals {
    // Whether there must be bombs or not
    private boolean bombs;
    private ArrayList<SweeperButton> buttons;

    /**
     * @param bombs   Whether the fields are bombs (for sure) or empty (for sure)
     * @param buttons List of {@code SweeperButtons} to be revealed / right clicked
     */
    public PossibleReveals(boolean bombs, ArrayList<SweeperButton> buttons) {
        this.bombs = bombs;
        this.buttons = buttons;
    }

    public void revealAll() throws UnsupportedOperationException {
        if (this.bombs) throw new UnsupportedOperationException("Must not reveal bombs!");
        for (SweeperButton button : this.buttons) {
            try {
                button.reveal();
            } catch (BombException e) {
                Logger.getInstance().log("REVEALED A BOMB BY AI..????");
                JOptionPane.showMessageDialog(null, "Irgendetwas ist schief gelaufen... Bitte versuchen Sie es erneut.");
                System.exit(0);
            }
        }
    }

    /**
     * Returns a string representation of the object. In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p>
     * The {@code toString} method for class {@code Object}
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `{@code @}', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return this.buttons.stream().map(SweeperButton::toString).collect(Collectors.joining(";\n"));
    }
}
