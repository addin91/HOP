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

                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial", Font.BOLD, 15));
                g.drawString("Votre Score : " + score, 120, 350);
                g.drawString("Meilleur score: " + ((score > bestScore) ? score : bestScore), 120, 380);
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