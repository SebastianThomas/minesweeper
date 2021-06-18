import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public abstract class SweeperButton extends JButton {
    private boolean revealed;
    private boolean rightClicked;

    private MineSweeper game;
    private int x;
    private int y;
    private int width;
    private int height;

    // PARAM sweeper = MineSweeper to tell the main game that a bomb has been fired
    public SweeperButton(MineSweeper sweeper, int x, int y, int width, int height) {
        this.revealed = false;
        this.rightClicked = false;

        this.game = sweeper;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.setSize(this.width, this.height);
        this.setBackground(new Color(20, 20, 20));

        this.addActionListener(event -> {
            this.emitReveal();
        });
        this.addMouseListener(new SweeperButtonMouseAdapter(this));
    }

    public void emitReveal() {
        try {
            // If current button is not revealed, and the game is still running
            if (!this.revealed && !this.game.gameOver) {
                this.revealed = true;
                this.reveal();
                this.game.newReveal(this, this.x, this.y);
            }
        } catch (BombException exception) {
            game.bombFired(this.x, this.y);
            this.setForeground(Color.RED);
            this.setText("B");
        }
    }

    public void rightClick() {
        if (!rightClicked) {
            this.setForeground(Color.CYAN);
            this.setText("F");
            this.rightClicked = true;
        } else {
            this.setText("");
            this.rightClicked = false;
        }
    }

    public abstract void reveal() throws BombException;

    public abstract int getBombsAround() throws UnsupportedOperationException;
}

class EmptySweeperButton extends SweeperButton {
    private int bombsAround;

    public EmptySweeperButton(MineSweeper sweeper, int x, int y, int width, int height, int bombsAround) {
        super(sweeper, x, y, width, height);
        this.bombsAround = bombsAround;
    }

    @Override
    public void reveal() {
        this.setForeground(Color.LIGHT_GRAY);
        this.setText(this.bombsAround != 0 ? String.valueOf(this.bombsAround) : "");
    }

    @Override
    public int getBombsAround() {
        return this.bombsAround;
    }
}

class BombSweeperButton extends SweeperButton {
    public BombSweeperButton(MineSweeper sweeper, int x, int y, int width, int height) {
        super(sweeper, x, y, width, height);
    }

    @Override
    public void reveal() throws BombException {
        throw new BombException();
    }

    @Override
    public int getBombsAround() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
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
