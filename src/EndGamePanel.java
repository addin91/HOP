package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe représentant le panneau de fin de jeu
 * Cette classe permet d'afficher le score, son rang dans le classement,
 * le meilleur score ainsi que deux boutons pour quitter ou rejouer.
 */
public class EndGamePanel extends JPanel {

    /**
     * Constructeur de la classe EndGamePanel.
     * Initialise l'interface de fin de jeu, affiche le score, le rang, le meilleur score,
     * et propose de quitter ou de rejouer une nouvelle partie.
     * @param score un entier représenant le score obtenu.
     * @param bestScore un entier représenant le meilleur score.
     * @param rank un entier représenant le rang du joueur dans le classement.
     */
    public EndGamePanel(final int score, final int bestScore, final int rank){
        setLayout(new BorderLayout());

        // Interface personnalisé
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
            Color scoreColor = new Color(0, 0, 0); 
            Color scorePosition = new Color(0,255,120);
            Color bestScoreColor = new Color(255, 220, 0);
            Color shadow = new Color(0, 0, 0, 130);

            // Positions 
            int xText = 75; 
            int yScore = 350; 
            int yPosition = yScore + 30; 
            int yBestScore = yPosition + 30;

            // Ombres
            g.setColor(shadow);
            g.drawString("Votre Score: " + score , xText + 1, yScore + 1);
            g.drawString("Votre position: " + rank + (rank == 1 ? " er" : " ème"), xText+1, yPosition + 1);
            g.drawString("Meilleur score: " + Math.max(score, bestScore), xText + 1, yBestScore + 1);

            // Texte principal : Votre Score
            g.setColor(scoreColor);
            g.drawString("Votre Score: " + score , xText, yScore);

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
        contentPanel.setOpaque(false); 

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

        // Ajouter les boutons au panneau de contenu
        contentPanel.add(quitButton);
        contentPanel.add(replayButton);

        // Ajouter les panneaux à la hiérarchie
        backgroundPanel.add(contentPanel, BorderLayout.SOUTH);  // Composants superposés
        add(backgroundPanel, BorderLayout.CENTER);  // Panneau de fond en bas
    }

}