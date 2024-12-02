package src;

import javax.swing.*;
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
    public Timer timer;
    private GamePanel gamePanel;
    public MenuPanel menuPanel;

    public Hop(JFrame frame) {
        this.frame = frame;
        this.field = new Field(WIDTH, HEIGHT);
        this.axel = new Axel(field, WIDTH / 2, Field.START_ALTITUDE);
        this.gamePanel = new GamePanel(field, axel);
        this.menuPanel = new MenuPanel(this);

        frame.add(menuPanel);
        frame.setSize(WIDTH, HEIGHT);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void startGame() {
        this.frame.remove(menuPanel);
        frame.add(gamePanel);   
        frame.addKeyListener(gamePanel);
        frame.revalidate(); 
        frame.repaint();
    }

    public JPanel getGamePanel() {
        return gamePanel;
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
        JFrame frame = new JFrame("Hop!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        Hop game = new Hop(frame);

        game.timer = new Timer(DELAY, (ActionEvent e) -> {
            game.round();
            if (game.over()) {
                game.timer.stop();
                JOptionPane.showMessageDialog(frame, "Game Over!", "Hop!", JOptionPane.INFORMATION_MESSAGE);
                game.frame.remove(game.gamePanel);
                System.exit(0);
            }
        });
        frame.setVisible(true);
        game.timer.start();
    }
}