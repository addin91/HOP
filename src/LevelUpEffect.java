package src;

import java.awt.*;
import java.util.ArrayList;

/**
 * Classe qui étend la classe mère {@link Effet}.
 * Cette classe représente un effet graphique, avec des particules scintillantes
 * générées autour des coordonnées données
 */

public class LevelUpEffect extends Effect {

    /**
     * Liste représentant les particules de l'effet où chaque tableau d'entiers représente
     * des informations sur chaque particule.
     */
    private ArrayList<int[]> particles;

    /**
     * Un entier représentant la coordonnée X autour de laquelle les particules
     * sont générées.
     */
    private int axelX;

    /**
     * Un entier représentant la coordonnée Y autour de laquelle les particules
     * sont générées.
     */
    private int axelY;

    /**
     * Constructeur de la classe LevelUpEffect.
     * Initialise l'effet avec des coordonnées données.
     * @param axelX un entier indiquant la position X de l'effet.
     * @param axelY un entier indiquant la position Y de l'effet.
     */
    public LevelUpEffect(final int axelX, final int axelY) {
        super(30);  // Durée de l'effet en frames
        this.axelX = axelX;
        this.axelY = axelY;
        this.particles = new ArrayList<>();
    }

    /**
     * Méthode déclenchant l'effet "Level Up" en générant 50 particules.
     * Chaque particule a :
     * une position x calculée autour de axelX.
     * une position y calculée autour de axelY.
     * une taille aléatoire.
     * une opacité maximale.
     * une vitesse d'apparition aléatoire sur X.
     * une vitesse d'apparition aléatoire² sur Y.
     */
    @Override
    public void trigger() {
        if (!this.isTriggered()) {
            super.trigger();
            for (int i = 0; i < 50; i++) {
                int x = this.axelX + (int)(Math.random() * 150);
                int y = this.axelY + (int)(Math.random() * 200);
                int taille = 5 + (int)(Math.random() * 5);
                int opacite = 255;
                int vitesseX = (int)(Math.random() * 10) - 5;
                int vitesseY = (int)(Math.random() * 10) - 5;
                this.particles.add(new int[] {x, y, taille, opacite, vitesseX, vitesseY});
            }
        }
    }

    /**
     * Méthode mettant à jour les particules.
     * Les particules se déplacent selon une vitesse et diminuent en opacité.
     * Les particules qui ont une opacité inférieur à 0 sont supprimées.
     * Si toutes les paritcules sont mortes, l'effet est marqué comme terminé.
     */
    @Override
    public void update() {
        for (int i = particles.size() - 1; i >= 0; i--) {
            int[] p = particles.get(i);
            p[0] += p[4];
            p[1] += p[5];
            p[3] -= 5;
            if (p[3] <= 0) {
                this.particles.remove(i);
            }
        }
        if (this.particles.isEmpty()) {
            this.finished = true;
        }
    }

    /**
     * Méthode dessinant l'effet sur l'écran.
     * Chaque particule est représentée par un cercle jaune.
     * @param g un objet {@link Graphics} utilisé pour dessiner les particules.
     */
    @Override
    public void draw(Graphics g) {
        // Dessiner les particules
        for (int[] p : this.particles) {
            g.setColor(new Color(255, 255, 0, p[3]));  // Jaune avec opacité
            g.fillOval(p[0], p[1], p[2], p[2]);  // Dessiner un cercle (particule)
        }
    }
}