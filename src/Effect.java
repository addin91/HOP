package src;

import java.awt.*;

/**
 * Classe abstraite représentant un effet graphique avec une durée donnée.
 * Cette classe sera étendue pour implémenter des effets spéciaux.
 */

public abstract class Effect {
    
    /**
     * Un entier représentant la durée de l'effet, en unité de temps.
     */
    protected int duration;

    /**
     * Un booléen indiquant si l'effet s'est terminé.
     * 'true' si l'effet est finie, sinon 'false'.
     */
    protected boolean finished;
    
    /**
     * Un booléen indiquant si l'effet a été déclenché.
     * 'true' si l'effet a été déclenché, sinon 'false'.
     */
    protected boolean triggered;

    /**
     * Constructeur de la classe Effect.
     * Initialise l'effet avec une durée donnée.
     * @param duration un entier indiquant la durée de l'effet, exprimé en unité de temps.
     */
    public Effect(final int duration) {
        this.duration = duration;
        this.finished = false;
        this.triggered = false;
    }

    /**
     * Méthode qui déclenche l'effet s'il n'a pas été encore déclenché.
     */
    public void trigger() {
        if (!triggered) {
            triggered = true;
        }
    }

    /**
     * Méthode abstraite qui met à jour l'effet.
     * Cette méthode sera implémentée par les sous-classes pour définir un comportement
     * spécifique de l'effet.
     */
    public abstract void update();

    /**
     * Méthode abstraite qui dessine l'effet à l'écran.
     * Cette méthode sera implémentée par les sous-classes pour définir la manière dont
     * l'effet sera dessinée à l'écran.
     * @param g un objet {@link Graphics} utilisé pour dessiner l'effet.
     */
    public abstract void draw(Graphics g);

    // GETTERS

    /**
     * Méthode renvoyant si l'effet s'est terminé.
     * @return 'true' si l'effet s'est terminé, sinon 'false'.
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * Méthode renvoyant si l'effet a été déclenché.
     * @return 'true' si l'effet a été déclenché, sinon 'false'
     */
    public boolean isTriggered(){
        return triggered;
    }

    // SETTERS
    
    /**
     * Méthode qui définit l'état déclenché de l'effet.
     * @param a 'true' pour définir que l'effet est déclenché, sinon 'false'.
     */
    public void setTriggered(boolean t){
        this.triggered = t;
    }
}