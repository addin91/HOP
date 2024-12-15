package src;

import java.awt.*;

public abstract class Effect {
    protected int duration;  // Durée de l'effet
    protected boolean finished;  // Si l'effet est terminé
    protected boolean triggered;

    public Effect(final int duration) {
        this.duration = duration;
        this.finished = false;
        this.triggered = false;
    }

    public void trigger() {
        if (!triggered) {
            triggered = true;
        }
    }

    public abstract void update();  // Mise à jour de l'effet
    public abstract void draw(Graphics g);  // Dessin de l'effet


    // GETTERS

    public boolean isFinished() {
        return finished;
    }

    public boolean isTriggered(){
        return triggered;
    }

    // SETTERS
    
    public void setTriggered(boolean a){
        this.triggered = a;
    }
}
