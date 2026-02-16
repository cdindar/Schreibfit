package view;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JPanel {
    public JTextField emailField = new JTextField();
    public JPasswordField passField = new JPasswordField();
    public JButton loginBtn = new JButton("Anmelden");
    public JLabel errorLabel = new JLabel(" ", SwingConstants.CENTER);

    public LoginView() {
        setLayout(new BorderLayout());
        setBackground(MainFrame.BEIGE);

        JLabel title = new JLabel("SchreibFit Pro", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 48));
        title.setForeground(Color.DARK_GRAY);
        title.setBorder(BorderFactory.createEmptyBorder(50, 20, 20, 20));
        add(title, BorderLayout.NORTH);

        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Abstände zwischen den Elementen
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JLabel emailLabel = new JLabel("TGM Email:");
        emailLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridy = 0;
        centerWrapper.add(emailLabel, gbc);

        emailField.setPreferredSize(new Dimension(350, 40));
        emailField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridy = 1;
        centerWrapper.add(emailField, gbc);

        JLabel passLabel = new JLabel("Passwort:");
        passLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridy = 2;
        centerWrapper.add(passLabel, gbc);

        passField.setPreferredSize(new Dimension(350, 40));
        gbc.gridy = 3;
        centerWrapper.add(passField, gbc);

        errorLabel.setForeground(new Color(255, 0, 0));
        errorLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridy = 4;
        centerWrapper.add(errorLabel, gbc);

        loginBtn.setPreferredSize(new Dimension(350, 55)); // Schön groß und hoch
        loginBtn.setFont(new Font("SansSerif", Font.BOLD, 18));
        loginBtn.setBackground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        gbc.gridy = 5;
        gbc.insets = new Insets(20, 10, 10, 10); // Mehr Abstand nach oben zum Button
        centerWrapper.add(loginBtn, gbc);

        JLabel hint = new JLabel("Beispiel: mmustermann@student.tgm.ac.at", SwingConstants.CENTER);
        hint.setForeground(Color.GRAY);
        hint.setFont(new Font("SansSerif", Font.ITALIC, 12));
        gbc.gridy = 6;
        gbc.insets = new Insets(5, 10, 10, 10);
        centerWrapper.add(hint, gbc);

        add(centerWrapper, BorderLayout.CENTER);

        add(Box.createVerticalStrut(100), BorderLayout.SOUTH);
    }

    public void showError(String msg) {
        errorLabel.setText(msg);
        Timer t = new Timer(2500, e -> errorLabel.setText(" "));
        t.setRepeats(false);
        t.start();
    }

    public void clearFields() {
        emailField.setText("");
        passField.setText("");
        errorLabel.setText(" ");
    }
}