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
        g.setColor ( new Color (0 , 0 , 0 , 255));
        for(Block b: this.field.ensembleBlocks){
            System.out.println("");
            g.fillRect(b.getX(), b.getY(), b.getWidth(), BLOCK_HEIGHT);
        }
        g.drawOval(this.axel.getX(), this.axel.getY()+AXEL_HEIGHT, AXEL_WIDTH, AXEL_HEIGHT);

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
