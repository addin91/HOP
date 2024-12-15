package src;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class Hop {
    public static final int WIDTH = 400;
    public static final int HEIGHT = 600;
    public static final int DELAY = 40;
    public static int speed = 1;
    public static boolean startGame = false;
    public static boolean playing = false;
    public static boolean restart = false;

    private final JFrame frame;
    private Field field;
    private Axel axel;
    private Timer timer;
    private ImageIcon logo;
    private GamePanel gamePanel;
    private MenuPanel menuPanel;
    private EndGamePanel endGamePanel;
    private MusicGame musicGame;
    private MusicGame musicGameFin;
    private ArrayList<String> musicFiles;
    private ArrayList<String> musicFilesFinDeJeu;
    private Db db;
    private int nbRound;

    public Hop() {
        this.frame =  new JFrame("Super Mario Hop!");
        // Logo 
        this.logo = new ImageIcon(getClass().getResource("/assets/images/HopLogo.png"));
        this.frame.setIconImage(logo.getImage());
        this.musicFiles = new ArrayList<>();
        this.musicFiles.add("assets/audio/Music/OST1.wav");
        this.musicFiles.add("assets/audio/Music/OST2.wav");
        this.musicFiles.add("assets/audio/Music/OST3.wav");
        this.musicFiles.add("assets/audio/Music/OST4.wav");
        this.musicFiles.add("assets/audio/Music/OST5.wav");
        this.musicGame = new MusicGame(musicFiles);
        this.musicGame.playRandom();

        this.menuPanel = new MenuPanel();
        this.frame.add(menuPanel);

        this.musicFilesFinDeJeu = new ArrayList<>();
        this.musicFilesFinDeJeu.add("assets/audio/SoundEffect/MarioDeath.wav");
        this.musicGameFin = new MusicGame(musicFilesFinDeJeu);

        this.frame.setSize(WIDTH, HEIGHT);
        this.frame.setVisible(true);
        this.frame.setResizable(false);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLocationRelativeTo(null);

        this.field = new Field(WIDTH, HEIGHT);
        this.axel = new Axel(field, WIDTH / 2, Field.START_ALTITUDE);
        this.gamePanel = new GamePanel(field, axel);
        this.frame.add(this.gamePanel);
        this.frame.addKeyListener(this.gamePanel);

        this.db = new Db();
        this.db.readFromFile();

        this.nbRound = 1;
    }


    /**
     * la méthode round rafraichit le jeu toutes les 40 ms
     */
    public void round() {
        if (playing && nbRound==1) {;
            this.frame.remove(menuPanel);
            this.axel.setName(menuPanel.getPlayerName());
            frame.add(gamePanel);   
            frame.addKeyListener(gamePanel);
            this.frame.requestFocusInWindow();
            frame.revalidate(); 
            frame.repaint();
        }

        if(!startGame){
            return;
        }
        axel.update();
        field.update();
        gamePanel.updateScoreAndLevel();
        frame.repaint();
    }

    /**
     * la méthode over teste si le personnage est toujours vivant ou mort
     * @return un booléen, "Vrai" si le personnage est mort sinon "Faux"
     */
    public boolean over() {
        return !this.axel.getSurviving();
    }

    /**
     * la méthode showEndGame affiche la fin de jeu en cas de perte
     */
    public void showEndGame(){
        musicGame.setPlaying(false);
        musicGame.stopMusic();
        musicGame.close();
        musicGameFin.playFin();
        endGamePanel = new EndGamePanel(this.field.getScore(), this.db.bestScore(), this.db.rank(this.field.getScore()));
        frame.setContentPane(endGamePanel);
        frame.revalidate();
        frame.repaint();
    }
    
    /**
     * la méthode registre enregistre les donnés de la partie dans la base de donnée
     */
    public void registre(){
        db.readFromFile();
        db.addPlayer(axel.getName(), field.getScore());
        db.sortRanking();
        db.writeToFile();
    }
    
    /**
     * la méthode replay relance le jeu si le joueur souhaite rejouer
     */
    public void replay(){
        musicGameFin.stop();
        musicGame.startMusic();
        this.field = new Field(WIDTH, HEIGHT);
        this.axel = new Axel(field, WIDTH / 2, Field.START_ALTITUDE);
        this.axel.setName(menuPanel.getPlayerName());
        this.gamePanel = new GamePanel(field, axel);
        frame.setContentPane(gamePanel);
        frame.addKeyListener(gamePanel);
        this.frame.requestFocusInWindow();
        frame.revalidate(); 
        frame.repaint();
    }


    public static void main(String[] args) {
            Hop game = new Hop();

            game.timer = new Timer(DELAY, (ActionEvent e) -> {
                    game.round();
                    if (game.over()) {
                        game.frame.remove(game.gamePanel);
                        if (game.frame.getContentPane().getComponentCount() == 0) {
                            game.showEndGame();
                        }
                        if(restart){
                            game.registre();
                            restart = false;
                            game.nbRound++;
                            Hop.startGame = false;
                            game.replay();
                            game.timer.restart();
                        }
                        else if(!Hop.playing) {
                            game.registre();
                            System.exit(0);
                        }
                    }


            });
            game.timer.start();
        
    }
}
