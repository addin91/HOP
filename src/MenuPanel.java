package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel {
    private String namePlayer;
    public MenuPanel() {
        setLayout(new BorderLayout());

        // Interface
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
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setOpaque(false);

        JTextField changeName = new JTextField("Player", 15);
        changeName.setFont(new Font ("Verdana", Font.BOLD, 13));
        changeName.setHorizontalAlignment(JTextField.CENTER);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // Positionnement dans la colonne 0
        gbc.gridy = 0; // Positionnement dans la ligne 0
        gbc.insets = new Insets(10, 10, 10, 10); // Marges autour de l'élément
        
        contentPanel.add(changeName,gbc);

        // Bouton de démarrage
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

        // Ajouter le bouton au panneau
        gbc.gridy = 1;
        contentPanel.add(startButton, gbc);


        // Ajouter les panneaux à la hiérarchie
        backgroundPanel.add(contentPanel, BorderLayout.CENTER); // Composants superposés
        add(backgroundPanel, BorderLayout.CENTER);
    }


    // GETTERS

    public String getPlayerName() {
        return this.namePlayer;
    }

}
