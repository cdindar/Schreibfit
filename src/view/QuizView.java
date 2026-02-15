package view;

import javax.swing.*;
import java.awt.*;

public class QuizView extends JPanel {
    public JButton backBtn = new JButton("Zurück");
    public JLabel fortschrittLabel = new JLabel("1/10", SwingConstants.RIGHT);

    public JLabel frageTextLabel = new JLabel("", SwingConstants.CENTER);

    public JTextField urlField = new JTextField();
    public JLabel bildLabel = new JLabel("", SwingConstants.CENTER);

    public JTextField antwortField = new JTextField();
    public JButton pruefenBtn = new JButton("Prüfen");
    public JLabel feedbackLabel = new JLabel(" ", SwingConstants.CENTER);

    public QuizView() {
        setLayout(new GridLayout(2, 1));
        setBackground(MainFrame.BEIGE);

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        backBtn.setPreferredSize(new Dimension(90, 28));
        top.add(backBtn, BorderLayout.WEST);

        fortschrittLabel.setForeground(Color.GRAY);
        fortschrittLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        top.add(fortschrittLabel, BorderLayout.EAST);

        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBorder(BorderFactory.createEmptyBorder(40, 60, 20, 60));

        frageTextLabel.setFont(new Font("Serif", Font.BOLD, 34));
        frageTextLabel.setForeground(Color.DARK_GRAY);
        frageTextLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        center.add(Box.createVerticalStrut(40));
        center.add(frageTextLabel);
        center.add(Box.createVerticalStrut(30));

        urlField.setFont(new Font("Monospaced", Font.PLAIN, 16));
        urlField.setEditable(false);
        urlField.setBackground(Color.WHITE);
        urlField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        urlField.setHorizontalAlignment(JTextField.CENTER);
        urlField.setMaximumSize(new Dimension(900, 34));
        urlField.setPreferredSize(new Dimension(700, 34));
        urlField.setVisible(false);
        center.add(urlField);
        center.add(Box.createVerticalStrut(15));

        bildLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        bildLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        bildLabel.setForeground(Color.DARK_GRAY);
        center.add(bildLabel);
        center.add(Box.createVerticalStrut(30));

        JPanel inputRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        inputRow.setOpaque(false);

        antwortField.setPreferredSize(new Dimension(650, 32));
        antwortField.setFont(new Font("SansSerif", Font.PLAIN, 18));

        pruefenBtn.setPreferredSize(new Dimension(90, 32));
        pruefenBtn.setFont(new Font("SansSerif", Font.BOLD, 14));

        inputRow.add(antwortField);
        inputRow.add(pruefenBtn);

        center.add(inputRow);
        center.add(Box.createVerticalStrut(20));

        feedbackLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        feedbackLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        center.add(feedbackLabel);

        add(top);
        add(center);
    }

    public void showUrl(String url) {
        bildLabel.setIcon(null);
        bildLabel.setText("");
        urlField.setText(url == null ? "" : url);
        urlField.setCaretPosition(0);
        urlField.setVisible(true);
        urlField.requestFocusInWindow();
        urlField.selectAll();
    }

    public void hideUrl() {
        urlField.setText("");
        urlField.setVisible(false);
    }
}