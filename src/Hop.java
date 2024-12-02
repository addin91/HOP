package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Hop {
    public static final int WIDTH = 400;
    public static final int HEIGHT = 600;
    public static final int DELAY = 40;
    public static int speed = 1;
    public static boolean startGame = false;

    private final JFrame frame;
    private final Field field;
    private final Axel axel;
    private Timer timer;
    private GamePanel gamePanel;

    public Hop() {
        this.field = new Field(WIDTH, HEIGHT);
        this.axel = new Axel(field, WIDTH/2, field.START_ALTITUDE);
        this.gamePanel = new GamePanel(field, axel);
        this.frame = new JFrame("Hop!");
        frame.add(gamePanel);
        frame.addKeyListener(gamePanel);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void round() {
        if(!startGame){
            return;
        }
        axel.update();
        field.update();
        gamePanel.updateScoreAndLevel();
        frame.repaint();
    }

    public boolean over() {
        return !this.axel.getSurviving() || axel.getY() <= 0;
    }

    public static void main(String[] args) {
        Hop game = new Hop();

        game.timer = new Timer(DELAY, (ActionEvent e) -> {
            game.round();
            if (game.over()) {
                game.timer.stop();
                game.frame.remove(game.gamePanel);
            }
        });
        game.timer.start();
    }
}