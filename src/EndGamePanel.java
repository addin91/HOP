package src;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EndGamePanel extends JPanel {
    private MusicGame music;
    public EndGamePanel(int score, int bestScore){
        
        //System.out.println("musique");
        //this.music.playRandom();
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
                // Texte
                Font font = new Font("Verdana", Font.BOLD,20);
                g.setFont(font);
                // Couleurs
                Color scoreColor = new Color(0, 0, 0);
                Color bestScoreColor = new Color(255,220,0);
                Color shadow = new Color(0,0,0,130);
                // Position 
                int xScore = 105;
                int yScore = 350;
                int xBestScore = 75;
                int yBestScore = 380;
                // Ombres 
                g.setColor(shadow);
                g.drawString("Votre Score: " + score, xScore + 1, yScore + 1);
                g.drawString("Meilleur score: " + ((score > bestScore) ? score : bestScore), xBestScore + 2, yBestScore + 2);
                // Votre Score 
                g.setColor(scoreColor);
                g.drawString("Votre Score: " + score, 105, 350);
                // Meilleur score
                g.setColor(bestScoreColor);
                g.drawString("Meilleur score: " + ((score > bestScore) ? score : bestScore), 75, 380);
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

    public void setMusic(MusicGame m){
        this.music = m;
    }

    public MusicGame getMusic(){
        return music;
    }
}