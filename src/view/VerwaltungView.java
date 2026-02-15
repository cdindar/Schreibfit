package view;

import javax.swing.*;
import java.awt.*;

public class VerwaltungView extends JPanel {
    public JList<String> liste = new JList<String>();
    public DefaultListModel<String> listeModel = new DefaultListModel<String>();

    public JComboBox<String> typBox = new JComboBox<String>(new String[]{"TEXT", "IMAGE"});
    public JTextField frageField = new JTextField();
    public JTextField antwortField = new JTextField();
    public JComboBox<String> stufeBox = new JComboBox<String>(new String[]{"Leicht", "Mittel", "Schwer"});

    public JButton neuBtn = new JButton("Neu");
    public JButton speichernBtn = new JButton("Speichern/Update");
    public JButton loeschenBtn = new JButton("Löschen");
    public JButton ladenBtn = new JButton("Datei laden");
    public JButton dateiSpeichernBtn = new JButton("Datei speichern");
    public JButton backBtn = new JButton("Zurück");

    public JLabel statusLabel = new JLabel(" ", SwingConstants.CENTER);

    public VerwaltungView() {
        setLayout(new GridLayout(2, 1));
        setBackground(MainFrame.BEIGE);

        JLabel title = new JLabel("Verwaltung", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 40));
        title.setForeground(Color.DARK_GRAY);
        title.setBorder(BorderFactory.createEmptyBorder(30, 20, 10, 20));

        JPanel center = new JPanel(new GridLayout(1, 2, 15, 15));
        center.setOpaque(false);
        center.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        liste.setModel(listeModel);
        JScrollPane scroll = new JScrollPane(liste);

        JPanel left = new JPanel(new BorderLayout());
        left.setOpaque(false);
        JLabel lTitle = new JLabel("Fragenpool", SwingConstants.CENTER);
        lTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        left.add(lTitle, BorderLayout.NORTH);
        left.add(scroll, BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(8, 1, 8, 8));
        form.setOpaque(false);

        JPanel r1 = row("Typ:", typBox);
        JPanel r2 = row("Frage/URL:", frageField);
        JPanel r3 = row("Antwort:", antwortField);
        JPanel r4 = row("Stufe:", stufeBox);

        frageField.setPreferredSize(new Dimension(360, 28));
        antwortField.setPreferredSize(new Dimension(360, 28));

        JPanel actions = new JPanel(new GridLayout(3, 2, 10, 10));
        actions.setOpaque(false);

        neuBtn.setBackground(Color.WHITE);
        speichernBtn.setBackground(Color.WHITE);
        loeschenBtn.setBackground(Color.WHITE);
        ladenBtn.setBackground(Color.WHITE);
        dateiSpeichernBtn.setBackground(Color.WHITE);
        backBtn.setBackground(Color.WHITE);

        actions.add(neuBtn);
        actions.add(speichernBtn);
        actions.add(loeschenBtn);
        actions.add(ladenBtn);
        actions.add(dateiSpeichernBtn);
        actions.add(backBtn);

        statusLabel.setForeground(new Color(255, 0, 0));
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        form.add(r1);
        form.add(r2);
        form.add(r3);
        form.add(r4);
        form.add(new JLabel(" ", SwingConstants.CENTER));
        form.add(actions);
        form.add(new JLabel(" ", SwingConstants.CENTER));
        form.add(statusLabel);

        JPanel right = new JPanel(new BorderLayout());
        right.setOpaque(false);
        JLabel rTitle = new JLabel("Bearbeiten", SwingConstants.CENTER);
        rTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        right.add(rTitle, BorderLayout.NORTH);
        right.add(form, BorderLayout.CENTER);

        center.add(left);
        center.add(right);

        add(title);
        add(center);
    }

    private JPanel row(String label, JComponent comp) {
        JPanel p = new JPanel(new BorderLayout(10, 0));
        p.setOpaque(false);
        JLabel l = new JLabel(label);
        l.setPreferredSize(new Dimension(90, 28));
        p.add(l, BorderLayout.WEST);
        p.add(comp, BorderLayout.CENTER);
        return p;
    }

    public void setStatus(String text, boolean ok) {
        statusLabel.setText(text);
        if (ok) statusLabel.setForeground(new Color(0, 255, 0));
        else statusLabel.setForeground(new Color(255, 0, 0));
    }

    public void clearFields() {
        typBox.setSelectedIndex(0);
        frageField.setText("");
        antwortField.setText("");
        stufeBox.setSelectedIndex(0);
    }
}
