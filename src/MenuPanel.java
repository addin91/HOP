package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel {
    public MenuPanel() {
        setBackground(new Color(100, 150, 200));
        setLayout(new BorderLayout());

        // Titre
        JLabel titleLabel = new JLabel("Hop!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setForeground(Color.WHITE);
        add(titleLabel, BorderLayout.NORTH);

        // Bouton de démarrage
        JButton startButton = new JButton("Commencer le jeu");
        startButton.setFont(new Font("Arial", Font.PLAIN, 20));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Hop.playing = true;
                System.out.println("hop true");
            }
        });
        this.add(startButton, BorderLayout.CENTER);
    }

}
