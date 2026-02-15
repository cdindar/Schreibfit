package view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public static final Color BEIGE = new Color(245, 245, 220);

    private JPanel content = new JPanel(new BorderLayout());
    private JLabel scoreLabel = new JLabel("SCORE: 0");

    public MainFrame() {
        super("SchreibFit");
        setSize(1000, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new GridLayout(2, 1));
        root.setBackground(BEIGE);

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

        scoreLabel.setFont(new Font("Monospaced", Font.BOLD, 22));
        scoreLabel.setForeground(Color.DARK_GRAY);
        header.add(scoreLabel, BorderLayout.EAST);

        root.add(header, BorderLayout.NORTH);
        root.add(content, BorderLayout.CENTER);

        setContentPane(root);
    }

    public void setView(JPanel panel) {
        content.removeAll();
        content.add(panel, BorderLayout.CENTER);
        content.revalidate();
        content.repaint();
    }

    public void updateScore(int score) {
        scoreLabel.setText("SCORE: " + score);
    }
}
