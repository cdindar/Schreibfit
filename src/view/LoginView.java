package view;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JPanel {
    public JTextField emailField = new JTextField();
    public JPasswordField passField = new JPasswordField();
    public JButton loginBtn = new JButton("Anmelden");
    public JLabel errorLabel = new JLabel(" ", SwingConstants.CENTER);

    public LoginView() {
        setLayout(new GridLayout(3, 1));
        setBackground(MainFrame.BEIGE);

        JLabel title = new JLabel("SchreibFit Pro", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 44));
        title.setForeground(Color.DARK_GRAY);
        title.setBorder(BorderFactory.createEmptyBorder(60, 20, 10, 20));

        JPanel center = new JPanel(new GridLayout(6, 1, 10, 10));
        center.setOpaque(false);
        center.setBorder(BorderFactory.createEmptyBorder(30, 220, 30, 220));

        JPanel row1 = new JPanel(new BorderLayout(10, 0));
        row1.setOpaque(false);
        JLabel emailLabel = new JLabel("TGM Email:");
        emailLabel.setPreferredSize(new Dimension(90, 30));
        row1.add(emailLabel, BorderLayout.WEST);
        emailField.setPreferredSize(new Dimension(460, 30));
        row1.add(emailField, BorderLayout.CENTER);

        JPanel row2 = new JPanel(new BorderLayout(10, 0));
        row2.setOpaque(false);
        JLabel passLabel = new JLabel("Passwort:");
        passLabel.setPreferredSize(new Dimension(90, 30));
        row2.add(passLabel, BorderLayout.WEST);
        passField.setPreferredSize(new Dimension(460, 30));
        row2.add(passField, BorderLayout.CENTER);

        errorLabel.setForeground(new Color(255, 0, 0));
        errorLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        btnRow.setOpaque(false);
        loginBtn.setBackground(Color.WHITE);
        loginBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        loginBtn.setPreferredSize(new Dimension(220, 40));
        btnRow.add(loginBtn);

        JLabel hint = new JLabel("Beispiel: mmustermann@student.tgm.ac.at", SwingConstants.CENTER);
        hint.setForeground(Color.GRAY);
        hint.setFont(new Font("SansSerif", Font.ITALIC, 12));

        center.add(row1);
        center.add(row2);
        center.add(errorLabel);
        center.add(btnRow);
        center.add(hint);
        center.add(new JLabel(" ", SwingConstants.CENTER));

        add(title);
        add(center);
        add(new JLabel(" "));
    }

    public void showError(String msg) {
        errorLabel.setText(msg);
        Timer t = new Timer(2500, new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                errorLabel.setText(" ");
            }
        });
        t.setRepeats(false);
        t.start();
    }

    public void clearFields() {
        emailField.setText("");
        passField.setText("");
        errorLabel.setText(" ");
    }
}
