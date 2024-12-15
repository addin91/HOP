package src;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Classe représentant la musique du jeu.
 * Cette classe permet de lire une liste de musiques, gérer la lecture,
 * arrêter la musique, etc...
 */
public class MusicGame {
    
    /**
     * Un objet {@link Clip} représentant le clip audio en cours de lecture.
     */
    private Clip clip;

    /**
     * Une liste de chaîne de caractères représentant les fichiers musicaux
     * en chemin d'accès.
     */
    private ArrayList<String> musicFiles;

    /**
     * Un objet {@link Random} représentant le générateur de nombres aléatoires, 
     * pour sélectionner un fichier audio de manière aléatoire aléatoire.
     */
    private Random random;

    /**
     * Une liste de chaîne de caracères représentant les fichiers restants avant de
     * réinstialiser la séléction de fichier audio, afin de s'assurer que chaque fichier audio est joué.
     */
    private ArrayList<String> remainingMusicFiles;

    /**
     * Un booléen indiquant si la musique est actuellement en cours de lecture
     * 'true' si la musique est active, sinon 'false'.
     */
    private boolean isPlaying;

    /**
     * Construteur de la classe MusicGame
     * Initialise la liste des fichiers musicaux, la liste des morceaux restants, etc...
     * @param musicFiles
     */
    public MusicGame(final ArrayList<String> musicFiles) {
        this.musicFiles = new ArrayList<>(musicFiles);
        this.remainingMusicFiles = new ArrayList<>(musicFiles);
        this.random = new Random();
        this.isPlaying = true;
    }

    /**
     * Méthode jouant un morceau audio séléctionné de manière aléatoire de la liste
     * des morceaux restants.
     * Si tous les morceaux ont été joués, la liste est réinstialisée.
     */
    public void playRandom() {
        if (remainingMusicFiles.isEmpty()) {
            remainingMusicFiles = new ArrayList<>(musicFiles);
        }
        stop();
        String selectedFile = remainingMusicFiles.remove(random.nextInt(remainingMusicFiles.size()));
        try {
            File audioFile = new File(selectedFile);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.setFramePosition(0);
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP && isPlaying ) {
                    clip.stop();
                    clip.close();
                    playRandom();
                }
            });
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode qui met à jour l'état de la musique en cours et joue
     * des morceaux de manière aléatoire.
     */
    public void startMusic() {
        isPlaying = true;
        playRandom();
    }

    /**
     * Méthode permettant d'arrêter la lecture de la musique.
     */
    public void stopMusic() {
        isPlaying = false;
        stop();
    }

    /**
     * Méthode permettant de jouer la musique de fin de jeu.
     */
    public void playFin() {
        if (musicFiles == null || musicFiles.isEmpty()) {
            System.err.println("Erreur : aucun fichier audio disponible.");
            return;
        }
        stop();
        try {
            String filePath = musicFiles.get(0);
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.setFramePosition(0);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Méthode permettant d'arrêter la lecture du morceau actuellement joué.
     */
    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    /**
     * Méthode permettant de libérer les ressources associées au morceau 
     * actuellement joué. 
     */
    public void close() {
        if (clip != null) {
            clip.close();
        }
    }

    /**
     * Méthode permettant de jouer un effet sonore spécifique.
     */
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


    // GETTERS 

    /**
     * Méthode renvoyant l'état de la lecture de musique.
     * @return 'true' si la musique est active, sinon 'false'.
     */
    public boolean getPlaying(){
        return isPlaying;
    }
    
    // SETTERS

    /**
     * Méthode mettant à jour l'état de la lecture de musique.
     * @param a 'true' pour activer la musique, sinon 'false' pour désactiver.
     */
    public void setPlaying(final boolean a){
        this.isPlaying = a;
    }
}