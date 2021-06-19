import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MineSweeper extends JPanel {
    public static final int ROWS = 24;
    public static final int COLS = 24;
    public static final int BOMBS = 99;

    public static final int BUTTON_WIDTH = 40;
    public static final int BUTTON_HEIGHT = 40;
    public boolean gameOver;

    private HashMap<Integer, SweeperButton> buttonMap;
    private boolean[][] field;
    private int revealed;
    private int nrOfRevealedFlags;
    private Set<SweeperButton> rightClicked;

    private TopPanel topPanel;

    public MineSweeper(TopPanel topPanel) {
        this.revealed = 0;
        this.nrOfRevealedFlags = 0;

        this.topPanel = topPanel;
        this.topPanel.setFlagsLeft(BOMBS);
        this.addKeyListener(new SweeperKeyboardAdapter(this));

        // Bombs
        this.field = new boolean[ROWS][COLS];
        for (int i = 0; i < BOMBS; i++) {
            int[] newPos = this.getNewPos();
            this.field[newPos[0]][newPos[1]] = true;
        }

        // Initialize Buttons
        this.buttonMap = new HashMap<>();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                int bombsAround = 0;

                // bombs around
                for (int row = -1; row < 2; row++) {
                    for (int col = -1; col < 2; col++) {
                        // Im Feld?
                        if (i + row >= 0 && j + col >= 0 && i + row < ROWS && j + col < COLS) {
                            if (this.field[i + row][j + col]) bombsAround++;
                        }
                    }
                }
                // If field == bomb then BombButton else EmptyButton
                buttonMap.put(i * COLS + j, this.field[i][j] ? new BombSweeperButton(this, i, j, BUTTON_WIDTH, BUTTON_HEIGHT) : new EmptySweeperButton(this, i, j, BUTTON_WIDTH, BUTTON_HEIGHT, bombsAround));
            }
        }

        this.setSize(500, 500);
        this.setLayout(new GridLayout(0, COLS));

        for (Map.Entry<Integer, SweeperButton> e : this.buttonMap.entrySet()) {
            this.add(e.getValue());
        }

        this.rightClicked = new HashSet<>();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();

        JPanel container = new JPanel();

        // Top Panel
        TopPanel topPanel = new TopPanel(frame);

        // Init game panel
        MineSweeper mineSweeperGame = new MineSweeper(topPanel);

        container.setLayout(new BorderLayout());
        container.add(topPanel, BorderLayout.NORTH);
        container.add(mineSweeperGame, BorderLayout.CENTER);

        // Init frame
        frame.getRootPane().setDefaultButton(topPanel.getStartAgainButton());

        frame.add(container);
        frame.setSize(1050, 1000);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    public int[] getNewPos() {
        int nr = (int) (Math.random() * ROWS * COLS);
        int y = nr % COLS;
        int x = (nr - y) / COLS;

        if (!this.field[x][y]) return new int[]{x, y};
        return getNewPos();
    }

    public void newReveal(SweeperButton button, int x, int y) {
        this.revealed++;
        if (ROWS * COLS - (BOMBS + this.revealed) <= 0) {
            this.revealAllFields();
            this.topPanel.gameOver(true);
        } else {
            this.revealSurroundingFields(button, x, y);
        }
    }

    public int getFlagsLeft() {
        return BOMBS - this.nrOfRevealedFlags;
    }

    public void newRightClick(SweeperButton button) {
        this.rightClicked.add(button);
        this.nrOfRevealedFlags++;
        this.topPanel.setFlagsLeft(BOMBS - this.nrOfRevealedFlags);
    }

    public void removeRightClick(SweeperButton toRemoveButton) {
        this.rightClicked = this.rightClicked.stream().filter(b -> b != toRemoveButton).collect(Collectors.toSet());
        this.nrOfRevealedFlags--;
        this.topPanel.setFlagsLeft(BOMBS - this.nrOfRevealedFlags);
    }

    public void bombFired(int x, int y) {
        this.gameOver = true;
        this.revealAllFields();
        this.setGameLostLabel();
    }

    private void revealSurroundingFields(SweeperButton pressedButton, int x, int y) {
        // If not bomb and no bombs around, reveal buttons around
        if (pressedButton instanceof BombSweeperButton || pressedButton.getBombsAround() != 0) return;

        for (int i = -1; i < 2; i++) {
            // Out of bounds
            if (x + i < 0 || x + i > ROWS - 1) continue;
            for (int j = -1; j < 2; j++) {
                // Out of bounds
                if (y + j < 0 || y + j > COLS - 1) continue;

                if (!this.field[x + i][y + j]) {
                    int buttonIndex = (x + i) * COLS + (y + j);
                    SweeperButton button = this.buttonMap.get(buttonIndex);

                    button.emitReveal();
                }
            }
        }
    }

    private void revealAllFields() {
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                int buttonIndex = (i) * COLS + (j);
                SweeperButton button = this.buttonMap.get(buttonIndex);

                button.emitReveal(true);
            }
        }

        SwitchFlagNrThread runnable = new SwitchFlagNrThread(this.rightClicked);
        Thread t = new Thread(runnable);

        t.start();
    }

    private void setGameLostLabel() {
        this.topPanel.gameOver(false);
    }

    public void enterPressed() {
        if (this.topPanel.isGameOver()) this.topPanel.startNewGame();
    }
}

class TopPanel extends JPanel {
    private JFrame frameToDispose;
    private JLabel label;
    private JButton startAgainButton;
    private boolean gameOver;
    private JLabel flagsLeft;

    public TopPanel(JFrame frameToDispose) {
        this.gameOver = false;

        this.setBackground(Color.BLACK);
        this.setForeground(Color.LIGHT_GRAY);

        this.frameToDispose = frameToDispose;

        this.label = new JLabel("Minesweeper");
        this.label.setFont(new Font(this.getFont().getFontName(), Font.BOLD, 24));
        this.label.setForeground(Color.LIGHT_GRAY);
        this.label.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 25));
        this.add(this.label);

        this.flagsLeft = new JLabel();
        this.flagsLeft.setFont(new Font(this.getFont().getFontName(), Font.BOLD, 20));
        this.flagsLeft.setForeground(Color.CYAN);
        this.label.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 50));
        this.add(this.flagsLeft);

        this.startAgainButton = new JButton("Erneut probieren?");
        this.startAgainButton.addActionListener(e -> {
            this.startNewGame();
        });
    }

    public void setFlagsLeft(int flagsLeft) {
        this.flagsLeft.setText("Flags left: " + flagsLeft);
    }

    public void gameOver(boolean won) {
        this.gameOver = true;

        if (won) {
            label.setForeground(new Color(0, 255, 0));
            label.setText("Sie haben gewonnen!");
        } else {
            label.setForeground(new Color(255, 0, 0));
            label.setText("Sie haben eine Bombe getroffen!");
        }

        this.add(this.startAgainButton);
    }

    public JButton getStartAgainButton() {
        return this.startAgainButton;
    }

    public void startNewGame() {
        frameToDispose.dispose();
        MineSweeper.main(new String[0]);
    }

    public boolean isGameOver() {
        return gameOver;
    }
}

class SweeperKeyboardAdapter implements KeyListener {
    private MineSweeper game;

    public SweeperKeyboardAdapter(MineSweeper game) {
        this.game = game;
    }

    /**
     * Invoked when a key has been typed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key typed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("Key typed");
    }

    /**
     * Invoked when a key has been pressed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key pressed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Key typed");
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            this.game.enterPressed();
        }
    }

    /**
     * Invoked when a key has been released.
     * See the class description for {@link KeyEvent} for a definition of
     * a key released event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("Key typed");
    }
}
