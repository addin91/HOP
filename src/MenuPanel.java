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
        ImageIcon icone = new ImageIcon("assets/images/HopLogo.png");
        if (icone.getImageLoadStatus() != MediaTracker.COMPLETE) {
            System.err.println("Le fichier logo.png est introuvable !");
        }
        Image newImage = icone.getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT);
        ImageIcon newImageIcone = new ImageIcon(newImage);
        JLabel logo = new JLabel(newImageIcone);
        add(logo, BorderLayout.NORTH);
        
        /* 
        // Titre
        JLabel titleLabel = new JLabel("Hop!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setForeground(Color.WHITE);
        add(titleLabel, BorderLayout.NORTH);*/
        
        // Bouton de démarrage
        JButton startButton = new JButton("Commencer le jeu");
        startButton.setFont(new Font("Arial", Font.PLAIN, 20));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Hop.playing = true;
            }
        });
        this.add(startButton, BorderLayout.CENTER);
    }
}