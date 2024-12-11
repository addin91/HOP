package src;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MusicGame {
    private Clip clip;
    private ArrayList<String> musicFiles;
    private Random random;

    public MusicGame(ArrayList<String> musicFiles) {
        this.musicFiles = musicFiles;
        this.random = new Random();
    }

    public void playRandom() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
        String selectedFile = musicFiles.get(random.nextInt(musicFiles.size()));
        try {
            File audioFile = new File(selectedFile);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.setFramePosition(0);
            clip.start();
            //clip.loop(Clip.LOOP_CONTINUOUSLY); // Lecture en boucle
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public void close() {
        if (clip != null) {
            clip.close();
        }
    }
}
