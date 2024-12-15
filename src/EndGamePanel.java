package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndGamePanel extends JPanel {
    public EndGamePanel(final int score, final int bestScore, int rank){
        
 
        setLayout(new BorderLayout());
        // Interface
        JPanel backgroundPanel = new JPanel() {
            private Image backgroundImage = new ImageIcon(getClass().getResource("/assets/images/GameOver.png")).getImage();
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
                // Définir la police
            Font font = new Font("Verdana", Font.BOLD, 20);
            g.setFont(font);

            // Couleurs
            Color scoreColor = new Color(0, 0, 0); // Couleur pour "Votre Score" et "Votre position"
            Color scorePosition = new Color(0,255,120);
            Color bestScoreColor = new Color(255, 220, 0); // Couleur pour "Meilleur score"
            Color shadow = new Color(0, 0, 0, 130); // Ombre semi-transparente

            // Positions 
            int xText = 75; 
            int yScore = 350; 
            int yPosition = yScore + 30; 
            int yBestScore = yPosition + 30;

            // Ombres
            g.setColor(shadow);
            g.drawString("Votre Score: " + score , xText + 33, yScore + 1);
            g.drawString("Votre position: " + rank + (rank == 1 ? " er" : " ème"), xText, yPosition + 1);
            g.drawString("Meilleur score: " + Math.max(score, bestScore), xText + 1, yBestScore + 1);

            // Texte principal : Votre Score
            g.setColor(scoreColor);
            g.drawString("Votre Score: " + score , xText+32, yScore);

            // Texte principal : Votre position
            g.setColor(scorePosition);
            g.drawString("Votre position: " + rank + (rank == 1 ? " er" : " ème"), xText, yPosition);

            // Texte principal : Meilleur score
            g.setColor(bestScoreColor);
            g.drawString("Meilleur score: " + Math.max(score, bestScore), xText, yBestScore);
            }
        };
        backgroundPanel.setLayout(new BorderLayout());
        
        // Panneau de contenu où les boutons seront ajoutés
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50));
        contentPanel.setOpaque(false);  // Rendre transparent pour voir le fond

        // Bouton "Rejouer"
        JButton replayButton = new JButton("Rejouer");
        replayButton.setFont(new Font("Arial", Font.PLAIN, 18));
        replayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                Hop.restart = true;
            }
        }); 

        // Bouton "Quitter"
        JButton quitButton = new JButton("Quitter");
        quitButton.setFont(new Font("Arial", Font.PLAIN, 18));
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Hop.playing = false; // Quitte l'application
            }
        });

        contentPanel.add(quitButton);
        contentPanel.add(replayButton);

        // Ajouter les panneaux à la hiérarchie
        backgroundPanel.add(contentPanel, BorderLayout.SOUTH);  // Composants superposés
        add(backgroundPanel, BorderLayout.CENTER);  // Panneau de fond en bas
    }

}