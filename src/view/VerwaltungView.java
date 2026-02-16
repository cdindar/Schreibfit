package view;

import javax.swing.*;
import java.awt.*;

public class VerwaltungView extends JPanel {
    public JList<String> liste = new JList<>();
    public DefaultListModel<String> listeModel = new DefaultListModel<>();

    public JComboBox<String> typBox = new JComboBox<>(new String[]{"TEXT", "IMAGE"});
    public JTextField frageField = new JTextField();
    public JTextField antwortField = new JTextField();
    public JComboBox<String> stufeBox = new JComboBox<>(new String[]{"Leicht", "Mittel", "Schwer"});

    public JButton neuBtn = new JButton("Neu");
    public JButton speichernBtn = new JButton("Speichern/Update");
    public JButton loeschenBtn = new JButton("Löschen");
    public JButton ladenBtn = new JButton("Datei laden");
    public JButton dateiSpeichernBtn = new JButton("Datei speichern");
    public JButton backBtn = new JButton("Zurück");

    public JLabel statusLabel = new JLabel(" ", SwingConstants.CENTER);

    public VerwaltungView() {
        setLayout(new BorderLayout());
        setBackground(MainFrame.BEIGE);

        JLabel title = new JLabel("Verwaltung", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 40));
        title.setForeground(Color.DARK_GRAY);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        JPanel mainContent = new JPanel(new GridLayout(1, 2, 30, 0));
        mainContent.setOpaque(false);
        mainContent.setBorder(BorderFactory.createEmptyBorder(0, 40, 20, 40));

        JPanel left = new JPanel(new BorderLayout(0, 10));
        left.setOpaque(false);
        JLabel lTitle = new JLabel("Fragenpool", SwingConstants.CENTER);
        lTitle.setFont(new Font("SansSerif", Font.BOLD, 18));

        liste.setModel(listeModel);
        JScrollPane scroll = new JScrollPane(liste);

        left.add(lTitle, BorderLayout.NORTH);
        left.add(scroll, BorderLayout.CENTER);

        JPanel right = new JPanel(new GridBagLayout()); // GridBagLayout für volle Kontrolle!
        right.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel rTitle = new JLabel("Bearbeiten", SwingConstants.CENTER);
        rTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 15, 0);
        right.add(rTitle, gbc);

        gbc.gridwidth = 1; gbc.insets = new Insets(5, 5, 5, 5);

        addFormField(right, "Typ:", typBox, 1, gbc);
        addFormField(right, "Frage/URL:", frageField, 2, gbc);
        addFormField(right, "Antwort:", antwortField, 3, gbc);
        addFormField(right, "Stufe:", stufeBox, 4, gbc);

        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 0, 10, 0);
        right.add(statusLabel, gbc);

        JPanel buttonGrid = new JPanel(new GridLayout(3, 2, 10, 10));
        buttonGrid.setOpaque(false);

        JButton[] allBtns = {neuBtn, speichernBtn, loeschenBtn, ladenBtn, dateiSpeichernBtn, backBtn};
        for (JButton b : allBtns) {
            b.setBackground(Color.WHITE);
            b.setFont(new Font("SansSerif", Font.BOLD, 14));
            b.setPreferredSize(new Dimension(0, 40)); // Hier erzwingen wir eine ordentliche Höhe!
            buttonGrid.add(b);
        }

        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        right.add(buttonGrid, gbc);

        mainContent.add(left);
        mainContent.add(right);
        add(mainContent, BorderLayout.CENTER);
    }

    private void addFormField(JPanel panel, String labelText, JComponent comp, int row, GridBagConstraints gbc) {
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        panel.add(new JLabel(labelText), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        comp.setPreferredSize(new Dimension(comp.getPreferredSize().width, 30));
        panel.add(comp, gbc);
    }

    public void setStatus(String text, boolean ok) {
        statusLabel.setText(text);
        statusLabel.setForeground(ok ? new Color(0, 255, 0) : Color.RED);
    }

    public void clearFields() {
        typBox.setSelectedIndex(0);
        frageField.setText("");
        antwortField.setText("");
        stufeBox.setSelectedIndex(0);
    }
}