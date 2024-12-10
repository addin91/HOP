package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndGamePanel extends JPanel {
    //private Clip endGameMusicClip;
    //private int finalScore;
    
    public EndGamePanel(){
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
            }
        };
        backgroundPanel.setLayout(new BorderLayout());
        
        // Panneau de contenu où les boutons seront ajoutés
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 60, 50));
        contentPanel.setOpaque(false);  // Rendre transparent pour voir le fond

        // Bouton "Rejouer"
        JButton replayButton = new JButton("Rejouer");
        replayButton.setFont(new Font("Arial", Font.PLAIN, 18));
        // à compléter 

        // Bouton "Quitter"
        JButton quitButton = new JButton("Quitter");
        quitButton.setFont(new Font("Arial", Font.PLAIN, 18));
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Quitte l'application
            }
        });

        contentPanel.add(quitButton);
        contentPanel.add(replayButton);

        // Ajouter les panneaux à la hiérarchie
        backgroundPanel.add(contentPanel, BorderLayout.CENTER);  // Composants superposés
        add(backgroundPanel, BorderLayout.CENTER);  // Panneau de fond en bas
    }
}