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
    public static boolean replay = false;
    private int nbround= 1;

    private final JFrame frame;
    private final Field field;
    private final Axel axel;
    public Timer timer;
    private GamePanel gamePanel;
    private MenuPanel menuPanel;
    private EndGamePanel endGamePanel;
    private MusicGame musicGame;
    private Db db;

    public Hop() {
        this.frame =  new JFrame("Hop!");
        ArrayList<String> musicFiles = new ArrayList<>();
        musicFiles.add("assets/audio/OST1.wav");
        this.musicGame = new MusicGame(musicFiles);
        musicGame.playRandom();

        this.menuPanel = new MenuPanel();
        frame.add(menuPanel);

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
        musicGame.stop();
        endGamePanel = new EndGamePanel();
        frame.setContentPane(endGamePanel);
        frame.revalidate();
        frame.repaint();
    }

    public void registre(){
        db.readFromFile();
        db.addPerson(axel.getName(), field.getScore());
        db.sortRanking();
        db.writeToFile();
    }
    

    public static void main(String[] args) {
            Hop game = new Hop();

            game.timer = new Timer(DELAY, (ActionEvent e) -> {
                    game.round();
                    if (game.over()) {
                        game.timer.stop();
                        game.registre();
                        //JOptionPane.showMessageDialog(game.frame, "Game Over!", "Hop!", JOptionPane.INFORMATION_MESSAGE);
                        game.frame.remove(game.gamePanel);
                        game.showEndGame();
                        //System.exit(0);
                    }


            });
            game.timer.start();
        
    }
}
