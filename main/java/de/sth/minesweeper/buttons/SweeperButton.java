package de.sth.minesweeper.buttons;

import de.sth.minesweeper.BombException;
import de.sth.minesweeper.MineSweeper;
import de.sth.minesweeper.constants.ColorConstant;
import de.sth.minesweeper.stats.GameStatistic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

public abstract class SweeperButton extends JButton {
    // "https://www.freepik.com"
    public static String BOMB_FILE_PATH = "bomb.png";
    // Vectors Market
    public static String FLAG_FILE_PATH = "flag_icon.png";

    protected boolean revealed;
    protected boolean rightClicked;

    protected GameStatistic stats;

    protected MineSweeper game;
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    // PARAM sweeper = de.sth.minesweeper.MineSweeper to tell the main game that a bomb has been fired
    public SweeperButton(MineSweeper sweeper, GameStatistic stats, int x, int y, int width, int height) {
        this.revealed = false;
        this.rightClicked = false;

        this.game = sweeper;
        this.stats = stats;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.setSize(this.width, this.height);
        this.setBackground((this.x + this.y) % 2 == 0 ? ColorConstant.BUTTON_UNREVEALED_LIGHT : ColorConstant.BUTTON_UNREVEALED_DARK);

        this.setFocusable(false);

        this.addActionListener(event -> {
            this.stats.incrementMoves();
            this.emitReveal();
        });
        this.addMouseListener(new SweeperButtonMouseAdapter(this));
    }

    public void emitReveal() {
        try {
            // If current button is not revealed, and the game is still running
            if (!this.revealed && !this.game.gameOver && !this.rightClicked) {
                this.revealed = true;
                this.setBackground((this.x + this.y) % 2 == 0 ? ColorConstant.BUTTON_REVEALED_LIGHT : ColorConstant.BUTTON_REVEALED_DARK);
                this.reveal();
                this.game.newReveal(this, this.x, this.y);
            }
        } catch (BombException exception) {
            game.bombFired(this.x, this.y);
            this.showIcon(Color.RED, BOMB_FILE_PATH);
        }
    }

    public void emitReveal(boolean gameOver) {
        if (gameOver && !this.revealed) {
            try {
                this.reveal(true);
            } catch (BombException exception) {
                this.showIcon(Color.ORANGE, BOMB_FILE_PATH);
            }
        }
    }

    private void showIcon(Color c, String filePath) {
        this.setForeground(c);

        this.setText("");
        // Show an icon:
        try {
            URL u = this.getClass().getResource(filePath);
            if (u == null) throw new NullPointerException("Image not found");
            ImageIcon icon = new ImageIcon(u);
            this.setIcon(icon);
        } catch (NullPointerException e) {
            this.setText(filePath.equals(FLAG_FILE_PATH) ? "B" : "F");
        }
    }

    private void removeIcon() {
        this.setIcon(null);
    }

    public void rightClick() {
        if (revealed) return;
        if (!rightClicked) {
            if (!(this.game.getFlagsLeft() > 0)) return;
            this.setForeground(Color.CYAN);

            // Do not set text
            // this.setText("F");
            // Instead: icon
            this.showIcon(Color.CYAN, FLAG_FILE_PATH);

            this.rightClicked = true;

            this.game.newRightClick(this);
        } else {
            this.removeIcon();
            this.rightClicked = false;
            this.game.removeRightClick(this);
        }
    }

    public void rightClick(boolean gameOver) {
        if (gameOver && !this.revealed) {
            // Do not set text
            // this.setText("F");
            // Instead: icon
            this.setText("");
            this.showIcon(Color.CYAN, FLAG_FILE_PATH);
        }
    }

    public abstract void reveal() throws BombException;

    public abstract void reveal(boolean gameOver) throws BombException;

    public abstract int getBombsAround() throws UnsupportedOperationException;
}

class SweeperButtonMouseAdapter implements MouseListener {
    SweeperButton button;

    public SweeperButtonMouseAdapter(SweeperButton button) {
        this.button = button;
    }

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) this.button.rightClick();
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {
    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {
    }
}
