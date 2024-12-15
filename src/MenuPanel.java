package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe représentant le panneau du menu principal du jeu.
 * Cette classe permet au joueur de saisir son pseudo et de commencer à jouer.
 */
public class MenuPanel extends JPanel {

    /**
     * Une chaîne de caractères représentant le pseudo du joueur saisi dans
     * le champ de texte du menu.
     */
    private String namePlayer;

    /**
     * Constructeur de la classe MenuPanel
     * Initialise l'interface du menu principal, avec un arrière-plan personnalisé,
     * un champ pour saisir le pseudo du joueur, et un bouton pour commencer à jouer.
     */
    public MenuPanel() {
        setLayout(new BorderLayout());

        // Interface personnalisée
        JPanel backgroundPanel = new JPanel() {
            private Image backgroundImage = new ImageIcon(getClass().getResource("/assets/images/InterfaceHop.png")).getImage();
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        backgroundPanel.setLayout(new BorderLayout());
        
        // Panneau pour les éléments du menu
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setOpaque(false);

        // Champ de texte pour le pseudo du joueur
        JTextField changeName = new JTextField("Player", 15);
        changeName.setFont(new Font ("Verdana", Font.BOLD, 13));
        changeName.setHorizontalAlignment(JTextField.CENTER);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Ajouter le champ au panneau de contenu
        contentPanel.add(changeName,gbc);

        // Bouton pour commencer le jeu
        JButton startButton = new JButton("Commencer le jeu");
        startButton.setBackground(new Color(250,220,0));
        startButton.setFont(new Font("Verdana", Font.BOLD, 20));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                namePlayer = changeName.getText();
                Hop.playing = true;
            }
        });
        gbc.gridy = 1;
        // Ajouter le bouton au panneau de contenu
        contentPanel.add(startButton, gbc);

        // Ajouter les panneaux à la hiérarchie
        backgroundPanel.add(contentPanel, BorderLayout.CENTER);
        add(backgroundPanel, BorderLayout.CENTER);
    }

    // GETTERS

    /**
     * Méthode renvoyant le pseudo du joueur
     * @return une chaîne de caractère représentant le pseudo du joueur
     */
    public String getPlayerName() {
        return this.namePlayer;
    }
}