package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.awt.event.KeyEvent;

/**
 * Classe représentant le panneau du jeu.
 * Cette classe gère les emplacements et les éléments graphiqes, les interactions clavier, 
 * l'affichage des effets...
 */
public class GamePanel extends JPanel implements KeyListener {

    // Constantes
    private static final int BLOCK_HEIGHT = 10; // La longueur d'un block.
    private static final int AXEL_WIDTH = 10; // La largeur d'axel.
    private static final int AXEL_HEIGHT = 10; // La longueur d'axel.
    public static final int BORDER_RIGHT = 33; // La bordure droite.
    public static final int BORDER_LEFT = 47; // La bordure gauche.
    private final int lavaHeight = 20; // Hauteur de la lave.
    private int lavaOffset = 0; // Décalage de la lave. 
    private final Timer lavaAnimation; // Timer pour animer la lave

    /**
     * Un objet {@link Axel} représentant le prersonnage principal du jeu.
     */
    private final Axel axel;

    /**
     * Un objet {@link Field} représentant le terrain de jeu.
     */
    private final Field field;

    /**
     * Un entier représentant le niveau de difficulté du jeu.
     */
    private int niveau;

    /**
     * Un objet {@link Image} représentant l'image de fond du jeu.
     */
    private final Image Game;

    /**
     * Un objet {@link Image} représentant l'image d'axel par défaut.
     */
    private Image marioFace;

    /**
     * Un objet {@link Image} représentant l'image d'axel quand il se dirige
     * vers la droite.
     */
    private Image marioRight;

    /**
     * Un objet {@link Image} représentant l'image d'axel quand il se dirige
     * vers la gauche.
     */
    private Image marioLeft;

    /**
     * Un objet {@link Image} représentant l'image d'axel quand il saute.
     */
    private Image marioJump;

    /**
     * Un objet {@link Image} représentant l'image actuelle d'axel dans le jeu.
     */
    private Image currentMario;

    /**
     * Un objet {@link Image} représentant l'image des blocks par défaut.
     */
    private Image blockImage; 

    /**
     * Un objet {@link MusicGame} représentant les effets sonores du jeu.
     */
    private MusicGame soundEffect;

    /**
     * Une liste de chaînes de caractères représentant les fichiers sonores du jeu.
     */
    private ArrayList<String> soundFiles;

    /**
     * Un objet {@link Effect} représentant l'effet de "Level Up".
     */
    private Effect levelUpEffect;
    
    /**
     * Constructeur de la classe GamePanel.
     * Initialise le panneau du jeu avec le terrain et le personnage.
     * @param field Un objet {@link Field} représentant le terrain de jeu.
     * @param axel Un objet {@link Axel} représentant le joueur principal du jeu.
     */
    public GamePanel(final Field field, final Axel axel) {
        
        // Charger les images du personnages
        this.marioFace = new ImageIcon(getClass().getResource("/assets/images/mario/axel_bas.gif")).getImage();
        this.marioRight= new ImageIcon(getClass().getResource("/assets/images/mario/axel_droite.gif")).getImage();
        this.marioLeft = new ImageIcon(getClass().getResource("/assets/images/mario/axel_gauche.gif")).getImage();
        this.marioJump = new ImageIcon(getClass().getResource("/assets/images/mario/axel_haut.gif")).getImage();
        this.currentMario = this.marioFace;
        
        // Charger l'image des blocks et du jeu
        this.blockImage = new ImageIcon(getClass().getResource("/assets/images/block.png")).getImage();
        this.Game = new ImageIcon(getClass().getResource("/assets/images/InterfaceGame.png")).getImage();

        // Charger les effets sonores du jeu
        this.soundFiles = new ArrayList<>();
        this.soundFiles.add("assets/audio/SoundEffect/Yahoo.wav");
        this.soundEffect = new MusicGame(soundFiles);
        
        // Initialiser le terrain, le personnage et le niveau
        this.field = field;
        this.axel = axel;
        this.niveau = 0;

        // Configurer le panneau
        setPreferredSize(new Dimension(field.width, field.height));
        addKeyListener(this);
        setFocusable(true);

        // Configurer la lave
        this.lavaAnimation = new Timer(50, e -> {
            this.lavaOffset += 3;
            if (this.lavaOffset >= 50) {
                this.lavaOffset = 0;
            }
            repaint();
        });
        lavaAnimation.start();
    }

    /**
     * Méthode déclenchant l'effet de "Level Up".
     * initialise l'effet si aucun effet n'est actif ou s'il n'a pas été déclenché.
     */
    public void triggerLevelUpEffect() {
        if (levelUpEffect == null || !levelUpEffect.isTriggered()) {
            levelUpEffect = new LevelUpEffect(120, 180);
        }
        levelUpEffect.trigger();
    }

    /**
     * Méthode dessinant le contenu du panneau, y compris le terrain, le personnage , les blocs,
     * le score, la lave...
     * @param graphics Un objet {@link Graphics} utilisé pour dessiner les éléments.
     */
    public void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        int x = this.getWidth() / 2;
        int y = this.getHeight() / 2;
        super.paintComponent(g);

        // Dessine l'image du fond
        if (Game != null) {
            g.drawImage(Game, 0, 0, getWidth(), getHeight(), this);
        }
        g.rotate(Math.toRadians(180.0), x, y); 
        
        // Dessine les blocs 
        g.setColor ( new Color (0 , 0 , 0 , 255));
        for(Block b: this.field.ensembleBlocks){
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
        // Dessine le personnage axel
        if (axel.isFalling()) {
            if (axel.isLeft()) currentMario = marioLeft;
            else if (axel.isRight()) currentMario = marioRight;
            else currentMario = marioJump;
        } else if (axel.isRight()) {
            currentMario = marioRight;
        } else if (axel.isLeft()) {
            currentMario = marioLeft;
        } else {
            currentMario = marioFace;
        }
        g.drawImage(currentMario, this.axel.getX()-(2*AXEL_WIDTH)+2, this.axel.getY()+AXEL_HEIGHT-3, null);
        
        // Score
        g.rotate(Math.toRadians(180.0), x, y);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Verdana", Font.ITALIC, 15));
        g.drawString("Score : " + this.field.getScore(), BORDER_LEFT-10, 20);
        g.drawString("Difficulté : " + niveau, Hop.WIDTH-BORDER_RIGHT-110, 20);
        
        // Démarrage
        if (!Hop.startGame) {
            g.setColor(new Color(0,0,0,180));
            g.setFont(new Font("Arial", Font.BOLD, 15));
            g.drawString("Appuyez sur une touche pour commencer !", 34, field.height / 2);
        }

        // Dessine la lave
        drawLava(g);
        if (levelUpEffect != null && !levelUpEffect.isFinished()) {
            levelUpEffect.update();
            levelUpEffect.draw(g);
        }
}

    /**
     * Méthode dessinant la lave en bas de l'écran avec une animation.
     * @param g Un objet {@link Graphics} utilisé pour dessiner la lave.
     */
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

    /**
     * Méthode mettant à jour le score et la difficulté du jeu.
     * Déclenche un effet "Level Up" lorsque le niveau augmente.
     */
    public void updateScoreAndLevel() {
        int oldNiveau = niveau;
            for(Block b  : field.ensembleBlocks){
                if(b.isMoving()){
                    b.effet(axel);
                }
            }
            if(this.field.getScore() >= 7200 && niveau < 6){
                niveau = 6;
                Hop.speed = 6;
                this.field.increaseWidthBlock();
            }
            else if(this.field.getScore() >= 4800 && niveau < 5){
                niveau = 5;
                Hop.speed = 5;
                this.field.increaseWidthBlock();
            }
            else if(this.field.getScore() >= 3200 && niveau < 4){
                niveau = 4;
                Hop.speed = 4;
                this.field.increaseWidthBlock();
            }
            else if(this.field.getScore() >= 2000 && niveau < 3){
                niveau = 3;
                Hop.speed = 3;
                this.field.increaseWidthBlock();
            }
            else if(this.field.getScore() >= 800 && niveau < 2){
                niveau = 2;
                Hop.speed = 2;
                this.field.increaseWidthBlock();
            }
            else if(this.field.getScore() >= 80 && niveau < 1){
                niveau = 1;
                Hop.speed = 1;
                this.field.increaseWidthBlock();
            }
        if (niveau != oldNiveau){
            triggerLevelUpEffect();
            soundEffect.playSound();
            levelUpEffect.setTriggered(false);
        }
    }

    /**
     * Méthode gérant le mouvement lorsqu'une touche est appuyé.
     * @param e Un objet {@link KeyEvent} utilisé pour déterminer les touches.
     */
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

    /**
     * Méthode gérant le mouvement lorsqu'une touche est relachée.
     * @param e Un objet {@link KeyEvent} utilisé pour déterminer les touches.
     */
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