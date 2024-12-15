package src;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Classe représentant les blocks
 */
public class Block {
    private int id;
    private int x;
    private int y;
    private final int width;

    private boolean kicking, breaking, moving;
    private boolean up;

    private int coefMoving; // Coefficient de mouvement

    /**
     * Constructeur de la classe Block.
     * Initialise le block.
     */
    public Block(final int x, final int y, final int width, final int id) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.id = id;
        this.kicking = false;
        this.breaking = false;
        this.moving = false;
        this.coefMoving = 4;
        this.up = false;
    }
    public Block(final int x, final int y, final int width, final int id, final boolean kicking, final boolean breaking, final boolean moving){
        this(x, y, width, id);
        this.kicking = kicking;
        this.breaking = breaking;
        this.moving = moving;
    }

    /**
     * la méthode effect gère les effets des blocks spéciaux
     * Super-saut si kikcing vrai
     * Block temporaire si breaking vrai
     * Block en mouvement si moving vrai
     * @param a le personnage  Axel
     */
    public void effect(Axel a){
        if(kicking){
            a.setSpeedY(25);
            a.setFalling(true);
        }
        else if(breaking){
            Block b = this;
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                public void run() {
                    List<Block> newBlockSet = a.getField().getBlockSet();
                    newBlockSet.remove(b);
                    a.getField().setBlockSet(newBlockSet);
                    a.setFalling(true);
                }
            };
            timer.schedule(task, 1000);
        }
        else if(moving){
            if(x < GamePanel.BORDER_RIGHT) coefMoving = 1;
            else if (x > Hop.WIDTH - width - GamePanel.BORDER_LEFT) coefMoving = -1;
            x+=coefMoving;
            if(a.getSpeedY() == 0 && a.isFalling() == false && this.up){
                a.setX(a.getX() + coefMoving);
            }
        }
    }


    // GETTERS
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }
    public boolean isKicking() {
        return kicking;
    }

    public boolean isBreaking() {
        return breaking;
    }
    public boolean isMoving() {
        return moving;
    }

    // SETTERS
    public void setX(final int x){
         this.x = x;
    }

    public void setY(final int y){
        this.y = y;
    }
    public void setUp(final boolean up) {
        this.up = up;
    }
}
