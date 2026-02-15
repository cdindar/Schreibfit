package view;

import javax.swing.*;
import java.awt.*;

public class SpielView extends JPanel {
    public JButton backBtn = new JButton("Zurück");
    public JLabel fortschrittLabel = new JLabel("0/0", SwingConstants.RIGHT);

    public JLabel gemischtLabel = new JLabel("", SwingConstants.CENTER);
    public JLabel hinweisLabel = new JLabel("", SwingConstants.CENTER);

    public JTextField eingabeField = new JTextField();
    public JButton pruefenBtn = new JButton("Prüfen");
    public JLabel feedbackLabel = new JLabel(" ", SwingConstants.CENTER);

    public SpielView() {
        setLayout(new GridLayout(3, 1));
        setBackground(MainFrame.BEIGE);

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        backBtn.setBackground(Color.WHITE);
        top.add(backBtn, BorderLayout.WEST);

        fortschrittLabel.setForeground(Color.GRAY);
        fortschrittLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        top.add(fortschrittLabel, BorderLayout.EAST);

        gemischtLabel.setFont(new Font("Monospaced", Font.BOLD, 50));
        gemischtLabel.setForeground(new Color(0, 0, 255));
        hinweisLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        hinweisLabel.setForeground(Color.GRAY);

        JPanel center = new JPanel(new GridLayout(2, 1, 10, 10));
        center.setOpaque(false);
        center.setBorder(BorderFactory.createEmptyBorder(80, 120, 30, 120));
        center.add(gemischtLabel);
        center.add(hinweisLabel);

        JPanel bottom = new JPanel(new GridLayout(3, 1, 10, 10));
        bottom.setOpaque(false);
        bottom.setBorder(BorderFactory.createEmptyBorder(10, 200, 60, 200));

        JPanel inputRow = new JPanel(new BorderLayout(10, 0));
        inputRow.setOpaque(false);
        eingabeField.setPreferredSize(new Dimension(400, 32));
        inputRow.add(eingabeField, BorderLayout.CENTER);
        pruefenBtn.setBackground(Color.WHITE);
        inputRow.add(pruefenBtn, BorderLayout.EAST);

        feedbackLabel.setFont(new Font("SansSerif", Font.BOLD, 18));

        bottom.add(inputRow);
        bottom.add(feedbackLabel);
        bottom.add(new JLabel(" ", SwingConstants.CENTER));

        add(top);
        add(center);
        add(bottom);
    }
}
