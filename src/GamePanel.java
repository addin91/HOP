package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel implements KeyListener {
    private static final int BLOCK_HEIGHT = 10;
    private static final int AXEL_WIDTH = 10;
    private static final int AXEL_HEIGHT = 10;
    public static final int BORDER_RIGHT = 33;
    public static final int BORDER_LEFT = 47;

    private final Axel axel;
    private final Field field;
    private int level;

    private final Image Game;
    private MusicGame soundEffect;

    private final int lavaHeight;
    private int lavaOffset;
    private final Timer lavaAnimation;

    private Image marioFace;
    private Image marioRight;
    private Image marioLeft;
    private Image marioJump;
    private Image currentMario;

    private Image blockImage;

    
    private Effect levelUpEffect;

    private ArrayList<String> soundFiles;

    
    public GamePanel(final Field field, final Axel axel) {
        this.soundFiles = new ArrayList<>();
        soundFiles.add("assets/audio/SoundEffect/Yahoo.wav");
        this.soundEffect = new MusicGame(soundFiles);
        this.marioFace = new ImageIcon(getClass().getResource("/assets/images/mario/axel_bas.gif")).getImage();
        this.marioRight= new ImageIcon(getClass().getResource("/assets/images/mario/axel_droite.gif")).getImage();
        this.marioLeft = new ImageIcon(getClass().getResource("/assets/images/mario/axel_gauche.gif")).getImage();
        this.marioJump = new ImageIcon(getClass().getResource("/assets/images/mario/axel_haut.gif")).getImage();
        this.currentMario = marioFace;
        
        // Charger l'image comme BufferedImage
        this.blockImage = new ImageIcon(getClass().getResource("/assets/images/block.png")).getImage();

        this.field = field;
        this.axel = axel;
        this.Game = new ImageIcon(getClass().getResource("/assets/images/InterfaceGame.png")).getImage();
        this.lavaHeight = 20;
        this.lavaOffset = 0;
        this.level = 0;

        setPreferredSize(new Dimension(Hop.WIDTH, Hop.HEIGHT));
        addKeyListener(this);
        setFocusable(true);

        lavaAnimation = new Timer(50, e -> {
            lavaOffset += 3; // Faire défiler les vagues
            if (lavaOffset >= 50) {
                lavaOffset = 0; // Réinitialiser après un cycle
            }
            repaint();
        });
        lavaAnimation.start();
    }

    public void triggerLevelUpEffect() {
        if (levelUpEffect == null || !levelUpEffect.isTriggered()) {
            levelUpEffect = new LevelUpEffect(120, 180);  // Créer un nouvel effet
        }
        levelUpEffect.trigger();
    }

    public void paintComponent(Graphics graphics) {
        // Retourne l'ecran
        Graphics2D g = (Graphics2D) graphics;
        int x = this.getWidth() / 2;
        int y = this.getHeight() / 2;
        super.paintComponent(g);
        if (Game != null) {
            g.drawImage(Game, 0, 0, getWidth(), getHeight(), this);
        }
        g.rotate(Math.toRadians(180.0), x, y);
        if (axel.isFalling()) {
            if (axel.isLeft()) currentMario = marioLeft;
            else if (axel.isRight()) currentMario = marioRight;
            else currentMario = marioJump; // Mario saute
        } else if (axel.isRight()) {
            currentMario = marioRight; // Mario va à droite
        } else if (axel.isLeft()) {
            currentMario = marioLeft; // Mario va à gauche
        } else {
            currentMario = marioFace; // Mario est face
        }
        //g.rotate(Math.toRadians(180.0), x, y);
        g.setColor ( new Color (0 , 0 , 0 , 255));
        for(Block b: this.field.getBlockSet()){
            if(b.isKicking()){
                g.setColor ( new Color (255 , 0 , 0 , 255));
                g.fillRect(b.getX(), b.getY()+15, b.getWidth(), BLOCK_HEIGHT);
            }
            else if(b.isBreaking()){
                g.setColor ( new Color (0 , 0 , 255 , 255));
                g.fillRect(b.getX(), b.getY(), b.getWidth(), BLOCK_HEIGHT);
            }
            else if(b.isMoving()){   
                g.setColor ( new Color (0 , 255 , 0 , 255));
                g.fillRect(b.getX(), b.getY(), b.getWidth(), BLOCK_HEIGHT);
            }
            else {
                g.drawImage(blockImage, b.getX(), b.getY(), b.getX() + b.getWidth(), b.getY() + BLOCK_HEIGHT,
                    20, 150, blockImage.getWidth(null), BLOCK_HEIGHT, this);
            }
            
            
        }

        g.drawImage(currentMario, this.axel.getX()-(2*AXEL_WIDTH)+2, this.axel.getY()+AXEL_HEIGHT-3, null);

        
        // Score
        g.rotate(Math.toRadians(180.0), x, y);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Verdana", Font.ITALIC, 15));
        g.drawString("Score : " + this.field.getScore(), BORDER_LEFT-10, 20);
        g.drawString("Difficulté: " + level, Hop.WIDTH-BORDER_RIGHT-105, 20);

        if (!Hop.startGame) {
            g.setColor(new Color(0,0,0,180));
            g.setFont(new Font("Arial", Font.BOLD, 15));
            g.drawString("Appuyez sur une touche pour commencer !", 34, Hop.HEIGHT / 2);
        }

        drawLava(g);
        if (levelUpEffect != null && !levelUpEffect.isFinished()) {
            levelUpEffect.update();
            levelUpEffect.draw(g);
        }
}
    private void drawLava(Graphics2D g) {
        // Couleur principale de la lave
        g.setColor(new Color(255, 40, 0));
        g.fillRect(0, getHeight() - lavaHeight, getWidth(), lavaHeight);

        // Ajouter des vagues mouvantes
        g.setColor(new Color(255, 100, 0));
        int waveWidth = 50;
        for (int x = -waveWidth + lavaOffset; x < getWidth(); x += waveWidth) {
            g.fillArc(x, getHeight() - lavaHeight - 10, waveWidth, 20, 0, 180);
        }
    }

    public void updateScoreAndLevel() {
        int oldNiveau = level;

            for(Block b  : field.getBlockSet()){
                if(b.isMoving()){
                    b.effect(axel);
                }
            }

            if(this.field.getScore() >= 7200 && level < 6){
                level = 6;
                Hop.speed = 6;
                this.field.increaseWidthBlock();
                
            }
            else if(this.field.getScore() >= 4800 && level < 5){
                level = 5;
                Hop.speed = 5;
                this.field.increaseWidthBlock();
                
            }
            else if(this.field.getScore() >= 3200 && level < 4){
                level = 4;
                Hop.speed = 4;
                this.field.increaseWidthBlock();
                
            }
            else if(this.field.getScore() >= 2000 && level < 3){
                level = 3;
                Hop.speed = 3;
                this.field.increaseWidthBlock();
                
            }
            else if(this.field.getScore() >= 800 && level < 2){
                level = 2;
                Hop.speed = 2;
                this.field.increaseWidthBlock();
                
            }
            else if(this.field.getScore() >= 80 && level < 1){
                level = 1;
                Hop.speed = 1;
                this.field.increaseWidthBlock();
                
 
            }
        if (level != oldNiveau){
            triggerLevelUpEffect();
            soundEffect.playSound();
            levelUpEffect.setTriggered(false);
        }


    }

               

    public void keyPressed(KeyEvent e){
        switch (e.getKeyCode()) {
            // Plonge
            case KeyEvent.VK_DOWN:
                this.axel.setDiving(true);
                break;
            // Saute
            case KeyEvent.VK_UP:
                this.axel.setJumping(true);
                Hop.startGame = true;
                break;
            // Gauche
            case KeyEvent.VK_LEFT:
                this.axel.setLeft(true);
                break;
            // Droite
            case KeyEvent.VK_RIGHT:
                this.axel.setRight(true);
                break;
            default: break;
        }
    }

    public void keyReleased(KeyEvent e){
        switch (e.getKeyCode()) {
            // Plonge
            case KeyEvent.VK_DOWN:
                this.axel.setDiving(false);
                break;
            // Saute
            case KeyEvent.VK_UP:
                this.axel.setJumping(false);
                break;
            // Gauche
            case KeyEvent.VK_LEFT:
                this.axel.setLeft(false);
                break;
            // Droite
            case KeyEvent.VK_RIGHT:
                this.axel.setRight(false);
                break;
            default: break;
        }
    }
   
    public void keyTyped(KeyEvent e){}

}
