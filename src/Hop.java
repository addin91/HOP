package src;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

public class Hop {
    public static final int WIDTH = 400;
    public static final int HEIGHT = 600;
    public static final int DELAY = 40;
    public static int speed = 1;
    public static boolean startGame = false;
    public static boolean playing = false;
    public static boolean restart = false;
    private int nbround= 1;

    private final JFrame frame;
    private Field field;
    private Axel axel;
    public Timer timer;
    private GamePanel gamePanel;
    private MenuPanel menuPanel;
    private EndGamePanel endGamePanel;
    private MusicGame musicGame;
    private MusicGame musicGameFin;
    private Db db;

    public Hop() {
        this.frame =  new JFrame("Hop!");
        ArrayList<String> musicFiles = new ArrayList<>();
        musicFiles.add("assets/audio/Music/OST1.wav");
        musicFiles.add("assets/audio/Music/OST2.wav");
        musicFiles.add("assets/audio/Music/OST3.wav");
        this.musicGame = new MusicGame(musicFiles);
        musicGame.playRandom();

        this.menuPanel = new MenuPanel();
        frame.add(menuPanel);

        ArrayList<String> musicFilesFinDeJeu = new ArrayList<>();
        musicFilesFinDeJeu.add("assets/audio/SoundEffect/MarioDeath.wav");
        this.musicGameFin = new MusicGame(musicFilesFinDeJeu);
        //endGamePanel.setMusic(musicGameFin);

        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        this.field = new Field(WIDTH, HEIGHT);
        this.axel = new Axel(field, WIDTH / 2, Field.START_ALTITUDE);
        this.gamePanel = new GamePanel(field, axel);
        frame.add(gamePanel);
        frame.addKeyListener(gamePanel);

        this.db = new Db();
        db.readFromFile();
    }

/*     public void startGame() {
        this.frame.remove(menuPanel);
        this.frame.removeKeyListener(menuPanel);

        frame.add(gamePanel);   
        frame.addKeyListener(gamePanel);
        frame.revalidate(); 
        frame.repaint();
    }
*/
    public JPanel getGamePanel() {
        return gamePanel;
    }

    public void round() {
        if (playing && nbround>0) {
            this.nbround--;
            this.frame.remove(menuPanel);
            this.axel.setName(menuPanel.getPlayerName());
            //this.frame.removeKeyListener(menuPanel);
//        }
        //if(nbround==0) 
//        {
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

    public boolean over() {
        return !this.axel.getSurviving() || axel.getY() < 0;
    }

    public void showEndGame(){
        musicGame.setPlaying(false);
        musicGame.stopMusic(); // Stop la musique actuelle
        musicGame.close(); // Libère les ressources
        musicGameFin.playFin();
        endGamePanel = new EndGamePanel(this.field.getScore(), this.db.bestScore());
        System.out.println("musique");
        frame.setContentPane(endGamePanel);
        frame.revalidate();
        frame.repaint();
    
        // Jouer la musique de fin
        //System.out.println("Lecture de la musique de fin...");
        //finMusicGame.playMusic("assets/audio/MarioDeath.wav");
    }
    

    public void registre(){
        db.readFromFile();
        db.addPerson(axel.getName(), field.getScore());
        db.sortRanking();
        db.writeToFile();
    }
    
    public void replay(){
        musicGameFin.stop();
        musicGame.startMusic();
        System.out.println(musicGame+"");
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
                        //game.showEndGame();
                        if(restart){
                            game.registre();
                            restart = false;
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
