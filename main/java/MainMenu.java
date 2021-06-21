import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {
    boolean revealFirstSelected = false;

    public MainMenu() {
        super("Minesweeper");

        JPanel contentPanel = new JPanel(new GridLayout(0, 1));
        contentPanel.setBackground(Color.BLACK);
        contentPanel.setForeground(Color.WHITE);

        JLabel title = new JLabel("Minesweeper", SwingConstants.CENTER);
        title.setFont(new Font(title.getFont().getFontName(), Font.BOLD, 32));
        title.setBackground(Color.BLACK);
        title.setForeground(Color.WHITE);
        contentPanel.add(title);

        JButton start = new JButton("START!");
        // Use the same font as in the title label
        start.setFont(new Font(title.getFont().getFontName(), Font.BOLD, 24));
        start.addActionListener(e -> {
            this.dispose();
            MineSweeper.start(revealFirstSelected);
        });
        start.setBackground(Color.BLACK);
        start.setForeground(Color.WHITE);
        start.setFocusable(false);

        JPanel startPanel = new JPanel();
        startPanel.add(start);
        startPanel.setBackground(Color.BLACK);
        startPanel.setForeground(Color.WHITE);
        contentPanel.add(startPanel);

        // OPTIONS
        // Heading
        JLabel optionsHeading = new JLabel("Optionen:", SwingConstants.CENTER);
        optionsHeading.setFont(new Font(title.getFont().getFontName(), Font.BOLD, 28));
        optionsHeading.setBackground(Color.BLACK);
        optionsHeading.setForeground(Color.WHITE);
        // Checkbox
        JCheckBox revealFirstCheckBox = new JCheckBox("Eine zufällige 0 bereits aufdecken?", revealFirstSelected);
        revealFirstCheckBox.addActionListener(e -> {
            revealFirstSelected = !revealFirstSelected;
            System.out.println(revealFirstSelected);
        });
        revealFirstCheckBox.setFocusable(false);
        revealFirstCheckBox.setBackground(Color.BLACK);
        revealFirstCheckBox.setForeground(Color.WHITE);
        // Options Panel
        JPanel optionsPanel = new JPanel();
        optionsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.setBackground(Color.BLACK);
        optionsPanel.setForeground(Color.WHITE);
        // Add options
        optionsHeading.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsPanel.add(optionsHeading);
        revealFirstCheckBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsPanel.add(revealFirstCheckBox);

        contentPanel.add(optionsPanel);

        this.setContentPane(contentPanel);
        this.setLocationRelativeTo(null);
        this.setSize(500, 250);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
