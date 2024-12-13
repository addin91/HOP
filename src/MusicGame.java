package src;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class MusicGame {
    private Clip clip;
    private ArrayList<String> musicFiles;
    private Random random;
    private ArrayList<String> remainingMusicFiles;
    private boolean isPlaying;

    public MusicGame(ArrayList<String> musicFiles) {
        this.musicFiles = new ArrayList<>(musicFiles);
        this.remainingMusicFiles = new ArrayList<>(musicFiles); // Initialiser la liste des morceaux restants
        this.random = new Random();
        this.isPlaying = true;
    }

    public void playRandom() {
        if (remainingMusicFiles.isEmpty()) {
            // Réinitialiser la liste des morceaux restants si tous ont été joués
            remainingMusicFiles = new ArrayList<>(musicFiles);
        }

        stop(); // Arrêter l'ancienne musique si elle est en cours

        String selectedFile = remainingMusicFiles.remove(random.nextInt(remainingMusicFiles.size()));
        try {
            File audioFile = new File(selectedFile);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.setFramePosition(0);

            // Ajouter un listener pour détecter la fin de la musique
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP && isPlaying ) {
                    //isPlaying = false;
                    clip.stop();
                    clip.close();
                    playRandom();
                     // Passer à une autre musique
                }
            });

            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void startMusic() {
        isPlaying = true; // Activer la musique
        playRandom();
    }

    public void stopMusic() {
        isPlaying = false; // Désactiver la musique
        stop();
    }

    public boolean getPlaying(){
        return isPlaying;
    }

    public void setPlaying(boolean a){
        isPlaying = a;
    }

    public void playFin() {
        if (musicFiles == null || musicFiles.isEmpty()) {
            System.err.println("Erreur : aucun fichier audio disponible.");
            return;
        }
    
        stop(); // Arrêter la musique précédente si elle est en cours
    
        try {
            String filePath = musicFiles.get(0); // Récupérer le seul fichier audio
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.setFramePosition(0); // Démarrer au début
            clip.start(); // Jouer la musique
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

    public void playSound(){
        try {
            String sound = musicFiles.get(0); // Charger le fichier son
            File soundFile = new File(sound);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();  // Créer un clip
            clip.open(audioStream);  // Ouvrir le flux audio
            clip.start();  // Démarrer la lecture du son
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();  // Gérer les erreurs
        }
    }
}
