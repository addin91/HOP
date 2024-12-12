package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel {
    private String name;
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

        JTextField changeName = new JTextField("Player");
        changeName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
            }
        });
        contentPanel.add(changeName);

        // Bouton de démarrage
        JButton startButton = new JButton("Commencer le jeu");
        startButton.setFont(new Font("Arial", Font.PLAIN, 20));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name = changeName.getText();
                Hop.playing = true;
            }
        });

        // Ajouter le bouton au panneau
        contentPanel.add(startButton);





        // Ajouter les panneaux à la hiérarchie
        backgroundPanel.add(contentPanel, BorderLayout.CENTER); // Composants superposés
        add(backgroundPanel, BorderLayout.CENTER);
    }

    public String getPlayerName() {
        return this.name;
    }
    public void setPlayerName(String namePlayer){
        this.name = namePlayer;
    }
}
