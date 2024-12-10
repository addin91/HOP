package src;

import javax.swing.*;
import java.awt.*;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel implements KeyListener {
    private static final int BLOCK_HEIGHT = 10;
    private static final int AXEL_WIDTH = 10;
    private static final int AXEL_HEIGHT = 10;

    private final Axel axel;
    private final Field field;
    private int score = 0;
    private int niveau = 0;

    
    public GamePanel(Field field, Axel axel) {
        this.field = field;
        this.axel = axel;
        setBackground ( new Color (161 , 202 , 241));
        setPreferredSize(new Dimension(field.width, field.height));

    }

    public void paintComponent(Graphics graphics) {
        // Retourne l'ecran
        Graphics2D g = (Graphics2D) graphics;
        int x = this.getWidth() / 2;
        int y = this.getHeight() / 2;
        g.rotate(Math.toRadians(180.0), x, y);
        super.paintComponent(g);



        
        for(Block b: this.field.ensembleBlocks){
            if(b.isKicking())       g.setColor ( new Color (255 , 0 , 0 , 255));
            else if(b.isBreaking()) g.setColor ( new Color (0 , 0 , 255 , 255));
            else if(b.isMoving())   g.setColor ( new Color (0 , 255 , 0 , 255));
            else                    g.setColor ( new Color (0 , 0 , 0 , 255));
            g.fillRect(b.getX(), b.getY(), b.getWidth(), BLOCK_HEIGHT);
        }
        // Axel
        //g.drawImage(this.axel.getImage(), this.axel.getX(), this.axel.getY(), 40, 40, null);
        g.setColor(Color.RED);
        g.fillOval(this.axel.getX()-AXEL_WIDTH/2, this.axel.getY()+AXEL_HEIGHT, AXEL_WIDTH, AXEL_HEIGHT);
        // Score


        
        g.rotate(Math.toRadians(180.0), x, y);
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.drawString("Score : " + this.field.getScore(), 10, 20);
        g.drawString("Difficulté: " + niveau, 300, 20);

        if (!Hop.startGame) {
            g.setColor(new Color(0,0,0,180));
            g.setFont(new Font("Arial", Font.BOLD, 15));
            g.drawString("Appuyez sur une touche pour commencer !", 45, field.height / 2);
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


    public void updateScoreAndLevel() {

            for(Block b  : field.ensembleBlocks){
                if(b.isMoving()){
                    b.effet(axel);
                }
            }

            if(this.field.getScore() >= 7200){
                niveau = 6;
                Hop.speed = 6;
                this.field.increaseWidthBlock();
            }
            else if(this.field.getScore() >= 4800){
                niveau = 5;
                Hop.speed = 5;
                this.field.increaseWidthBlock();
            }
            else if(this.field.getScore() >= 3200){
                niveau = 4;
                Hop.speed = 4;
                this.field.increaseWidthBlock();
            }
            else if(this.field.getScore() >= 2000){
                niveau = 3;
                Hop.speed = 3;
                this.field.increaseWidthBlock();
            }
            else if(this.field.getScore() >= 800){
                niveau = 2;
                Hop.speed = 2;
                this.field.increaseWidthBlock();
            }
            else if(this.field.getScore() >= 80){
                niveau = 1;
                Hop.speed = 1;
                this.field.increaseWidthBlock();
            }

        


    }

                  
    public void keyTyped(KeyEvent e){}

}
