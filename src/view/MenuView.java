package view;

import javax.swing.*;
import java.awt.*;

public class MenuView extends JPanel {
    public JLabel titelLabel = new JLabel("", SwingConstants.CENTER);
    public JLabel infoLabel = new JLabel("", SwingConstants.CENTER);
    public JPanel buttonPanel = new JPanel(new GridLayout(1, 1, 10, 10));

    public MenuView() {
        setLayout(new GridLayout(3, 1));
        setBackground(MainFrame.BEIGE);

        titelLabel.setFont(new Font("Serif", Font.BOLD, 44));
        titelLabel.setForeground(Color.DARK_GRAY);
        titelLabel.setBorder(BorderFactory.createEmptyBorder(40, 20, 10, 20));

        infoLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        infoLabel.setForeground(Color.DARK_GRAY);
        infoLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 20));

        buttonPanel.setOpaque(false);

        JPanel centerWrap = new JPanel(new BorderLayout());
        centerWrap.setOpaque(false);
        centerWrap.setBorder(BorderFactory.createEmptyBorder(40, 320, 60, 320));
        centerWrap.add(buttonPanel, BorderLayout.CENTER);

        add(titelLabel);
        add(infoLabel);
        add(centerWrap);
    }

    public void setTitel(String text) {
        titelLabel.setText(text);
    }

    public void setInfo(String text) {
        infoLabel.setText(text);
    }

    public void setButtons(JButton[] buttons) {
        buttonPanel.removeAll();
        buttonPanel.setLayout(new GridLayout(buttons.length, 1, 10, 10));
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setFont(new Font("SansSerif", Font.BOLD, 16));
            buttons[i].setBackground(Color.WHITE);
            buttonPanel.add(buttons[i]);
        }
    }
}
