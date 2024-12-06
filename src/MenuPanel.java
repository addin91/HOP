package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel {
    public MenuPanel() {
        setBackground(new Color(0, 96, 173));
        setLayout(new BorderLayout());

        // Logo Hop
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(new Color(0, 96, 173)); // Même couleur de fond
        logoPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Centrer le logo horizontalement
        ImageIcon icone = new ImageIcon(getClass().getResource("/assets/images/HopLogo.png"));
        Image newImage = icone.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        JLabel logo = new JLabel(new ImageIcon(newImage));
        logoPanel.add(logo);
        add(logoPanel, BorderLayout.NORTH); 

        // Bouton de démarrage
        JButton startButton = new JButton("Commencer le jeu");
        startButton.setFont(new Font("Arial", Font.PLAIN, 20));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Hop.playing = true;
            }
        });

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(new Color(0, 96, 173));
        buttonPanel.add(startButton);
        add(buttonPanel);
    }
}