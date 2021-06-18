import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

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

    private TopPanel topPanel;

    public MineSweeper(TopPanel topPanel) {
        this.revealed = 0;

        this.topPanel = topPanel;

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
        // TODO
    }

    private void setGameLostLabel() {
        this.topPanel.gameOver(false);
    }
}

class TopPanel extends JPanel {
    private JFrame frameToDispose;
    private JLabel label;

    public TopPanel(JFrame frameToDispose) {
        this.frameToDispose = frameToDispose;
        this.label = new JLabel("Minesweeper");
        this.add(this.label);

        this.label.setFont(new Font(this.getFont().getFontName(), Font.BOLD, 24));
    }

    public void gameOver(boolean won) {
        if (won) {
            label.setForeground(new Color(0, 255, 0));
            label.setText("Sie haben gewonnen!");
        } else {
            label.setForeground(new Color(255, 0, 0));
            label.setText("Sie haben eine Bombe getroffen!");
        }

        JButton playAgainButton = new JButton("Erneut probieren?");
        playAgainButton.addActionListener(e -> {
            frameToDispose.dispose();
            MineSweeper.main(new String[0]);
        });
        this.add(playAgainButton);
    }
}
