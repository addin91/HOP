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
    private int niveau = 1;
    private final Hop game;

    public GamePanel(Field field, Axel axel, Hop game) {
        this.field = field;
        this.axel = axel;
        this.game = game;
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
            g.fillRect(b.getX(), b.getY(), b.getWidth(), BLOCK_HEIGHT);
        }
        // Axel
        //g.drawImage(this.axel.getImage(), this.axel.getX(), this.axel.getY(), 40, 40, null);
        g.setColor(Color.RED);
        g.fillOval(this.axel.getX(), this.axel.getY()+AXEL_HEIGHT, AXEL_WIDTH, AXEL_HEIGHT);
        // Score
        int lastY = axel.getY();
            for (Block block : field.ensembleBlocks) {
                if (axel.getY() < block.getY() && axel.getY() + AXEL_HEIGHT > block.getY()) {
                    score+=(int)(Math.random()*10);
                    lastY = axel.getY();
                    break;
                }
            }
        g.rotate(Math.toRadians(180.0), x, y);
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.drawString("Score : " + score, 10, 20);
        g.drawString("Difficulté: " + field.getSpeed(), 310, 20);

        if (!game.isGameStarted()) {
            g.setColor(new Color(0,0,0,180));
            g.setFont(new Font("Arial", Font.BOLD, 15));
            g.drawString("Appuyez sur une touche pour commencer !", 45, field.height / 2);
        }
    }

    public void keyPressed(KeyEvent e){
        if (!game.isGameStarted()) {
            game.startGame();
        }
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

    public void updateScoreAndLevel() {
        if (score / 150 > niveau - 1) {
            niveau++;
            field.increaseDifficulty();
        }
    }

                  
    public void keyTyped(KeyEvent e){}

}