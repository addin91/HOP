package src;

import java.util.Timer;
import java.util.TimerTask;

public class Block {
    private int id;
    private int x;
    private int y;
    private final int width;

    private boolean kicking, breaking, moving;
    private boolean habiter;

    private int coefMoving;

    public Block(int x, int y, int width, int id) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.id = id;
        this.kicking = false;
        this.breaking = false;
        this.moving = false;
        this.coefMoving = 4;
        this.habiter = false;
    }
    public Block(int x, int y, int width, int id, boolean kicking, boolean breaking, boolean moving){
        this(x, y, width, id);
        this.kicking = kicking;
        this.breaking = breaking;
        this.moving = moving;
    }

    public void movingXBlock(){
        System.out.println(x);
        
    }

    public void effet(Axel a){
        if(kicking){

        System.out.println("Effet");
        a.setVitesseY(30);
        a.setFalling(true);
         
        }
        else if(breaking){

            Block b = this;
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                public void run() {
                    a.getField().ensembleBlocks.remove(b);
                    a.setFalling(true);
                }
            };

            timer.schedule(task, 1000);
            System.out.println("Casse");
    
        }
        else if(moving){
            if(x < GamePanel.BORDER_RIGHT) coefMoving = 1;
            else if (x > Hop.WIDTH - width - GamePanel.BORDER_LEFT) coefMoving = -1;
            x+=coefMoving;
            if(a.getVitesseY() == 0 && a.isFalling() == false && this.habiter){
                a.setX(a.getX() + coefMoving);
            }
        }
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getId() {
        return id;
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
    public void setX(int x){
         this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }
    public void setHabiter(boolean habiter) {
        this.habiter = habiter;
    }
    public int getWidth() {
        return width;
    }
}
