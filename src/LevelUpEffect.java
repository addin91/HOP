package src;

import java.awt.*;
import java.util.ArrayList;

public class LevelUpEffect extends Effect {
    private ArrayList<int[]> particles;
    private int axelX, axelY;

    public LevelUpEffect(int axelX, int axelY) {
        super(30);  // Durée de l'effet en frames
        this.axelX = axelX;
        this.axelY = axelY;
        particles = new ArrayList<>();
    }

    @Override
    public void trigger() {
        if (!isTriggered()) {
            //setTriggered(true);
            super.trigger();  // Marque l'effet comme déclenché
            // Créer des particules scintillantes autour d'Axel
            for (int i = 0; i < 50; i++) {  // Générer 50 particules
                int x = axelX + (int)(Math.random() * 150);  // Position autour d'Axel (X)
                int y = axelY + (int)(Math.random() * 200);  // Position autour d'Axel (Y)
                int taille = 5 + (int)(Math.random() * 5);  // Taille aléatoire
                int alpha = 255;  // Opacité maximale
                int vitesseX = (int)(Math.random() * 10) - 5;  // Vitesse aléatoire sur X
                int vitesseY = (int)(Math.random() * 10) - 5;  // Vitesse aléatoire sur Y

                // Ajouter la particule dans la liste
                particles.add(new int[] {x, y, taille, alpha, vitesseX, vitesseY});
            }
        }
    }

    @Override
    public void update() {
        // Met à jour la position des particules et leur opacité
        for (int i = particles.size() - 1; i >= 0; i--) {
            int[] p = particles.get(i);

            // Mettre à jour la position de la particule
            p[0] += p[4];  // Mise à jour de X avec vitesseX
            p[1] += p[5];  // Mise à jour de Y avec vitesseY
            p[3] -= 5;     // Diminue l'opacité (alpha) pour simuler la disparition

            // Supprimer les particules mortes (opacité <= 0)
            if (p[3] <= 0) {
                particles.remove(i);
            }
        }

        // Si les particules sont toutes mortes, l'effet est terminé
        if (particles.isEmpty()) {
            finished = true;
        }
    }

    @Override
    public void draw(Graphics g) {
        // Dessiner les particules
        for (int[] p : particles) {
            g.setColor(new Color(255, 255, 0, p[3]));  // Jaune avec opacité
            g.fillOval(p[0], p[1], p[2], p[2]);  // Dessiner un cercle (particule)
        }
    }
}
